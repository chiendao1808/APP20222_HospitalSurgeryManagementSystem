package com.app20222.app20222_fxapp.dto.responses.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionResponse {
    private String code;

    private Integer status;

    private String message;

    private String fieldError;

    private Long timestamp;

}
