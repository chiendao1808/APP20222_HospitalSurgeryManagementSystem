package com.app20222.app20222_fxapp.services.comboBox;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app20222.app20222_fxapp.dto.common.CommonIdCodeName;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryRoleTypeDTO;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class ComboBoxAPIService {


    /*
      ==== COMBO BOX USERS =================================================================
     */

    /**
     * API Call getComboBoxUser
     */
    public List<CommonIdCodeName> getComboBoxUsers(Map<String, String> params) throws ApiResponseException {
        List<CommonIdCodeName> comboBoxUsers;
        String uri = ApiUtils.buildURI(APIDetails.GET_COMBO_BOX_USER.getRequestPath() + APIDetails.GET_COMBO_BOX_USER.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.GET_COMBO_BOX_USER.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            comboBoxUsers = HttpUtils.handleResponse(response, new TypeReference<>() {
            });
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {
            });
            throw new ApiResponseException(exceptionResponse);
        }
        return comboBoxUsers;
    }

    /**
     * API Call getComboBoxRoles
     */
    public List<CommonIdCodeName> getComboBoxRoles(Map<String, String> params) throws ApiResponseException {
        List<CommonIdCodeName> comboBoxRoles;
        String uri = ApiUtils.buildURI(APIDetails.GET_COMBO_BOX_ROLE.getRequestPath() + APIDetails.GET_COMBO_BOX_ROLE.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.GET_COMBO_BOX_ROLE.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            comboBoxRoles = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return comboBoxRoles;
    }



    /*
      ==== COMBO BOX DEPARTMENT =================================================================
     */

    /**
     * API Call getComboBoxDepartments
     */
    public List<CommonIdCodeName> getComboBoxDepartments(Map<String, String> params) throws ApiResponseException {
        List<CommonIdCodeName> comboBoxDepartments;
        String uri = ApiUtils
            .buildURI(APIDetails.GET_COMBO_BOX_DEPARTMENT.getRequestPath() + APIDetails.GET_COMBO_BOX_DEPARTMENT.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.GET_COMBO_BOX_DEPARTMENT.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            comboBoxDepartments = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return comboBoxDepartments;
    }


    /*
      ==== COMBO BOX PATIENT =================================================================
     */

    /**
     * API Call getComboBoxPatient
     */
    public List<CommonIdCodeName> getComboBoxPatients(Map<String, String> params) throws ApiResponseException {
        List<CommonIdCodeName> comboBoxPatients;
        String uri = ApiUtils
            .buildURI(APIDetails.GET_COMBO_BOX_PATIENT.getRequestPath() + APIDetails.GET_COMBO_BOX_PATIENT.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.GET_COMBO_BOX_PATIENT.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            comboBoxPatients = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return comboBoxPatients;
    }


    /*
      ==== COMBO BOX SURGERY =================================================================
     */

    /**
     * API Call getComboBoxSurgeryRoleType
     */
    public List<SurgeryRoleTypeDTO> getComboBoxSurgeryRoleTypes(Map<String, String> params) throws ApiResponseException {
        List<SurgeryRoleTypeDTO> comboBoxSurgeryRoleTypes;
        String uri = ApiUtils
            .buildURI(APIDetails.GET_COMBO_BOX_SURGERY_ROLE_TYPE.getRequestPath() + APIDetails.GET_COMBO_BOX_SURGERY_ROLE_TYPE.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.GET_COMBO_BOX_SURGERY_ROLE_TYPE.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            comboBoxSurgeryRoleTypes = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return comboBoxSurgeryRoleTypes;
    }

    /*
      ==== COMBO BOX SURGERY ROOM =================================================================
     */

    /**
     * API Call getComboBoxSurgeryRooms
     */
    public List<CommonIdCodeName> getComboBoxSurgeryRooms(Map<String, String> params) throws ApiResponseException {
        List<CommonIdCodeName> comboBoxPatients;
        String uri = ApiUtils
            .buildURI(APIDetails.GET_COMBO_BOX_SURGERY_ROOM.getRequestPath() + APIDetails.GET_COMBO_BOX_SURGERY_ROOM.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.GET_COMBO_BOX_SURGERY_ROOM.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            comboBoxPatients = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return comboBoxPatients;
    }


}
