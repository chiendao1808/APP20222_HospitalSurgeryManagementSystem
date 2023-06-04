package com.app20222.app20222_backend.exceptions;

import java.time.LocalDateTime;
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

    private LocalDateTime timestamp = LocalDateTime.now();

    public ExceptionResponse(String code,Integer status, String message, String fieldError, LocalDateTime timestamp) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.fieldError = fieldError;
        this.timestamp = timestamp;
    }

    public ExceptionResponse(String code, Integer status, String message, LocalDateTime timestamp) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
