package com.app20222.app20222_backend.enums.surgery;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SurgeryRoleTypeEnum {

    MAIN_DOCTOR(0, "Bác sĩ phẫu thuật chính"),
    ANAESTHETISE_DOCTOR(1, "Bác sĩ gây mê"),
    SUPPORT_DOCTOR(2, "Bác sĩ phẫu thuật hộ trợ"),
    SUPPORT_NURSE(3, "Y tá hỗ trợ"),
    RECORD_NURSE(4, "Nhân viên ghi tài liệu");

    private final Integer type;
    private final String typeName;

    public SurgeryRoleTypeEnum valueOf(Integer type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case 0:
                return MAIN_DOCTOR;
            case 1:
                return ANAESTHETISE_DOCTOR;
            case 2:
                return SUPPORT_DOCTOR;
            case 3:
                return SUPPORT_NURSE;
            case 4:
                return RECORD_NURSE;
            default:
                return null;
        }
    }
}
