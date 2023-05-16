package com.app20222.app20222_fxapp.app_controllers.login_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.context.AppContext;
import com.app20222.app20222_fxapp.dto.requests.auth.LoginRequest;
import com.app20222.app20222_fxapp.dto.responses.auth.LoginResponse;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.HttpMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class LoginViewController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSceneMain(ActionEvent event) throws IOException {
        login("admin", "admin");
        root = FXMLLoader.load(MainApplication.class.getResource("views/main_view/main-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Hospital-Systems");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Hàm đăng nhập -> lấy token
     */
    private Integer login(String username, String password){
      try{
          String apiPath = APIDetails.AUTH_LOGIN.getRequestPath() + APIDetails.AUTH_LOGIN.getDetailPath();
          String uri = ApiUtils.buildURI(apiPath, new HashMap<>());
          HttpResponse response = HttpUtils.doRequest(uri, HttpMethods.POST, new LoginRequest(username, password), new HashMap<>());
          // Check login response
          if(response.statusCode() == 200 || response.statusCode() == 201){
              LoginResponse loginResponse = HttpUtils.mappingResponseBody(response, new TypeReference<LoginResponse>(){});
              AppContext.ACCESS_TOKEN = loginResponse.getAccessToken();
              return response.statusCode();
          }
      }catch (Exception exception){
          exception.printStackTrace();
          return null;
      }
      return null;
    }

}
