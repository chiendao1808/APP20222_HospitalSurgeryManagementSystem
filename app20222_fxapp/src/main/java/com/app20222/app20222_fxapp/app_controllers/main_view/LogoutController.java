package com.app20222.app20222_fxapp.app_controllers.main_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.context.ApplicationContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;


public class LogoutController {

    private Button logoutBtn;

    LogoutController() {
    }

    public Button getLogoutBtn() {
        return logoutBtn;
    }

    public void setLogoutBtn(Button logout_btn) {
        this.logoutBtn = logout_btn;
    }

    public void logout(ActionEvent event) {
        try {
            if (event.getSource() == logoutBtn) {
                // TO SWAP FROM DASHBOARD TO LOGIN FORM
                Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("views/login_view/login-view.fxml")));
                Image icon = new Image(Objects.requireNonNull(MainApplication.class.getResource("views/login_view/img/logo.png")).openStream());
                Stage stage = new Stage();
                stage.getIcons().add(icon);
                stage.setTitle("Hệ thống quản lý phẫu thuật");
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

                logoutBtn.getScene().getWindow().hide();

                // Clear token in context
//                ApplicationContext.ACCESS_TOKEN = "";
//                ApplicationContext.REFRESH_TOKEN = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
