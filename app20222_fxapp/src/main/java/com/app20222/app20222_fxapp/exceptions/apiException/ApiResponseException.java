package com.app20222.app20222_fxapp.exceptions.apiException;

import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
public class ApiResponseException extends RuntimeException {
    private ExceptionResponse exceptionResponse;

}
