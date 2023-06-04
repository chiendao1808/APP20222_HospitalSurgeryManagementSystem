package com.app20222.app20222_backend.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException {

    String code;

    String message;

    String fieldError;

    String values;

    public BaseException(String code, String message, String fieldError, String... values) {
        this.code = code;
        this.message = message;
        this.fieldError = fieldError;
        this.values = String.join(",", values);
    }

    public BaseException(String code, String resource, String message){
        this.code = code;
        this.message = message;
    }
}
