package com.app20222.app20222_backend.services.fileAttach;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.app20222.app20222_backend.dtos.fileAttach.FileUploadResDTO;
import com.app20222.app20222_backend.enums.file_attach.FileTypeEnum;

@Service
public interface FileAttachService {


    /**
     * Upload image file
     */
    FileUploadResDTO uploadImage(MultipartFile multipartFile, FileTypeEnum fileType);

    /**
     * Upload document file
     */
    FileUploadResDTO uploadDocument(MultipartFile multipartFile, FileTypeEnum fileType);

}
