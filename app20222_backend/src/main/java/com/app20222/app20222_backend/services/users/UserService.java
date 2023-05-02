package com.app20222.app20222_backend.services.users;

import com.app20222.app20222_backend.dtos.responses.users.IGetListUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<IGetListUser> getListUser();
}
