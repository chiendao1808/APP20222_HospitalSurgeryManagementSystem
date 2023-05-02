package com.app20222.app20222_backend.services.users.impl;

import com.app20222.app20222_backend.dtos.responses.users.IGetListUser;
import com.app20222.app20222_backend.repositories.users.UserRepository;
import com.app20222.app20222_backend.services.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<IGetListUser> getListUser() {
        return userRepository.getListUser();
    }
}
