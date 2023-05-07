package com.app20222.app20222_fxapp.dto.requests.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

    @NotNull
    String username;

    @NotNull
    String password;
}
