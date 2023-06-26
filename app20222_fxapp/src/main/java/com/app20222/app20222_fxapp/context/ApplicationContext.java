package com.app20222.app20222_fxapp.context;

import java.util.HashSet;
import java.util.Set;

public class ApplicationContext {

    /**
     * Access Token
     */
    public static String accessToken = "";


    /**
     * Refresh Token
     */
    public static String refreshToken = "";

    /**
     * Role for authorization
     */
    public static Set<String> roles = new HashSet<>();

    /**
     * List usable features
     */
    public static Set<String> features = new HashSet<>();
}
