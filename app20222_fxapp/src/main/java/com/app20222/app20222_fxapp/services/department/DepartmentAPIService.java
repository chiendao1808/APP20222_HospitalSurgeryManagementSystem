package com.app20222.app20222_fxapp.services.department;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app20222.app20222_fxapp.dto.requests.department.DepartmentCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.department.DepartmentUpdateDTO;
import com.app20222.app20222_fxapp.dto.responses.deparment.DepartmentListDTO;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class DepartmentAPIService {

    /**
     * API Call getListDepartment
     */
    public List<DepartmentListDTO> getListDepartment(Map<String, String> params) throws ApiResponseException {
        List<DepartmentListDTO> departments;
        String uri = ApiUtils.buildURI(APIDetails.DEPARTMENT_GET_LIST.getRequestPath() + APIDetails.DEPARTMENT_GET_LIST.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.DEPARTMENT_GET_LIST.getMethod(), null, params);
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            departments = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return departments;
    }

    /**
     * API call createDepartment
     */
    public Boolean createDepartment(DepartmentCreateDTO createDTO) {
        String uri = ApiUtils.buildURI(APIDetails.DEPARTMENT_CREATE.getRequestPath() + APIDetails.DEPARTMENT_CREATE.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.DEPARTMENT_CREATE.getMethod(), createDTO, new HashMap<>());
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
     * API call updateDepartment
     */
    public Boolean updateDepartment(DepartmentUpdateDTO updateDTO) {
        String uri = ApiUtils.buildURI(APIDetails.DEPARTMENT_UPDATE.getRequestPath() + APIDetails.DEPARTMENT_UPDATE.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.DEPARTMENT_UPDATE.getMethod(), updateDTO, new HashMap<>());
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
