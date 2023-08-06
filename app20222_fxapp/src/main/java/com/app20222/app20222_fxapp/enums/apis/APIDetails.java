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
    USER_SWITCH_STATUS("USER_SWITCH_STATUS", "/users", "/switch-status", "PUT"),
    USER_GET_PROFILE("USER_GET_PROFILE", "/users", "/profile", "GET"),

    // Department's
    DEPARTMENT_CREATE("DEPARTMENT_CREATE", "/department", "", "POST"),
    DEPARTMENT_UPDATE("DEPARTMENT_UPDATE", "/department", "", "PUT"),
    DEPARTMENT_GET_LIST("DEPARTMENT_GET_LIST", "/department", "/get-list", "GET"),
    DEPARTMENT_DELETE("DEPARTMENT_DELETE", "/department", "/", "DELETE"),


    // Surgery's APIs
    SURGERY_GET_LIST("SURGERY_GET_LIST", "/surgery","/get-list", "GET"),
    SURGERY_CREATE("SURGERY_CREATE", "/surgery","", "POST"),
    SURGERY_UPDATE("SURGERY_UPDATE", "/surgery","", "PUT"),
    SURGERY_DETAILS("SURGERY_DETAILS", "/surgery", "/get-details", "GET"),
    SURGERY_DELETE("SURGERY_DELETE", "/surgery", "/delete", "DELETE"),
    SURGERY_SWITCH_STATUS("SURGERY_SWITCH_STATUS", "/surgery", "/switch-status", "PUT"),

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
    MEDICAL_RECORD_UPDATE("MEDICAL_RECORD_UPDATE", "/medical-record", "/", "PUT"),

    // ComboBox
    GET_COMBO_BOX_USER("GET_COMBO_BOX_USER","/combo-box", "/users/get-lst-user-name-code", "GET"),
    GET_COMBO_BOX_ROLE("GET_COMBO_BOX_ROLE","/combo-box", "/role/get-lst-role-name-code", "GET"),
    GET_COMBO_BOX_DEPARTMENT("GET_COMBO_BOX_DEPARTMENT","/combo-box", "/department/get-lst-department-name-code", "GET"),
    GET_COMBO_BOX_PATIENT("GET_COMBO_BOX_PATIENT","/combo-box", "/patient/get-lst-patient-name-code", "GET"),
    GET_COMBO_BOX_SURGERY_ROLE_TYPE("GET_COMBO_BOX_SURGERY_ROLE_TYPE","/combo-box", "/surgery/get-lst-surgery-role-type", "GET"),
    GET_COMBO_BOX_SURGERY_ROOM("GET_COMBO_BOX_SURGERY_ROOM","/combo-box", "/surgery-room/get-surgery-room-name-code", "GET"),

    // Statistics
    STATISTICS_PREVIEW_NUMBER_SURGERY("STATISTICS_PREVIEW_NUMBER_SURGERY", "/statistics", "/preview-number-surgery", "GET"),
    STATISTICS_PREVIEW_STATISTICS_MONTHLY("STATISTICS_PREVIEW_STATISTICS_MONTHLY", "/statistics", "/preview-statistics-monthly", "GET"),
    STATISTICS_PREVIEW_LIST_SURGERY("STATISTICS_PREVIEW_LIST_SURGERY", "/statistics", "/preview-list-surgery", "GET"),
    STATISTICS_EXPORT_PREVIEW_SURGERY("STATISTICS_EXPORT_PREVIEW_SURGERY", "/statistics", "/export-preview-surgery", "GET"),

    //Upload file
    UPLOAD_DOCUMENT("UPLOAD_DOCUMENT", "/file-attach", "/upload-doc", "POST"),
    UPLOAD_IMAGE("UPLOAD_IMAGE", "/file-attach", "/upload-img", "POST");


    private final String code;
    private final String requestPath;
    private final String detailPath;
    private final String method;
}
