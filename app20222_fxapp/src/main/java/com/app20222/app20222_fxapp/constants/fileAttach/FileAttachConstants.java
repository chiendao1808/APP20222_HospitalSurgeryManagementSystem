package com.app20222.app20222_fxapp.constants.fileAttach;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileAttachConstants {

    public static final FileChooser.ExtensionFilter[] lstExtensionFilters = {
        new FileChooser.ExtensionFilter("pdf", "*.pdf"),
        new FileChooser.ExtensionFilter("doc", "*.doc"),
        new FileChooser.ExtensionFilter("docx", "*.docx"),
        new FileChooser.ExtensionFilter("xls", "*.xls"),
        new FileChooser.ExtensionFilter("xlsx", "*.xlsx"),
        new FileChooser.ExtensionFilter("ppt", "*.ppt"),
        new FileChooser.ExtensionFilter("pptx", "*.pptx"),
        new FileChooser.ExtensionFilter("jpg", "*.jpg"),
        new FileChooser.ExtensionFilter("png", "*.png"),
        new FileChooser.ExtensionFilter("txt", "*.txt")
    };

    public static final Map<String, ExtensionFilter> mapExtensionFilters = new HashMap<>();

    static {
        for(FileChooser.ExtensionFilter extensionFilter : lstExtensionFilters){
            mapExtensionFilters.put(extensionFilter.getDescription().toLowerCase(), extensionFilter);
        }
    }

    public static final String DEFAULT_USER_DIR = System.getProperty("user.home");

    public static final String DEFAULT_DOWNLOAD_FOLDER = DEFAULT_USER_DIR + "\\Downloads";

}
