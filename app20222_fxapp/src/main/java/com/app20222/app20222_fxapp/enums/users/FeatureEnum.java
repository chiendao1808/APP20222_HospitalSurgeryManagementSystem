package com.app20222.app20222_fxapp.enums.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FeatureEnum {

    SYSTEM("SYSTEM", "NONE", "Hệ thống quản lý phẫu thuật trong bệnh viện"),

    // Chức năng lớn
    USER_MANAGEMENT("USER_MANAGEMENT", "SYSTEM", "Quản lý người dùng"),
    PATIENT_MANAGEMENT("PATIENT_MANAGEMENT", "SYSTEM", "Quản lý bệnh nhân"),
    MEDICAL_RECORD_MANAGEMENT("MEDICAL_RECORD_MANAGEMENT", "SYSTEM", "Quản lý hồ sơ bệnh án"),
    SURGERY_MANAGEMENT("SURGERY_MANAGEMENT", "SYSTEM", "Quản lý ca phẫu thuật"),
    SURGERY_ROOM_MANAGEMENT("SURGERY_ROOM_MANAGEMENT", "SYSTEM", "Quản lý phòng phẫu thuật"),

    // Chức năng con
    // Quản lý người dùng,
    USER_CREATE("USER_CREATE", "USER_MANAGEMENT", "Tạo người dùng"),
    USER_DETAILS("USER_DETAILS", "USER_MANAGEMENT", "Xem chi tiết người dùng"),
    USER_UPDATE("USER_UPDATE", "USER_MANAGEMENT", "Cập nhật thông tin người dùng"),
    USER_SEARCH("USER_SEARCH", "USER_MANAGEMENT", "Tìm kiếm người dùng"),

    // Quản lý bệnh nhân
    PATIENT_CREATE("PATIENT_CREATE", "PATIENT_MANAGEMENT", "Tạo hồ sơ bệnh nhân"),
    PATIENT_SEARCH("PATIENT_SEARCH", "PATIENT_MANAGEMENT", "Tìm kiếm bệnh nhân"),
    PATIENT_DETAILS("PATIENT_DETAILS", "PATIENT_MANAGEMENT", "Xem thông tin chi tiết bệnh nhân"),
    PATIENT_UPDATE("PATIENT_UPDATE", "PATIENT_MANAGEMENT", "Cập nhật hồ sơ bệnh nhân"),

    // Quản lý hồ sơ bệnh án
    MEDICAL_RECORD_CREATE("MEDICAL_RECORD_CREATE", "MEDICAL_RECORD_MANAGEMENT", "Tạo hồ sơ bệnh án"),
    MEDICAL_RECORD_SEARCH("MEDICAL_RECORD_SEARCH", "MEDICAL_RECORD_MANAGEMENT", "Tìm kiếm hồ sơ bệnh án"),
    MEDICAL_RECORD_UPDATE("MEDICAL_RECORD_UPDATE", "MEDICAL_RECORD_MANAGEMENT", "Cập nhật hồ sơ bệnh án"),

    // Quản lý ca phẫu thuật
    SURGERY_CREATE("SURGERY_CREATE", "SURGERY_MANAGEMENT", "Tạo ca phẫu thuật"),
    SURGERY_SEARCH("SURGERY_SEARCH", "SURGERY_MANAGEMENT", "Tìm kiếm ca phẫu thuật"),
    SURGERY_DETAILS("SURGERY_DETAILS", "SURGERY_MANAGEMENT", "Xem chi tiết ca phẫu thuật"),
    SURGERY_UPDATE("SURGERY_UPDATE", "SURGERY_MANAGEMENT", "Cập nhật ca phẫu thuật"),

    // Quản lý phòng phẫu thuật
    SURGERY_ROOM_CREATE("SURGERY_ROOM_CREATE", "SURGERY_ROOM_MANAGEMENT", "Tạo phòng phẫu thuật"),
    SURGERY_ROOM_SEARCH("SURGERY_ROOM_SEARCH", "SURGERY_ROOM_MANAGEMENT", "Tìm kiếm phòng phẫu thuật"),
    SURGERY_ROOM_UPDATE("SURGERY_ROOM_UPDATE", "SURGERY_ROOM_MANAGEMENT", "Cập nhật phòng phẫu thuật");

    private final String code;
    private final String parentCode;
    private final String name;
}
