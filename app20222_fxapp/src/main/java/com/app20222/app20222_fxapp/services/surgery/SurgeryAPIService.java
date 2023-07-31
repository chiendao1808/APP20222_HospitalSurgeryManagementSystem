package com.app20222.app20222_fxapp.services.surgery;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app20222.app20222_fxapp.dto.requests.surgery.SurgeryCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.surgery.SurgeryUpdateDTO;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryDetailDTO;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryGetListDTO;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class SurgeryAPIService {

    /**
     * API Call getListSurgery
     */
    public List<SurgeryGetListDTO> getListSurgery(Map<String, String> params) throws ApiResponseException {
        List<SurgeryGetListDTO> surgeries;
        String uri = ApiUtils.buildURI(APIDetails.SURGERY_GET_LIST.getRequestPath() + APIDetails.SURGERY_GET_LIST.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.SURGERY_GET_LIST.getMethod(), null, params);
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            surgeries = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return surgeries;
    }

    /**
     * API call createSurgery
     */
    public Boolean createSurgery(SurgeryCreateDTO createDTO) throws ApiResponseException {
        String uri = ApiUtils.buildURI(APIDetails.SURGERY_CREATE.getRequestPath() + APIDetails.SURGERY_CREATE.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.SURGERY_CREATE.getMethod(), createDTO, new HashMap<>());
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
     * API call getDetailsSurgery
     */
    public SurgeryDetailDTO getDetailsSurgery(Map<String, String> params) {
        SurgeryDetailDTO surgery;
        String uri = ApiUtils.buildURI(APIDetails.SURGERY_DETAILS.getRequestPath() + APIDetails.SURGERY_DETAILS.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.SURGERY_DETAILS.getMethod(), null, params);
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            surgery = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return surgery;
    }

    /**
     * API call updateSurgery
     */
    public Boolean updateSurgery(SurgeryUpdateDTO updateDTO) {
        String uri = ApiUtils.buildURI(APIDetails.SURGERY_UPDATE.getRequestPath() + APIDetails.SURGERY_UPDATE.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.SURGERY_UPDATE.getMethod(), updateDTO, new HashMap<>());
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
