package com.app20222.app20222_fxapp.services.dashboard;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.dto.responses.statistics.NumberOfSurgeryDiseaseGroupDTO;
import com.app20222.app20222_fxapp.dto.responses.statistics.NumberSurgeryPreviewDTO;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryGetListDTO;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class DashboardAPIService {

    /**
     * API Call previewSurgeryList
     */
    public List<SurgeryGetListDTO> previewListSurgery(Map<String, String> params) throws ApiResponseException {
        List<SurgeryGetListDTO> previewList;
        String uri = ApiUtils
            .buildURI(APIDetails.STATISTICS_PREVIEW_LIST_SURGERY.getRequestPath() + APIDetails.STATISTICS_PREVIEW_LIST_SURGERY.getDetailPath(),
                params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.STATISTICS_PREVIEW_LIST_SURGERY.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            previewList = HttpUtils.handleResponse(response, new TypeReference<>() {
            });
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {
            });
            throw new ApiResponseException(exceptionResponse);
        }
        return previewList;
    }


    /**
     * API Call previewNumberSurgery
     */
    public NumberSurgeryPreviewDTO previewNumberSurgery() throws ApiResponseException {
        NumberSurgeryPreviewDTO numberSurgeryPreview;
        String uri = ApiUtils.buildURI(
            APIDetails.STATISTICS_PREVIEW_NUMBER_SURGERY.getRequestPath() + APIDetails.STATISTICS_PREVIEW_NUMBER_SURGERY.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils
            .doRequest(uri, APIDetails.STATISTICS_PREVIEW_NUMBER_SURGERY.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            numberSurgeryPreview = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return numberSurgeryPreview;
    }

    /**
     * API Call exportListSurgery
     */
    public String exportListSurgery(Map<String, String> params) throws ApiResponseException {
        String csvRes;
        String uri = ApiUtils.buildURI(
            APIDetails.STATISTICS_EXPORT_PREVIEW_SURGERY.getRequestPath() + APIDetails.STATISTICS_EXPORT_PREVIEW_SURGERY.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils
            .doRequest(uri, APIDetails.STATISTICS_EXPORT_PREVIEW_SURGERY.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            csvRes = response.body();
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return csvRes;
    }

    /**
     * API Call getNoOfSurgeryGroupByDisease
     */
    public List<NumberOfSurgeryDiseaseGroupDTO> getNoOfSurgeryGroupByDisease(Map<String, String> params) throws ApiResponseException {
        List<NumberOfSurgeryDiseaseGroupDTO> numberSurgery;
        String uri = ApiUtils.buildURI(
            APIDetails.STATISTICS_GET_NO_SURGERY_GROUP_BY_DISEASE.getRequestPath() + APIDetails.STATISTICS_GET_NO_SURGERY_GROUP_BY_DISEASE.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils
            .doRequest(uri, APIDetails.STATISTICS_GET_NO_SURGERY_GROUP_BY_DISEASE.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            numberSurgery = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return numberSurgery;
    }


}
