package com.app20222.app20222_backend.services.department;

import java.util.List;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.dtos.department.DepartmentCreateDTO;
import com.app20222.app20222_backend.dtos.department.DepartmentUpdateDTO;
import com.app20222.app20222_backend.dtos.department.IGetListDepartment;

@Service
public interface DepartmentService {

    void createDepartment(DepartmentCreateDTO createDTO);

    void updateDepartment(Long id, DepartmentUpdateDTO updateDTO);

    List<IGetListDepartment> getListDepartment(Long id, String code, String name, String email, String phone);

    /**
     * Xoá khoa/bộ phận -> chuyển các user có departmentId = id -> -1L -> đợi gán lại
     */
    void deleteDepartment(Long id);

}
