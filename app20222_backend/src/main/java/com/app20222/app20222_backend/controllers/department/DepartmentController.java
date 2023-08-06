package com.app20222.app20222_backend.controllers.department;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app20222.app20222_backend.dtos.department.DepartmentCreateDTO;
import com.app20222.app20222_backend.dtos.department.DepartmentUpdateDTO;
import com.app20222.app20222_backend.dtos.department.IGetListDepartment;
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

    @GetMapping("/get-list")
    @Operation(description = "Lấy danh sách khoa/bộ phận")
    public List<IGetListDepartment> getListDepartment(
        @RequestParam(name = "id", required = false, defaultValue = "-1") Long id,
        @RequestParam(name = "code", required = false, defaultValue = "") String code,
        @RequestParam(name = "name", required = false, defaultValue = "") String name,
        @RequestParam(name = "email", required = false, defaultValue = "") String email,
        @RequestParam(name = "phone", required = false, defaultValue = "") String phone) {
        return departmentService.getListDepartment(id, code, name, email, phone);
    }

    @DeleteMapping()
    @Operation(description = "Xoá khoa bộ phận")
    public void deleteDepartment(@RequestParam(name = "id") Long id){

    }

}
