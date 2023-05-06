package com.app20222.app20222_backend.controllers.users;

import com.app20222.app20222_backend.dtos.responses.users.IGetListUser;
import com.app20222.app20222_backend.services.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<IGetListUser> getListUsers(){
        return userService.getListUser();
    }

}
