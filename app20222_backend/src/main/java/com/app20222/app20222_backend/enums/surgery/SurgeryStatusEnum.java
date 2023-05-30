package com.app20222.app20222_backend.enums.surgery;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SurgeryStatusEnum {

    REMAINING(0, "Chờ thực hiện"),
    OPERATING(1, "Đang được thực hiện"),
    COMPLETED(2, "Đã được thực hiện");

    private final Integer value;
    private final String status;
}
