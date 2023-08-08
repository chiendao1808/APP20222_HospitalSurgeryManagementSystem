package com.app20222.app20222_fxapp.enums.users;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.util.Objects;


@AllArgsConstructor
@Getter
public enum RoleEnum {

    SUPER_ADMIN(1L, "ROLE_SUPER_ADMIN", "SUPER_ADMIN"),
    HOSPITAL_ADMIN(2L, "ROLE_HOSPITAL_ADMIN", "HOSPITAL_ADMIN"),
    HOSPITAL_MANAGER(3L, "ROLE_HOSPITAL_MANAGER", "HOSPITAL_MANAGER"),
    DEPARTMENT_ADMIN(4L, "ROLE_DEPARTMENT_ADMIN", "DEPARTMENT_ADMIN"),
    DEPARTMENT_MANAGER(5L, "ROLE_DEPARTMENT_MANAGER", "DEPARTMENT_MANAGER"),
    DOCTOR(6L, "ROLE_DOCTOR", "DOCTOR"),
    NURSE(7L, "ROLE_NURSE", "NURSE"),
    STAFF(8L, "ROLE_STAFF", "STAFF");

    private final Long id;
    private final String roleCode;
    private final String roleName;

    /**
     * @param value : int type
     * @return : enum type
     */
    public static RoleEnum valueOf(Integer value) {
        if (Objects.isNull(value)) return null;
        switch (value) {
            case 1:
                return SUPER_ADMIN;
            case 2:
                return HOSPITAL_ADMIN;
            case 3:
                return HOSPITAL_MANAGER;
            case 4 :
                return DEPARTMENT_ADMIN;
            case 5 :
                return DEPARTMENT_MANAGER;
            case 6 :
                return DOCTOR;
            case 7 :
                return NURSE;
            case 8:
                return STAFF;
            default:
                return null;
        }
    }
    /**
     * @param value : string type
     * @return : enum type
     */
    public static RoleEnum typeOf(String value) {
        if (Objects.isNull(value)) return null;
        switch (value) {
            case "SUPER_ADMIN":
                return SUPER_ADMIN;
            case "HOSPITAL_ADMIN":
                return HOSPITAL_ADMIN;
            case "HOSPITAL_MANAGER":
                return HOSPITAL_MANAGER;
            case "DEPARTMENT_ADMIN":
                return DEPARTMENT_ADMIN;
            case  "DEPARTMENT_MANAGER":
                return DEPARTMENT_MANAGER;
            case  "DOCTOR":
                return DOCTOR;
            case  "NURSE" :
                return NURSE;
            case "STAFF" :
                return STAFF;
            default:
                return null;
        }
    }
}

