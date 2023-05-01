package com.app20222.app20222_fxapp.app_controllers;

import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.dto.responses.BaseResponse;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        ObjectMapper mapper = new ObjectMapper();
        String message = "";
        try{
            HttpClient client = HttpClients.createDefault();
            String uri = ApiUtils.buildURI(APIDetails.HELLO.getPath(), new HashMap<>());
            System.out.println(uri);
            HttpGet getRequest = new HttpGet(uri);
            HttpResponse response = client.execute(getRequest);
            List<BaseResponse> lstResponse = mapper.readValue(IOUtils.toString(response.getEntity().getContent()), new TypeReference<>(){});
            message = lstResponse.stream().map(BaseResponse::getMessage).collect(Collectors.toList()).toString();
        } catch (Exception exception){
            exception.printStackTrace();
        }
        welcomeText.setText(message);
    }
}