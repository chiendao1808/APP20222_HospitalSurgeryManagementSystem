package com.app20222.app20222_backend.services.users;

import com.app20222.app20222_backend.dtos.users.IGetListUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<IGetListUser> getListUser(String code, String name, String email, String phone, Long departmentId, Long roleId);
}
