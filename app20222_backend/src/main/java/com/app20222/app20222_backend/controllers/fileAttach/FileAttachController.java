package com.app20222.app20222_backend.controllers.fileAttach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.app20222.app20222_backend.dtos.file_attach.FileUploadResDTO;
import com.app20222.app20222_backend.enums.file_attach.FileTypeEnum;
import com.app20222.app20222_backend.services.fileAttach.FileAttachService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/file-attach")
@Slf4j
public class FileAttachController {

    @Autowired
    private FileAttachService fileAttachService;

    @PostMapping(value = "/upload-doc", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(description = "Upload document files")
    public FileUploadResDTO uploadDocFile(@RequestParam(name = "file") MultipartFile multipartFile){
        return fileAttachService.uploadDocument(multipartFile, FileTypeEnum.DOCUMENT);
    }

    @PostMapping(value = "/upload-img", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @Operation(description = "Upload image files")
    public FileUploadResDTO uploadImageFile(@RequestParam(name = "image") MultipartFile multipartFile) {
        return fileAttachService.uploadImage(multipartFile, FileTypeEnum.IMAGE);
    }

}
