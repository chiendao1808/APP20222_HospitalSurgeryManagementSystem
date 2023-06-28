package com.app20222.app20222_backend.exceptions;

import java.time.LocalDateTime;
import com.app20222.app20222_backend.utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionResponse {

    private String code;

    private Integer status;

    private String message;

    private String fieldError;

    private Long timestamp = DateUtils.getCurrentDateTime().getTime();

    public ExceptionResponse(String code,Integer status, String message, String fieldError) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.fieldError = fieldError;
    }

    public ExceptionResponse(String code, Integer status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
