package com.app20222.app20222_fxapp.enums.fileAttach;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FileExtensionEnum {

    PDF("pdf", "*.pdf"),
    DOC("doc", "*.doc"),
    DOCX("docx", "*.docx"),
    XLS("xls", "*.xls"),
    XLSX("xlsx", "*.xlsx"),
    PPT("ppt", "*.ppt"),
    PPTX("pptx", "*.pptx"),
    JPG("jpg", "*.jpg"),
    PNG("png", "*.png"),
    TXT("txt", "*.txt");

    private final String extension;
    private final String pattern;

    public static final Map<String, FileExtensionEnum> mapExtensions = new HashMap<>();

    static {
        for(FileExtensionEnum fileExtension: FileExtensionEnum.values()){
            mapExtensions.put(fileExtension.getExtension(), fileExtension);
        }
    }

}
