package com.app20222.app20222_fxapp.services.fileUpload;

import java.io.File;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import com.app20222.app20222_fxapp.dto.responses.FileUploadResDTO;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class FileUploadAPIService {

    /**
     * API Call uploadFileDoc
     */
    public FileUploadResDTO uploadFileDocument(File file, Map<String, String> headers) throws ApiResponseException {
        FileUploadResDTO resDTO;
        String uri = ApiUtils.buildURI(APIDetails.UPLOAD_DOCUMENT.getRequestPath() + APIDetails.UPLOAD_DOCUMENT.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils.doUploadFile(uri, file, headers);
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            resDTO = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return resDTO;
    }


    /**
     * API Call uploadFileImage
     */
    public FileUploadResDTO uploadFileImage(File file, Map<String, String> headers) throws ApiResponseException {
        FileUploadResDTO resDTO;
        String uri = ApiUtils.buildURI(APIDetails.UPLOAD_IMAGE.getRequestPath() + APIDetails.UPLOAD_IMAGE.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils.doUploadFile(uri, file, headers);
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            resDTO = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return resDTO;
    }



}
