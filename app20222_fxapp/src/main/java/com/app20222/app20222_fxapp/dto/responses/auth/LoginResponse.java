package com.app20222.app20222_fxapp.dto.responses.auth;

import com.app20222.app20222_fxapp.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {

    String accessToken;

    String refreshToken;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM_SS, timezone = DateUtils.TIME_ZONE)
    Date issuedAt;
}