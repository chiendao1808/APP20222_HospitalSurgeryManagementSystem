package com.app20222.app20222_fxapp.enums.apis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum APIDetails {

    HELLO("HELLO","/hello"),
    USER_LOGIN("USER_LOGIN","/login");

    private final String code;
    private final String path;
}
