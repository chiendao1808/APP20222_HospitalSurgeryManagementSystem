package com.app20222.app20222_fxapp.services.users;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app20222.app20222_fxapp.dto.requests.users.UserCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.users.UserUpdateDTO;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.dto.responses.users.ProfileUserDTO;
import com.app20222.app20222_fxapp.dto.responses.users.UserDetailsDTO;
import com.app20222.app20222_fxapp.dto.responses.users.UserListDTO;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class UserAPIService {

    /**
     * API Call getListUser
     */
    public List<UserListDTO> getListUsers(Map<String, String> params) throws ApiResponseException {
        List<UserListDTO> users;
        String uri = ApiUtils.buildURI(APIDetails.USER_GET_LIST.getRequestPath() + APIDetails.USER_GET_LIST.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.USER_GET_LIST.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            users = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return users;
    }

    /**
     * API call createUser
     */
    public Boolean createUser(UserCreateDTO createDTO) {
        String uri = ApiUtils.buildURI(APIDetails.USER_CREATE.getRequestPath() + APIDetails.USER_CREATE.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.USER_CREATE.getMethod(), createDTO, new HashMap<>());
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
     * API call getDetailsUser
     */
    public UserDetailsDTO getDetailsUser(Map<String, String> params) {
        UserDetailsDTO user;
        String uri = ApiUtils.buildURI(APIDetails.USER_GET_DETAIL.getRequestPath() + APIDetails.USER_GET_DETAIL.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.USER_GET_DETAIL.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            user = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return user;
    }

    /**
     * API call updateUser
     */
    public Boolean updateUser(UserUpdateDTO updateDTO, Map<String, String> params) {
        String uri = ApiUtils.buildURI(APIDetails.USER_UPDATE.getRequestPath() + APIDetails.USER_UPDATE.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.USER_UPDATE.getMethod(), updateDTO, new HashMap<>());
        Boolean isSuccess = HttpUtils.isCallSuccessfully(response);
        if (isSuccess) {
            System.out.println("response: Updated successfully");
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return isSuccess;
    }

    /**
     * API call getProfileUser
     */
    public ProfileUserDTO getCurrentUserProfile() {
        ProfileUserDTO profile;
        String uri = ApiUtils.buildURI(APIDetails.USER_GET_PROFILE.getRequestPath() + APIDetails.USER_GET_PROFILE.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.USER_GET_PROFILE.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            profile = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return profile;
    }

}
