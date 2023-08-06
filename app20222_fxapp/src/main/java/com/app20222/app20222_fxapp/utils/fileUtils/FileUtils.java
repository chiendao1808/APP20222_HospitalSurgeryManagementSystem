package com.app20222.app20222_fxapp.utils.fileUtils;

import java.util.Objects;

public class FileUtils {

    /**
     * Get file extension
     */
    public static String getFileExtension(String fileName) {
        if(Objects.isNull(fileName) || fileName.length() == 0) return "";
        Integer indexOfDot = fileName.lastIndexOf(".");
        return fileName.substring(indexOfDot + 1);
    }

}
