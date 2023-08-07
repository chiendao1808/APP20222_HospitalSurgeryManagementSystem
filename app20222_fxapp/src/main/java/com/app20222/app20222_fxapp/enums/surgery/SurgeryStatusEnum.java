package com.app20222.app20222_fxapp.enums.surgery;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SurgeryStatusEnum {

    ALL(-1, "Tất cả"),
    REMAINING(0, "Chờ thực hiện"),
    OPERATING(1, "Đang được thực hiện"),
    COMPLETED(2, "Đã được thực hiện"),
    CANCELED(3, "Đã hủy");

    private final Integer value;
    private final String status;
}
