package com.app20222.app20222_backend.dtos.responses.login;

import com.app20222.app20222_backend.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {

    String accessToken;

    String refreshToken;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM_SS, timezone = DateUtils.TIME_ZONE)
    Date issuedAt;

    Set<String> roles; // lst role của user đăng nhập

    Set<String> features; // danh sách tính năng user đăng nhập có quyền sử dụng
}
