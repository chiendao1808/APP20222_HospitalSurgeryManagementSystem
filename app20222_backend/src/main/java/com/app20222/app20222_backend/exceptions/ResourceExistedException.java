package com.app20222.app20222_backend.exceptions;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class ResourceExistedException extends BaseException {


    public ResourceExistedException(String resource, String message, String errorField, String... values){
        super(resource, message, errorField, values);
    }

}
