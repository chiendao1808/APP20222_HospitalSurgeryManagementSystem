package com.app20222.app20222_backend.security.constants;

import lombok.Data;


@Data
public class SecurityConstants {

    public static final String BEARER_AUTH_SCHEME = "Bearer";

    public static final String API_KEY = "APP20222";

    public static final String AUTH_HEADER = "Authorization";

    public static final String[] LIST_PERMIT_ALL_API = {"/auth/login", "/hello"};

}
