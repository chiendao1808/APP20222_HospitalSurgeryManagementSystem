package com.app20222.app20222_backend.exceptions;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class PermissionDeniedException extends BaseException {

    public PermissionDeniedException(String code, String resource, String message) {
        super(code, resource, message);
    }
}
