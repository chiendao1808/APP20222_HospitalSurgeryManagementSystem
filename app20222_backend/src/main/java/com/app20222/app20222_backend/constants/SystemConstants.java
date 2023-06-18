package com.app20222.app20222_backend.constants;

import lombok.Data;

@Data
public class SystemConstants {

    public static final String BASE_PATH = System.getProperty("user.dir");

    public static final String RESOURCE_PATH = BASE_PATH + "/app20222_backend/src/main/resources/";

}
