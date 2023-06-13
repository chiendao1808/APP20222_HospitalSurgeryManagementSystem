package com.app20222.app20222_backend.controllers.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app20222.app20222_backend.dtos.department.DepartmentCreateDTO;
import com.app20222.app20222_backend.dtos.department.DepartmentUpdateDTO;
import com.app20222.app20222_backend.services.department.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/department")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    @Operation(description = "Tạo Khoa/Bộ phận")
    public void createDepartment(@RequestBody @Validated DepartmentCreateDTO createDTO) {
        departmentService.createDepartment(createDTO);
    }

    @PutMapping
    @Operation(description = "Cập nhật Khoa/Bộ phận")
    public void updateDepartment(@RequestParam(name = "id") Long id,
        @RequestBody @Validated DepartmentUpdateDTO updateDTO) {
        departmentService.updateDepartment(id, updateDTO);
    }

}
