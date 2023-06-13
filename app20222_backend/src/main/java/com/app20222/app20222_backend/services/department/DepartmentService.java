package com.app20222.app20222_backend.services.department;

import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.dtos.department.DepartmentCreateDTO;
import com.app20222.app20222_backend.dtos.department.DepartmentUpdateDTO;

@Service
public interface DepartmentService {

    void createDepartment(DepartmentCreateDTO createDTO);

    void updateDepartment(Long id, DepartmentUpdateDTO updateDTO);

}
