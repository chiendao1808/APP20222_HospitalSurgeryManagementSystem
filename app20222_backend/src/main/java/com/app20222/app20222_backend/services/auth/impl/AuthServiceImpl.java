package com.app20222.app20222_backend.services.auth.impl;

import java.time.LocalDateTime;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app20222.app20222_backend.entities.auth.AuthInfo;
import com.app20222.app20222_backend.repositories.auth.AuthInfoRepository;
import com.app20222.app20222_backend.security.jwt.JwtUtils;
import com.app20222.app20222_backend.services.auth.AuthInfoService;

@Service
public class AuthServiceImpl implements AuthInfoService {

    @Autowired
    private AuthInfoRepository authInfoRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public AuthInfo saveAuthInfo(Long userId, String token, String ip) {
        AuthInfo currentAuthInfo = authInfoRepository.findFirstByUserIdOrderByCreatedAtDesc(userId).orElse(null);
        boolean tokenIsValid = jwtUtils.validateToken(token);
        if(Objects.isNull(currentAuthInfo) || !tokenIsValid){
            if(Objects.nonNull(currentAuthInfo)){
                currentAuthInfo.setStatus(tokenIsValid ? 1 : 0); // set status invalid
                authInfoRepository.save(currentAuthInfo);
            }
            AuthInfo newAuthInfo = AuthInfo.builder().userId(userId).token(token).ipAddress(ip)
                .status(1).createdAt(LocalDateTime.now()).lastLoginAt(LocalDateTime.now()).build();
            return authInfoRepository.save(newAuthInfo);
        }
        currentAuthInfo.setLastLoginAt(LocalDateTime.now());
        return authInfoRepository.save(currentAuthInfo);
    }

    @Override
    public AuthInfo findByUserId(Long userId) {
        return authInfoRepository.findFirstByUserIdOrderByCreatedAtDesc(userId).orElse(null);
    }
}
