package com.app20222.app20222_fxapp.app_controllers;

import com.app20222.app20222_fxapp.dto.responses.BaseResponse;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.HttpMethods;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        String message = "";
        try{
            String apiPath = APIDetails.HELLO.getRequestPath() + APIDetails.HELLO.getDetailPath();
            String uri = ApiUtils.buildURI(apiPath, new HashMap<>());
            List<BaseResponse> lstResponse = HttpUtils.doRequest(uri, HttpMethods.GET, new TypeReference<>(){}, new Object());
            message = Objects.requireNonNull(lstResponse).stream().map(BaseResponse::getMessage).collect(Collectors.toList()).toString();
        } catch (Exception exception){
            exception.printStackTrace();
        }
        welcomeText.setText(message);
    }
}