package com.app20222.app20222_backend.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseResponse {

    Integer code;

    String message;

    Date timestamp;

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.timestamp = new Date();
    }
}
