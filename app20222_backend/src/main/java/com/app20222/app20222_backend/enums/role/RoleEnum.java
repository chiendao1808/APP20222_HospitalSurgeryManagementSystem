package com.app20222.app20222_backend.enums.role;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
