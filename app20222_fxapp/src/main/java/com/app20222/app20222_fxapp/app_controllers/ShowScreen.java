package com.app20222.app20222_fxapp.app_controllers;

import com.app20222.app20222_fxapp.MainApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ShowScreen {

    private static double x = 0;
    private static double y = 0;

    public Stage Show(String FXMLPath, String title) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(FXMLPath)));
        Scene scene = new Scene(root);
        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        Stage stage = new Stage();
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
        return stage;
    }
}
