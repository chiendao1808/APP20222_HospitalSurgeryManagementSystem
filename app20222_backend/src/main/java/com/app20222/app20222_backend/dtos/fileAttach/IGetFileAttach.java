package com.app20222.app20222_backend.dtos.fileAttach;

import io.swagger.v3.oas.annotations.media.Schema;

public interface IGetFileAttach {
    @Schema(description = "File's id")
    Long getFileId();

    @Schema(description = "File's name")
    String getFileName();

    @Schema(description = "File's location")
    String getLocation();

}
