package com.app20222.app20222_fxapp.enums.apis;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum APIDetails {

    HELLO("HELLO", "", "/hello", "GET"),

    // Auth APIs
    AUTH_LOGIN("AUTH_LOGIN", "/auth", "/login", "POST"),

    // User's APIs
    USER_GET_LIST("USER_GET_LIST", "/users", "", "GET");

    private final String code;
    private final String requestPath;
    private final String detailPath;
    private final String method;
}
