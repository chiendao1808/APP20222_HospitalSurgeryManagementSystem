package com.app20222.app20222_fxapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static final String DEFAULT_RESOURCE_PATH = "com/app20222/app20222_fxapp";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/login_view/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image(MainApplication.class.getResource( "views/login_view/img/logo.png").openStream());
        stage.getIcons().add(icon);
        stage.setTitle("Hệ thống quản lý phẫu thuật");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}