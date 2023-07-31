package com.app20222.app20222_fxapp.services.patient;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.app20222.app20222_fxapp.dto.requests.patient.PatientCreateDTO;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListNewDTO;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpStatusCodes;

public class PatientAPIService {

    /**
     * API Call getListPatient
     */
    public List<PatientGetListNewDTO> getListPatient(Map<String, String> params) throws ApiResponseException {
        List<PatientGetListNewDTO> patients;
        String uri = ApiUtils.buildURI(APIDetails.PATIENT_GET_LIST.getRequestPath() + APIDetails.PATIENT_GET_LIST.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, HttpMethods.GET, null, new HashMap<>());
        // api call successfully (status = 200)
        if (Objects.nonNull(response) && Objects.equals(response.statusCode(), HttpStatusCodes.STATUS_CODE_OK)) {
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
    public Boolean createPatient(PatientCreateDTO createDTO){
        String uri = ApiUtils.buildURI(APIDetails.PATIENT_CREATE.getRequestPath() + APIDetails.PATIENT_CREATE.getDetailPath(),
            new HashMap<>());
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.PATIENT_CREATE.getMethod(), createDTO, new HashMap<>());
        if (Objects.isNull(response)) {
            return false;
        }
        if (Objects.equals(response.statusCode(), HttpStatusCodes.STATUS_CODE_OK)) {
            System.out.println("response" + response.body());
            return true;
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
    }


}
