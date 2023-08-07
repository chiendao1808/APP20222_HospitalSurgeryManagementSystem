package com.app20222.app20222_fxapp.app_controllers.login_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.context.ApplicationContext;
import com.app20222.app20222_fxapp.dto.requests.auth.LoginRequest;
import com.app20222.app20222_fxapp.dto.responses.auth.LoginResponse;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.HttpMethods;
import com.google.api.client.http.HttpStatusCodes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Objects;

public class LoginController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField plainPassword;

    @FXML
    private CheckBox passwordVisibilityCheckbox;

    @FXML
    private Button loginBtn;


    /**
     * Hàm sử lý khi login vào màn home
     */
    public void switchToSceneMain(ActionEvent event) throws IOException {
        Alert alert;
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
            alert.showAndWait();
        } else {
            Integer check = login(username.getText(), password.getText());
            if (Objects.equals(check, HttpStatusCodes.STATUS_CODE_OK)) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Đăng nhập thành công !");
                alert.showAndWait();
                //   Ẩn màn login
                loginBtn.getScene().getWindow().hide();
                root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("views/main_view/main-view.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Hệ thống quản lý phẫu thuật");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.centerOnScreen();
                stage.show();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Thông báo");
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Sai tài khoản hoặc mật khẩu!");
                alert.showAndWait();
            }
        }


    }

    /**
     * Hàm sử lý ẩn hiện mật khẩu
     */
    @FXML
    public void togglePasswordVisibility(ActionEvent event) {
        if (passwordVisibilityCheckbox.isSelected()) {
            password.setVisible(false);
            plainPassword.setVisible(true);
            plainPassword.setText(password.getText());
        } else {
            password.setVisible(true);
            plainPassword.setVisible(false);
            password.setText(plainPassword.getText());
        }

    }

    /**
     * Hàm đăng nhập -> lấy token
     */
    private Integer login(String username, String password) {
        try {
            String apiPath = APIDetails.AUTH_LOGIN.getRequestPath() + APIDetails.AUTH_LOGIN.getDetailPath();
            String uri = ApiUtils.buildURI(apiPath, new HashMap<>());
            HttpResponse<String> response = HttpUtils.doRequest(uri, HttpMethods.POST, new LoginRequest(username, password), new HashMap<>());
            // Check login response
            // Logic xử lý response
            LoginResponse loginResponse;
            if (Objects.nonNull(response)) {
                // Login success
                if (Objects.equals(response.statusCode(), HttpStatusCodes.STATUS_CODE_OK)) {
                    loginResponse = HttpUtils.handleResponse(response, new TypeReference<>() {});
                    // Log in success then set authentication info in application context
                    ApplicationContext.accessToken = loginResponse.getAccessToken();
                    ApplicationContext.refreshToken = loginResponse.getRefreshToken();
                    ApplicationContext.roles = loginResponse.getRoles();
                    ApplicationContext.features = loginResponse.getFeatures();
                    ApplicationContext.lastLoginUsername = username;
                    ApplicationContext.lastLoginPassword = password;
                } else { // Login fail
                    System.out.println(HttpUtils.handleResponse(response, new TypeReference<ExceptionResponse>() {}));
                }
                return response.statusCode();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return -1;
    }

}
