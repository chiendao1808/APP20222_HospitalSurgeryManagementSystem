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
    USER_GET_LIST("USER_GET_LIST", "/users", "", "GET"),
    USER_CREATE("USER_CREATE", "/users", "", "POST"),
    USER_UPDATE("USER_UPDATE", "/users", "", "PUT"),
    USER_GET_DETAIL("USER_GET_DETAIL", "/users", "/get-detail", "GET"),

    // Department's
    DEPARTMENT_CREATE("DEPARTMENT_CREATE", "/department", "", "POST"),
    DEPARTMENT_UPDATE("DEPARTMENT_UPDATE", "/department", "", "PUT"),


    // Surgery's APIs
    SURGERY_GET_LIST("SURGERY_GET_LIST", "/surgery","/get-list", "GET"),
    SURGERY_CREATE("SURGERY_CREATE", "/surgery","", "POST"),
    SURGERY_UPDATE("SURGERY_UPDATE", "/surgery","/update", "PUT"),
    SURGERY_DETAILS("SURGERY_DETAILS", "/surgery", "/get-details", "GET"),
    SURGERY_DELETE("SURGERY_DELETE", "/surgery", "/delete", "DELETE"),

    // Surgery Room's APIs
    SURGERY_ROOM_GET_LIST("SURGERY_ROOM_GET_LIST", "/surgery-room","/get-list", "GET"),
    SURGERY_ROOM_CREATE("SURGERY_ROOM_CREATE", "/surgery-room","/", "POST"),
    SURGERY_ROOM_UPDATE("SURGERY_ROOM_CREATE", "/surgery-room","/", "PUT"),

    // Patient's APIs
    PATIENT_GET_LIST("PATIENT_GET_LIST","/patient", "/get-list", "GET"),
    PATIENT_GET_DETAILS("PATIENT_GET_DETAIL","/patient", "/get-detail", "GET"),
    PATIENT_CREATE("PATIENT_CREATE", "/patient", "/", "POST"),
    PATIENT_UPDATE("PATIENT_UPDATE", "/patient", "/", "PUT"),

    // Medical Record
    MEDICAL_RECORD_CREATE("MEDICAL_RECORD_CREATE","/medical-record", "", "POST"),
    MEDICAL_RECORD_GET_LIST("MEDICAL_RECORD_GET_LIST","/medical-record", "/get-list", "GET"),
    MEDICAL_RECORD_GET_DETAILS("MEDICAL_RECORD_GET_DETAIL","/medical-record", "/get-detail", "GET"),
    MEDICAL_RECORD_UPDATE("MEDICAL_RECORD_UPDATE", "/medical-record", "/", "PUT");


    private final String code;
    private final String requestPath;
    private final String detailPath;
    private final String method;
}
