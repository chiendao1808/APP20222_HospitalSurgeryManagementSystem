package com.app20222.app20222_fxapp.services.surgeryRoom;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.app20222.app20222_fxapp.dto.requests.surgeryRoom.SurgeryRoomCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.surgeryRoom.SurgeryRoomUpdateDTO;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.dto.responses.surgeryRoom.SurgeryRoomListDTO;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;

public class SurgeryRoomAPIService {

    /**
     * API Call getListSurgeryRoom
     */
    public List<SurgeryRoomListDTO> getListSurgeryRoom(Map<String, String> params) throws ApiResponseException {
        List<SurgeryRoomListDTO> surgeryRooms;
        String uri = ApiUtils.buildURI(APIDetails.SURGERY_ROOM_GET_LIST.getRequestPath() + APIDetails.SURGERY_ROOM_GET_LIST.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.SURGERY_ROOM_GET_LIST.getMethod(), null, new HashMap<>());
        // api call successfully (status = 200)
        if (HttpUtils.isCallSuccessfully(response)) {
            surgeryRooms = HttpUtils.handleResponse(response, new TypeReference<>() {});
        } else {
            ExceptionResponse exceptionResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
            throw new ApiResponseException(exceptionResponse);
        }
        return surgeryRooms;
    }

    /**
     * API call createSurgeryRoom
     */
    public Boolean createSurgeryRoom(SurgeryRoomCreateDTO createDTO) {
        String uri = ApiUtils.buildURI(APIDetails.SURGERY_ROOM_CREATE.getRequestPath() + APIDetails.SURGERY_ROOM_CREATE.getDetailPath(), new HashMap<>());
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.SURGERY_ROOM_CREATE.getMethod(), createDTO, new HashMap<>());
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
     * API call updateSurgeryRoom
     */
    public Boolean updateSurgeryRoom(SurgeryRoomUpdateDTO updateDTO, Map<String, String> params) {
        String uri = ApiUtils.buildURI(APIDetails.SURGERY_ROOM_UPDATE.getRequestPath() + APIDetails.SURGERY_ROOM_UPDATE.getDetailPath(), params);
        HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.SURGERY_ROOM_UPDATE.getMethod(), updateDTO, new HashMap<>());
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
