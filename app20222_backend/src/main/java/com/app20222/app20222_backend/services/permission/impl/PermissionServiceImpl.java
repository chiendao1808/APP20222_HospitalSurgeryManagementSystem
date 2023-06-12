package com.app20222.app20222_backend.services.permission.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.app20222.app20222_backend.constants.message.error_field.ErrorKey;
import com.app20222.app20222_backend.constants.message.message_const.MessageConst;
import com.app20222.app20222_backend.constants.message.message_const.MessageConst.Resources;
import com.app20222.app20222_backend.entities.patient.Patient;
import com.app20222.app20222_backend.entities.surgery.Surgery;
import com.app20222.app20222_backend.entities.users.User;
import com.app20222.app20222_backend.enums.permission.BasePermissionEnum;
import com.app20222.app20222_backend.enums.role.RoleEnum;
import com.app20222.app20222_backend.exceptions.exception_factory.ExceptionFactory;
import com.app20222.app20222_backend.repositories.department.DepartmentRepository;
import com.app20222.app20222_backend.repositories.patient.PatientRepository;
import com.app20222.app20222_backend.repositories.surgery.SurgeryRepository;
import com.app20222.app20222_backend.repositories.users.UserRepository;
import com.app20222.app20222_backend.services.permission.PermissionService;
import com.app20222.app20222_backend.utils.PermissionUtils.PermissionUtils;
import com.app20222.app20222_backend.utils.auth.AuthUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    private final DepartmentRepository departmentRepository;

    private final UserRepository userRepository;

    private final SurgeryRepository surgeryRepository;

    private final ExceptionFactory exceptionFactory;

    private final PatientRepository patientRepository;


    /**
     * CDI : Constructor Dependency Injection
     */
    public PermissionServiceImpl(DepartmentRepository departmentRepository,
        UserRepository userRepository, SurgeryRepository surgeryRepository, ExceptionFactory exceptionFactory,
        PatientRepository patientRepository){
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.surgeryRepository = surgeryRepository;
        this.exceptionFactory = exceptionFactory;
        this.patientRepository = patientRepository;
    }


    /*
      ========================== USER PERMISSIONS =========================
     */

    /**
     * Lấy danh sách các department mà user đăng nhập có quyền xem -> màn danh sách
     */
    @Override
    public Set<Long> getLstViewableDepartmentId() {
        Set<Long> lstViewableDepartmentId = new HashSet<>();
        Long userDepartmentId = AuthUtils.getCurrentUserDepartmentId();
        // Nếu là các vai trò trong department -> chỉ xem được department trực thuộc
        if(PermissionUtils.checkDepartmentRoles())
            lstViewableDepartmentId.add(userDepartmentId);
        // Nếu là các vai trò trong hospital hoặc là super admin -> xem được tất cả department trong hospital
        if(PermissionUtils.checkHospitalRoles() || AuthUtils.isSuperAdmin())
            lstViewableDepartmentId.addAll(departmentRepository.getAllDepartmentId());
        return lstViewableDepartmentId;
    }

    /**
     * Check các quyền của user đăng nhập với một user bất kỳ -> chi tiết
     */
    @Override
    public User hasUserPermission(Long userId, BasePermissionEnum permission) {
        // check user exists
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw exceptionFactory.resourceNotFoundException(ErrorKey.User.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND, Resources.USER,
                ErrorKey.User.ID, String.valueOf(userId));
        }
        Set<Long> lstViewableDepartmentId = getLstViewableDepartmentId();
        List<String> currentUsersRoles = AuthUtils.getCurrentUserRoles() != null ? AuthUtils.getCurrentUserRoles() : new ArrayList<>();
        Long currentUserId = AuthUtils.getCurrentUserId();
        boolean hasPermission = false;
        switch (permission) {
            case VIEW:
                hasPermission = lstViewableDepartmentId.contains(user.getDepartmentId());
                break;
            case EDIT:
            case DELETE:
                hasPermission = AuthUtils.isSuperAdmin()
                    || currentUsersRoles.contains(RoleEnum.HOSPITAL_ADMIN.getRoleCode())
                    || Objects.equals(currentUserId, userId)
                    || (currentUsersRoles.contains(RoleEnum.DEPARTMENT_ADMIN.getRoleCode())
                        && Objects.equals(AuthUtils.getCurrentUserDepartmentId(), user.getDepartmentId()));
                break;
            default:
                break;
        }
        if (!hasPermission) {
            throw exceptionFactory.permissionDeniedException(ErrorKey.User.PERMISSION_DENIED_ERROR_CODE, Resources.USER,
                MessageConst.PERMISSIONS_DENIED);
        }
        return user;
    }

    /*
     * ========================== SURGERY PERMISSIONS =========================
     */

    /**
     * Lấy danh sách surgery id mà user đăng nhập có quyền xem -> màn danh sách
     */
    @Override
    public Set<Long> getLstViewableSurgeryId() {
        Set<Long> lstViewableSurgeryId = new HashSet<>();
        List<String> roles = AuthUtils.getCurrentUserRoles() != null ? AuthUtils.getCurrentUserRoles() : new ArrayList<>();
        // Nếu là role hospital hoặc super admin -> xem toàn bộ
        if (PermissionUtils.checkHospitalRoles() || AuthUtils.isSuperAdmin()) {
            lstViewableSurgeryId.addAll(surgeryRepository.findAllSurgeryIds());
        }
        // Nếu role là admin hoặc manager của department -> xem những surgery có user tham gia thuộc department của mình hoặc do mình tạo
        if (roles.contains(RoleEnum.DEPARTMENT_ADMIN.getRoleCode()) || roles.contains(RoleEnum.DEPARTMENT_MANAGER.getRoleCode())) {
            lstViewableSurgeryId.addAll(
                surgeryRepository.findViewableSurgeryIdsByDepartment(AuthUtils.getCurrentUserId(), AuthUtils.getCurrentUserDepartmentId()));
        }
        // Nếu role là doctor hoặc nurse -> chỉ xem những surgery được assign
        if (roles.contains(RoleEnum.DOCTOR.getRoleCode()) || roles.contains(RoleEnum.NURSE.getRoleCode())) {
            lstViewableSurgeryId.addAll(surgeryRepository.findViewableSurgeryIdsByUser(AuthUtils.getCurrentUserId()));
        }
        return lstViewableSurgeryId;
    }

    /**
     * Check quyền của user đăng nhập với một surgery bất kỳ -> chi tiết surgery
     */
    @Override
    public Surgery hasSurgeryPermission(Long surgeryId, BasePermissionEnum permission) {
        // Check exists
        Surgery surgery = surgeryRepository.findById(surgeryId)
            .orElseThrow(() -> exceptionFactory.resourceNotFoundException(ErrorKey.Surgery.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND,
                Resources.SURGERY, ErrorKey.Surgery.ID, String.valueOf(surgeryId)));
        // Check surgery permission
        Set<Long> lstViewableSurgeryIds = getLstViewableSurgeryId();
        List<String> roles = AuthUtils.getCurrentUserRoles() != null ? AuthUtils.getCurrentUserRoles() : new ArrayList<>();
        boolean hasPermission = false;
        switch (permission){
            case VIEW:
                hasPermission = lstViewableSurgeryIds.contains(surgery.getId());
                break;
            case EDIT:
            case DELETE:
                hasPermission = AuthUtils.isSuperAdmin()
                    || roles.contains(RoleEnum.HOSPITAL_ADMIN.getRoleCode())
                    || roles.contains(RoleEnum.DEPARTMENT_ADMIN.getRoleCode())
                    || Objects.equals(surgery.getCreatedBy(), AuthUtils.getCurrentUserId());
                break;
        }
        if(!hasPermission){
            throw exceptionFactory.permissionDeniedException(ErrorKey.Surgery.PERMISSION_DENIED_ERROR_CODE, Resources.SURGERY, MessageConst.PERMISSIONS_DENIED);
        }
        return surgery;
    }

    @Override
    public Patient hasPatientPermission(Long patientId, BasePermissionEnum permission) {
        // check patient exists
        Patient patient = patientRepository.findById(patientId).orElseThrow(
            () -> exceptionFactory.resourceNotFoundException(ErrorKey.Patient.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND,
                Resources.PATIENT, ErrorKey.Patient.ID, String.valueOf(patientId)));
        List<String> roles =
            Objects.nonNull(AuthUtils.getCurrentUserId()) && Objects.nonNull(AuthUtils.getCurrentUserRoles()) ? AuthUtils.getCurrentUserRoles()
                : new ArrayList<>();
        // Danh sách các role có quyền chỉnh sửa và xóa hồ sơ bệnh nhân
        List<String> lstEditableRole = Arrays.asList(RoleEnum.DEPARTMENT_ADMIN.getRoleCode(), RoleEnum.DEPARTMENT_MANAGER.getRoleCode(),
            RoleEnum.HOSPITAL_ADMIN.getRoleCode(), RoleEnum.HOSPITAL_MANAGER.getRoleName());
        boolean hasPermission = false;
        switch (permission) {
            case VIEW:
                hasPermission = true;
                break;
            case EDIT:
            case DELETE:
                if (CollectionUtils.containsAny(roles, lstEditableRole)) {
                    hasPermission = true;
                }
                break;
            default:
                break;
        }
        // check permission if false then throw permission denied exception
        if (!hasPermission) {
            throw exceptionFactory.permissionDeniedException(ErrorKey.Patient.PERMISSION_DENIED_ERROR_CODE, Resources.PATIENT,
                MessageConst.PERMISSIONS_DENIED);
        }
        return patient;
    }
}
