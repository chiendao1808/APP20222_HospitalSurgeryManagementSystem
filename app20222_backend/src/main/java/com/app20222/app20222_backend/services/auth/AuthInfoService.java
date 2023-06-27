package com.app20222.app20222_backend.services.auth;

import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.entities.auth.AuthInfo;

@Service
public interface AuthInfoService {

    /**
     * Lưu thông tin xác thực người dùng
     */
    AuthInfo saveAuthInfo(Long userId, String token, String ip);

    /**
     * Tìm thông tin xác thực người dùng
     */
    AuthInfo findByUserId(Long userId);

}
