package com.app20222.app20222_fxapp.enums.fileAttach;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileTypeEnum {

    IMAGE(0, "File ảnh"),
    DOCUMENT(1, "File tài liệu");

    private final Integer type;
    private final String typeName;
}
