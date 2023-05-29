package com.app20222.app20222_backend.controllers.users;

import com.app20222.app20222_backend.dtos.responses.users.IGetListUser;
import com.app20222.app20222_backend.services.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "User Controllers")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Lấy danh sách users trong hệ thống")
    public List<IGetListUser> getListUsers(@RequestParam(name = "code", required = false, defaultValue = "") String code,
        @RequestParam(name = "name", required = false, defaultValue = "") String name,
        @RequestParam(name = "email", required = false, defaultValue = "") String email,
        @RequestParam(name = "phone", required = false, defaultValue = "") String phone,
        @RequestParam(name = "departmentId", required = false, defaultValue = "-1") Long departmentId,
        @RequestParam(name = "roleId", required = false, defaultValue = "-1") Long roleId){
        return userService.getListUser(code, name, email, phone, departmentId, roleId);
    }

}
