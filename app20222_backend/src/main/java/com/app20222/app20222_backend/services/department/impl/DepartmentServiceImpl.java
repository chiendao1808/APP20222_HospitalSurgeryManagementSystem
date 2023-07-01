package com.app20222.app20222_backend.services.department.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.constants.message.errorKey.ErrorKey;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst.Resources;
import com.app20222.app20222_backend.dtos.department.DepartmentCreateDTO;
import com.app20222.app20222_backend.dtos.department.DepartmentUpdateDTO;
import com.app20222.app20222_backend.entities.department.Department;
import com.app20222.app20222_backend.exceptions.exceptionFactory.ExceptionFactory;
import com.app20222.app20222_backend.repositories.department.DepartmentRepository;
import com.app20222.app20222_backend.services.department.DepartmentService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    private final ExceptionFactory exceptionFactory;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ExceptionFactory exceptionFactory) {
        this.departmentRepository = departmentRepository;
        this.exceptionFactory = exceptionFactory;
    }

    @Override
    public void createDepartment(DepartmentCreateDTO createDTO) {
        if (departmentRepository.existsByCode(createDTO.getCode())) {
            throw exceptionFactory.resourceExistedException(ErrorKey.Department.EXISTED_ERROR_CODE, Resources.DEPARTMENT, MessageConst.RESOURCE_EXISTED,
                ErrorKey.Department.CODE, createDTO.getCode());
        }
        Department department = new Department();
        BeanUtils.copyProperties(createDTO, department);
        departmentRepository.save(department);
    }

    @Override
    public void updateDepartment(Long id, DepartmentUpdateDTO updateDTO) {
        Department department = departmentRepository.findById(id).orElseThrow(
            () -> exceptionFactory.resourceNotFoundException(ErrorKey.Department.NOT_FOUND_ERROR_CODE, MessageConst.RESOURCE_NOT_FOUND,
                Resources.DEPARTMENT, ErrorKey.Department.ID, String.valueOf(id)));
        BeanUtils.copyProperties(updateDTO, department);
        departmentRepository.save(department);
    }
}
