package com.app20222.app20222_fxapp.app_controllers.main_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.dto.responses.BaseResponse;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.HttpMethods;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class MainController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label welcomeText;

    @FXML
    private Button arrow_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button logout_btn1;

    @FXML
    private AnchorPane headerLeft_form;

    @FXML
    private AnchorPane headerLeft_form1;

    @FXML
    private AnchorPane nav_form;

    @FXML
    private AnchorPane nav_form1;

    @FXML
    private AnchorPane mainCenter_form;
    @FXML
    protected void onHelloButtonClick() {
        String message = "";
        try {
            String apiPath = APIDetails.HELLO.getRequestPath() + APIDetails.HELLO.getDetailPath();
            String uri = ApiUtils.buildURI(apiPath, new HashMap<>());
            HttpResponse response = HttpUtils.doRequest(uri, HttpMethods.GET, new Object(), new HashMap<>());
            List<BaseResponse> lstResponse = HttpUtils.mappingResponseBody(response, new TypeReference<List<BaseResponse>>() {});
            message = Objects.requireNonNull(lstResponse).stream().map(BaseResponse::getMessage).collect(Collectors.toList()).toString();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        welcomeText.setText(message);
    }

    @FXML
    public void sliderArrow() {

        TranslateTransition slide = new TranslateTransition(Duration.seconds(.5),nav_form);
        TranslateTransition slideNav = new TranslateTransition(Duration.seconds(.5),nav_form1);
        TranslateTransition slide1 = new TranslateTransition(Duration.seconds(.5),mainCenter_form);
        TranslateTransition slideHeader = new TranslateTransition(Duration.seconds(.5),headerLeft_form);
        TranslateTransition slideHeader1 = new TranslateTransition(Duration.seconds(.5),headerLeft_form1);
        TranslateTransition slideButton = new TranslateTransition(Duration.seconds(.5), arrow_btn);

        System.out.println(nav_form.getTranslateX());
        if (nav_form.getTranslateX() == 0) {
            // Close the left pane
            slide.setToX(-260);
            slideHeader.setToX(-250);
            slide1.setToX(-250 + 90);
            slideButton.setToX(-250 + 90);
            headerLeft_form.setVisible(false);
            headerLeft_form1.setVisible(true);
            nav_form.setVisible(false);
            nav_form1.setVisible(true);
        } else {
            // Open the left pane
            slide.setToX(0);
            slide1.setToX(0);
            slideHeader.setToX(0);
            slideButton.setToX(0);
            headerLeft_form1.setVisible(false);
            headerLeft_form.setVisible(true);
            nav_form.setVisible(true);
            nav_form1.setVisible(false);
        }
        slide.play();
        slide1.play();
        slideHeader.play();
        slideButton.play();
        slideHeader1.play();
        slideNav.play();
    }
    @FXML
    public void logout(ActionEvent event) {
        LogoutController logoutController = new LogoutController();
        logoutController.setLogout_btn(logout_btn);
        logoutController.logout(event);
    }
    //  Khi co lại nav_left
    @FXML
    public void logout1(ActionEvent event) {
        LogoutController logoutController = new LogoutController();
        logoutController.setLogout_btn(logout_btn1);
        logoutController.logout(event);
    }
}