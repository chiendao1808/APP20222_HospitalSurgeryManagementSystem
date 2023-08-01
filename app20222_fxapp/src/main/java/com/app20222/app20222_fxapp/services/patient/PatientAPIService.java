package com.app20222.app20222_fxapp.services.patient;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app20222.app20222_fxapp.dto.requests.patient.PatientCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.patient.PatientUpdateDTO;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientDetailsDTO;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListNewDTO;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class PatientAPIService {

    /**
     * API Call getListPatient
     */
    public List<PatientGetListNewDTO> getListPatient(Map<String, String> params) throws ApiResponseException {
        List<PatientGetListNewDTO> patients;
        String uri = ApiUtils.buildURI(APIDetails.PATIENT_GET_LIST.getRequestPath() + APIDetails.PATIENT_GET_LIST.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.PATIENT_GET_LIST.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            patients = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return patients;
    }

    /**
     * API call createPatient
     */
    public Boolean createPatient(PatientCreateDTO createDTO) {
        String uri = ApiUtils.buildURI(APIDetails.PATIENT_CREATE.getRequestPath() + APIDetails.PATIENT_CREATE.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.PATIENT_CREATE.getMethod(), createDTO, new HashMap<>());
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
     * API call getDetailsPatient
     */
    public PatientDetailsDTO getDetailsPatient(Map<String, String> params) {
        PatientDetailsDTO patient;
        String uri = ApiUtils.buildURI(APIDetails.PATIENT_GET_DETAILS.getRequestPath() + APIDetails.PATIENT_GET_DETAILS.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.PATIENT_GET_DETAILS.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            patient = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return patient;
    }

    /**
     * API call updatePatient
     */
    public Boolean updatePatient(PatientUpdateDTO updateDTO, Map<String, String> params) {
        String uri = ApiUtils.buildURI(APIDetails.PATIENT_UPDATE.getRequestPath() + APIDetails.PATIENT_UPDATE.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.PATIENT_UPDATE.getMethod(), updateDTO, new HashMap<>());
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
