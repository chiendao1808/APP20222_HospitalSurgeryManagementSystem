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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class LoginViewController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    public void switchToSceneMain(ActionEvent event) throws IOException {
        Alert alert;
        if(username.getText().isEmpty() || password.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Nhập đẩy đủ các trường");
            alert.showAndWait();
        } else {
            int check =  login(username.getText(), password.getText());
            if(check == 200) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Đăng nhập thành công");
                alert.showAndWait();
                loginBtn.getScene().getWindow().hide();
                root = FXMLLoader.load(MainApplication.class.getResource("views/main_view/main-view.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Hospital-Systems");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Sai tài khoản hoặc mật khẩu");
                alert.showAndWait();
            }
        }


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
          return -1;
      }
      return -1;
    }

}
