package com.app20222.app20222_backend.services.fileAttach.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.app20222.app20222_backend.constants.SystemConstants;
import com.app20222.app20222_backend.constants.message.errorKey.ErrorKey;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst;
import com.app20222.app20222_backend.constants.message.messageConst.MessageConst.Resources;
import com.app20222.app20222_backend.dtos.fileAttach.FileUploadResDTO;
import com.app20222.app20222_backend.entities.fileAttach.FileAttach;
import com.app20222.app20222_backend.enums.file_attach.FileStoredTypeEnum;
import com.app20222.app20222_backend.enums.file_attach.FileTypeEnum;
import com.app20222.app20222_backend.exceptions.exceptionFactory.ExceptionFactory;
import com.app20222.app20222_backend.repositories.fileAttach.FileAttachRepository;
import com.app20222.app20222_backend.services.fileAttach.FileAttachService;
import com.app20222.app20222_backend.utils.auth.AuthUtils;
import com.app20222.app20222_backend.utils.file.FileUtils;
import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileAttachServiceImpl implements FileAttachService {

    private final String IMAGES_RESOURCE_DIR = "/resources/upload/files/images/";

    public static final String IMAGES_STORED_LOCATION = "upload/files/images/";

    private final String DOCUMENTS_RESOURCE_DIR = "/resources/upload/files/docs/";

    public static final String DOCUMENTS_STORED_LOCATION = "upload/files/docs/";

    private final Cloudinary cloudinary;

    private final FileAttachRepository fileAttachRepository;

    private final ExceptionFactory exceptionFactory;


    public FileAttachServiceImpl(Cloudinary cloudinary, FileAttachRepository fileAttachRepository, ExceptionFactory exceptionFactory) {
        this.cloudinary = cloudinary;
        this.fileAttachRepository = fileAttachRepository;
        this.exceptionFactory = exceptionFactory;
    }


    @Override
    public FileUploadResDTO uploadImage(MultipartFile multipartFile, FileTypeEnum fileType) {
        try {
            File uploadFile = FileUtils.covertMultipartToFile(SystemConstants.RESOURCE_PATH + IMAGES_STORED_LOCATION, multipartFile);
            // Map option upload file cloudinary
            Map<String, Object> mapOptions = new HashMap<>();
            mapOptions.put("use_filename", true);
            mapOptions.put("resource_type", "image");
            Map<String, Object> uploadResponse = cloudinary.uploader().upload(uploadFile, mapOptions);
            String location = (String)uploadResponse.get("secure_url");
            if (Objects.isNull(uploadFile) || Objects.isNull(location)) {
                throw exceptionFactory.fileUploadException(ErrorKey.FileAttach.UPLOAD_ERROR_CODE, Resources.FILES, MessageConst.UPLOAD_FAILED);
            }

            // Creat file attach in db
            FileAttach fileAttach = FileAttach.builder()
                .fileExtension(FileUtils.getFileExt(uploadFile))
                .fileType(fileType.getType())
                .fileName(uploadFile.getName())
                .location(location)
                .storedType(FileStoredTypeEnum.EXTERNAL_SERVER.getType())
                .createdAt(LocalDateTime.now())
                .createdBy(AuthUtils.getCurrentUserId())
                .build();
            fileAttach = fileAttachRepository.save(fileAttach);
            // Delete file after upload cloudinary
            uploadFile.delete();
            return new FileUploadResDTO(fileAttach.getId(), fileAttach.getFileName(), fileAttach.getLocation());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public FileUploadResDTO uploadDocument(MultipartFile multipartFile, FileTypeEnum fileType) {
        // generate file name
        File uploadFile = FileUtils.covertMultipartToFile(SystemConstants.RESOURCE_PATH + DOCUMENTS_STORED_LOCATION, multipartFile);
        if (Objects.isNull(uploadFile)) {
            throw exceptionFactory.fileUploadException(ErrorKey.FileAttach.UPLOAD_ERROR_CODE, Resources.FILES, MessageConst.UPLOAD_FAILED);
        }
        String location = DOCUMENTS_RESOURCE_DIR + uploadFile.getName();

        // create file attach
        FileAttach fileAttach = FileAttach.builder()
            .fileExtension(FileUtils.getFileExt(uploadFile))
            .fileType(fileType.getType())
            .fileName(uploadFile.getName())
            .location(location)
            .storedType(FileStoredTypeEnum.INTERNAL_SERVER.getType())
            .createdAt(LocalDateTime.now())
            .createdBy(AuthUtils.getCurrentUserId())
            .build();
        fileAttach = fileAttachRepository.save(fileAttach);
        return new FileUploadResDTO(fileAttach.getId(), fileAttach.getFileName(), fileAttach.getLocation());
    }
}
