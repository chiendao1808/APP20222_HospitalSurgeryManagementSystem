package com.app20222.app20222_backend.enums.permission;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BasePermissionEnum {

    VIEW("VIEW"), EDIT("EDIT"), DELETE("DELETE");

    private final String value;
}
