package com.app20222.app20222_backend.controllers.users;

import com.app20222.app20222_backend.dtos.responses.users.IGetListUser;
import com.app20222.app20222_backend.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<IGetListUser> getListUsers(){
        return userService.getListUser();
    }

}
