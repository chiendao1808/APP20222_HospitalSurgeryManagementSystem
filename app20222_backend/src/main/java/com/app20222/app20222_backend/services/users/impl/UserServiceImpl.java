package com.app20222.app20222_backend.services.users.impl;

import com.app20222.app20222_backend.dtos.responses.users.IGetListUser;
import com.app20222.app20222_backend.repositories.users.UserRepository;
import com.app20222.app20222_backend.services.permission.PermissionService;
import com.app20222.app20222_backend.services.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<IGetListUser> getListUser(String code, String name, String email, String phone, Long departmentId, Long roleId) {
        // Lấy danh sách viewable department id
        Set<Long> lstViewableDepartmentId = permissionService.getLstViewableDepartmentId();
        lstViewableDepartmentId.add(departmentId);
        return userRepository.getListUser(code, name, email, phone, lstViewableDepartmentId);
    }
}
