package com.app20222.app20222_backend.services.department.impl;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.app20222.app20222_backend.constants.message.errorKey.ErrorKey;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst.Resources;
import com.app20222.app20222_backend.dtos.department.DepartmentCreateDTO;
import com.app20222.app20222_backend.dtos.department.DepartmentUpdateDTO;
import com.app20222.app20222_backend.dtos.department.IGetListDepartment;
import com.app20222.app20222_backend.entities.department.Department;
import com.app20222.app20222_backend.exceptions.exceptionFactory.ExceptionFactory;
import com.app20222.app20222_backend.repositories.department.DepartmentRepository;
import com.app20222.app20222_backend.repositories.users.UserRepository;
import com.app20222.app20222_backend.services.department.DepartmentService;
import com.app20222.app20222_backend.services.permission.PermissionService;
import com.app20222.app20222_backend.utils.auth.AuthUtils;
import com.app20222.app20222_backend.utils.permissionUtils.PermissionUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final ExceptionFactory exceptionFactory;

    private final UserRepository userRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ExceptionFactory exceptionFactory, UserRepository userRepository) {
        this.departmentRepository = departmentRepository;
        this.exceptionFactory = exceptionFactory;
        this.userRepository = userRepository;
    }

    @Override
    public void createDepartment(DepartmentCreateDTO createDTO) {
        if (AuthUtils.isSuperAdmin() && PermissionUtils.checkAdminRoles()) {
            if (departmentRepository.existsByCode(createDTO.getCode())) {
                throw exceptionFactory
                    .resourceExistedException(ErrorKey.Department.EXISTED_ERROR_CODE, Resources.DEPARTMENT, MessageConst.RESOURCE_EXISTED,
                        ErrorKey.Department.CODE, createDTO.getCode());
            }
            Department department = new Department();
            BeanUtils.copyProperties(createDTO, department);
            departmentRepository.save(department);
        } else {
            throw exceptionFactory.permissionDeniedException(ErrorKey.User.PERMISSION_DENIED_ERROR_CODE, Resources.DEPARTMENT,
                MessageConst.PERMISSIONS_DENIED);
        }
    }

    @Override
    public void updateDepartment(Long id, DepartmentUpdateDTO updateDTO) {
        if (AuthUtils.isSuperAdmin() && PermissionUtils.checkAdminRoles()) {
            Department department = departmentRepository.findById(id).orElseThrow(
                () -> exceptionFactory.resourceNotFoundException(ErrorKey.Department.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND,
                    Resources.DEPARTMENT, ErrorKey.Department.ID, String.valueOf(id)));
            BeanUtils.copyProperties(updateDTO, department);
            departmentRepository.save(department);
        } else {
            throw exceptionFactory.permissionDeniedException(ErrorKey.User.PERMISSION_DENIED_ERROR_CODE, Resources.DEPARTMENT,
                MessageConst.PERMISSIONS_DENIED);
        }
    }

    @Override
    public List<IGetListDepartment> getListDepartment(Long id, String code, String name, String email, String phone) {
        return departmentRepository.getListDepartment(id, code, name, email, phone);
    }

    @Transactional
    @Override
    public void deleteDepartment(Long id) {
        if(AuthUtils.isSuperAdmin() && PermissionUtils.checkAdminRoles()){
            if(departmentRepository.existsById(id)){
                userRepository.changeAllUserDepartmentByDepartmentId(id, -1L);
                departmentRepository.deleteById(id);
            } else {
                throw exceptionFactory.resourceNotFoundException(ErrorKey.Department.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND,
                    Resources.DEPARTMENT, ErrorKey.Department.ID, String.valueOf(id));
            }
        }else {
            throw exceptionFactory.permissionDeniedException(ErrorKey.User.PERMISSION_DENIED_ERROR_CODE, Resources.DEPARTMENT,
                MessageConst.PERMISSIONS_DENIED);
        }
    }
}
