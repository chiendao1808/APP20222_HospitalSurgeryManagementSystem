package com.app20222.app20222_backend.exceptions;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private Integer status;

    private String message;

    private String fieldError;

    private LocalDateTime timestamp = LocalDateTime.now();

    public ExceptionResponse(Integer status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
}
