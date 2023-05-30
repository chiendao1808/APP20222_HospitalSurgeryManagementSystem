package com.app20222.app20222_backend.exceptions.ex_handlers;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.app20222.app20222_backend.exceptions.BadRequestException;
import com.app20222.app20222_backend.exceptions.ExceptionResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    public ExceptionResponse handlerBadRequestException(BadRequestException exception){
        return new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), LocalDateTime.now());
    }

}
