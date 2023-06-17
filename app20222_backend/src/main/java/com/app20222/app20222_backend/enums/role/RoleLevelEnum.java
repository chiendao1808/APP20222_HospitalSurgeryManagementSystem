package com.app20222.app20222_backend.enums.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleLevelEnum {
    SYSTEM(0, "Hệ thống"),
    HOSPITAL_MANAGER(1, "Admin/Quản lý bệnh viện"),
    DEPARTMENT_MANAGER(2, "Admin/Quản lý khoa"),
    PERSONEL(3, "Nhân viên");

    private final Integer level;
    private final String levelName;
}
