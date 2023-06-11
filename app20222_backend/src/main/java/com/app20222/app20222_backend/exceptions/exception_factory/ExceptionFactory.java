package com.app20222.app20222_backend.exceptions.exception_factory;

import org.springframework.stereotype.Component;
import com.app20222.app20222_backend.exceptions.BadRequestException;
import com.app20222.app20222_backend.exceptions.PermissionDeniedException;
import com.app20222.app20222_backend.exceptions.ResourceExistedException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExceptionFactory {

    public BadRequestException resourceNotFoundException(String code, String messageConst, String resource, String fieldError, String... values)
        throws BadRequestException {
        StringBuilder builder = new StringBuilder();
        builder.append(resource).append(" ").append(messageConst);
        return new BadRequestException(code, builder.toString(), fieldError, values);
    }

    public BadRequestException overlappedException(String code, String messageConst, String resource, String fieldError, String... values) throws BadRequestException {
        StringBuilder builder = new StringBuilder();
        builder.append(resource).append(" ").append(messageConst);
        return new BadRequestException(code, builder.toString(), fieldError, values);
    }

    public PermissionDeniedException permissionDeniedException(String code, String resource, String message) throws PermissionDeniedException {
        return new PermissionDeniedException(code, resource, message);
    }

    public BadRequestException resourceExistedException(String code, String resource, String messageConst, String errorField, String... values) throws ResourceExistedException {
        StringBuilder builder = new StringBuilder();
        builder.append(resource).append(" ").append(messageConst);
        return new BadRequestException(code, builder.toString(), errorField, values);
    }
}
