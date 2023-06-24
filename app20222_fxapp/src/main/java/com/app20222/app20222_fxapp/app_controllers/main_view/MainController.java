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
    private Button arrowBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button logoutBtn1;

    @FXML
    private AnchorPane headerLeftForm;

    @FXML
    private AnchorPane headerLeftForm1;

    @FXML
    private AnchorPane navForm;

    @FXML
    private AnchorPane navForm1;
    @FXML
    private AnchorPane mainCenterForm;
    @FXML
    private AnchorPane patient;
    @FXML
    private AnchorPane medicalRecord;
    @FXML
    private AnchorPane doctors;
    @FXML
    private AnchorPane surgery;
    @FXML
    private Button tabPatient;
    @FXML
    private Button tabMedicalRecord;
    @FXML
    private Button tabDoctor;
    @FXML
    private Button tabSurgery;
    @FXML
    protected void onHelloButtonClick() {
        String message = "";
        try {
            String apiPath = APIDetails.HELLO.getRequestPath() + APIDetails.HELLO.getDetailPath();
            String uri = ApiUtils.buildURI(apiPath, new HashMap<>());
            HttpResponse response = HttpUtils.doRequest(uri, HttpMethods.GET, new Object(), new HashMap<>());
            List<BaseResponse> lstResponse = HttpUtils.mappingResponseBody(response, new TypeReference<List<BaseResponse>>() {
            });
            message = Objects.requireNonNull(lstResponse).stream().map(BaseResponse::getMessage).collect(Collectors.toList()).toString();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        welcomeText.setText(message);
    }

    @FXML
    public void sliderArrow() {

        TranslateTransition slide = new TranslateTransition(Duration.seconds(.5), navForm);
        TranslateTransition slideNav = new TranslateTransition(Duration.seconds(.5), navForm1);
        TranslateTransition slide1 = new TranslateTransition(Duration.seconds(.5), mainCenterForm);
        TranslateTransition slideHeader = new TranslateTransition(Duration.seconds(.5), headerLeftForm);
        TranslateTransition slideHeader1 = new TranslateTransition(Duration.seconds(.5), headerLeftForm1);
        TranslateTransition slideButton = new TranslateTransition(Duration.seconds(.5), arrowBtn);

        System.out.println(navForm.getTranslateX());
        if (navForm.getTranslateX() == 0) {
            // Close the left pane
            slide.setToX(-260);
            slideHeader.setToX(-250);
            slide1.setToX(-250 + 90);
            slideButton.setToX(-250 + 90);
            headerLeftForm.setVisible(false);
            headerLeftForm1.setVisible(true);
            navForm.setVisible(false);
            navForm1.setVisible(true);
        } else {
            // Open the left pane
            slide.setToX(0);
            slide1.setToX(0);
            slideHeader.setToX(0);
            slideButton.setToX(0);
            headerLeftForm1.setVisible(false);
            headerLeftForm.setVisible(true);
            navForm.setVisible(true);
            navForm1.setVisible(false);
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
        logoutController.setLogoutBtn(logoutBtn);
        logoutController.logout(event);
    }

    //  Khi co lại nav_left
    @FXML
    public void logout1(ActionEvent event) {
        LogoutController logoutController = new LogoutController();
        logoutController.setLogoutBtn(logoutBtn1);
        logoutController.logout(event);
    }


    @FXML
    private void switchTab(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        if (selectedButton == tabPatient) {
            switchToTab(tabPatient, patient);
        } else if (selectedButton == tabMedicalRecord) {
            switchToTab(tabMedicalRecord, medicalRecord);
        } else if (selectedButton == tabDoctor) {
            switchToTab(tabDoctor, doctors);
        } else if (selectedButton == tabSurgery) {
            switchToTab(tabSurgery, surgery);
        }
    }

    private void switchToTab(Button selectedButton, AnchorPane selectedPane) {
        patient.setVisible(selectedPane == patient);
        medicalRecord.setVisible(selectedPane == medicalRecord);
        doctors.setVisible(selectedPane == doctors);
        surgery.setVisible(selectedPane == surgery);

        // Reset the background for all tabs
        tabPatient.setStyle("-fx-background-color: transparent");
        tabMedicalRecord.setStyle("-fx-background-color: transparent");
        tabDoctor.setStyle("-fx-background-color: transparent");
        tabSurgery.setStyle("-fx-background-color: transparent");

        // Set the background for the selected tab
        selectedButton.setStyle("-fx-background-color: #2C3D94");
        selectedButton.requestFocus();
    }


}