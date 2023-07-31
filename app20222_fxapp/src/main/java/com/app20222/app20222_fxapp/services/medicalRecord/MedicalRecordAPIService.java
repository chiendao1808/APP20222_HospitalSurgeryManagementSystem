package com.app20222.app20222_fxapp.services.medicalRecord;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app20222.app20222_fxapp.dto.requests.medicalRecord.MedicalRecordCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.medicalRecord.MedicalRecordUpdateDTO;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordDetailsRes;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordListDTO;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class MedicalRecordAPIService {

    /**
     * API Call getListMedicalRecord
     */
    public List<MedicalRecordListDTO> getListMedicalRecord(Map<String, String> params) throws ApiResponseException {
        List<MedicalRecordListDTO> medicalRecords;
        String uri = ApiUtils.buildURI(APIDetails.MEDICAL_RECORD_GET_LIST.getRequestPath() + APIDetails.MEDICAL_RECORD_GET_LIST.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.MEDICAL_RECORD_GET_LIST.getMethod(), null, params);
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            medicalRecords = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return medicalRecords;
    }

    /**
     * API call createMedicalRecord
     */
    public Boolean createMedicalRecord(MedicalRecordCreateDTO createDTO) {
        String uri = ApiUtils.buildURI(APIDetails.MEDICAL_RECORD_CREATE.getRequestPath() + APIDetails.MEDICAL_RECORD_CREATE.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.MEDICAL_RECORD_CREATE.getMethod(), createDTO, new HashMap<>());
        Boolean isSuccess = HttpUtils.isCallSuccessfully(response);
        if (isSuccess) {
            System.out.println("response: Created successfully");
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return isSuccess;
    }

    /**
     * API call getDetailsMedicalRecord
     */
    public MedicalRecordDetailsRes getDetailsMedicalRecord(Map<String, String> params) {
        MedicalRecordDetailsRes medicalRecord;
        String uri = ApiUtils.buildURI(APIDetails.MEDICAL_RECORD_GET_DETAILS.getRequestPath() + APIDetails.MEDICAL_RECORD_GET_DETAILS.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.MEDICAL_RECORD_GET_DETAILS.getMethod(), null, params);
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            medicalRecord = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return medicalRecord;
    }

    /**
     * API call updateMedicalRecord
     */
    public Boolean updateMedicalRecord(MedicalRecordUpdateDTO updateDTO) {
        String uri = ApiUtils.buildURI(APIDetails.MEDICAL_RECORD_UPDATE.getRequestPath() + APIDetails.MEDICAL_RECORD_UPDATE.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.MEDICAL_RECORD_UPDATE.getMethod(), updateDTO, new HashMap<>());
        Boolean isSuccess = HttpUtils.isCallSuccessfully(response);
        if (isSuccess) {
            System.out.println("response: Updated successfully");
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }

        return isSuccess;
    }

}
