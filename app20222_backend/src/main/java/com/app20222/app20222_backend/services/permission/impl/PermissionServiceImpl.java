package com.app20222.app20222_backend.services.permission.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.entities.surgery.Surgery;
import com.app20222.app20222_backend.entities.users.User;
import com.app20222.app20222_backend.enums.permission.BasePermissionEnum;
import com.app20222.app20222_backend.enums.role.RoleEnum;
import com.app20222.app20222_backend.repositories.department.DepartmentRepository;
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


    /**
     * CDI : Constructor Dependency Injection
     */
    public PermissionServiceImpl(DepartmentRepository departmentRepository,
        UserRepository userRepository, SurgeryRepository surgeryRepository){
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.surgeryRepository = surgeryRepository;
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
    public Boolean hasUserPermission(Long userId, BasePermissionEnum permission) {
        // check user exists
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) return false;
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
        return hasPermission;
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
        // Nếu role là doctor hoặc nurse -> chỉ xem những surgery được assigne
        if (roles.contains(RoleEnum.DOCTOR.getRoleCode()) || roles.contains(RoleEnum.NURSE.getRoleCode())) {
            lstViewableSurgeryId.addAll(surgeryRepository.findViewableSurgeryIdsByUser(AuthUtils.getCurrentUserId()));
        }
        return lstViewableSurgeryId;
    }

    /**
     * Check quyền của user đăng nhập với một surgery bất kỳ -> chi tiết surgery
     */
    @Override
    public Boolean hasSurgeryPermission(Long surgeryId, BasePermissionEnum permission) {
        Surgery surgery = surgeryRepository.findById(surgeryId).orElse(null);
        if(surgery == null) return false;
        Set<Long> lstViewableSurgeryIds = getLstViewableSurgeryId();
        List<String> roles = AuthUtils.getCurrentUserRoles() != null ? AuthUtils.getCurrentUserRoles() : new ArrayList<>();
        boolean hasPermission = false;
        switch (permission){
            case VIEW:
                hasPermission = lstViewableSurgeryIds.contains(surgeryId);
                break;
            case EDIT:
            case DELETE:
                hasPermission = AuthUtils.isSuperAdmin()
                    || roles.contains(RoleEnum.HOSPITAL_ADMIN.getRoleCode())
                    || roles.contains(RoleEnum.DEPARTMENT_ADMIN.getRoleCode())
                    || Objects.equals(surgery.getCreatedBy(), AuthUtils.getCurrentUserId());
                break;
        }
        return hasPermission;
    }
}
