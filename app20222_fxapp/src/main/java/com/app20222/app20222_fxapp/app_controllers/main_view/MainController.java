package com.app20222.app20222_fxapp.app_controllers.main_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.app_controllers.ShowScreen;
import com.app20222.app20222_fxapp.app_controllers.patient_view.PatientController;
import com.app20222.app20222_fxapp.app_controllers.surgery_view.SurgeryController;
import com.app20222.app20222_fxapp.dto.responses.BaseResponse;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListDTO;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryGetListDTO;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.HttpMethods;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;


public class MainController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

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

    //  Tab dùng để chuyển giữa các màn
    // Pane
    @FXML
    private AnchorPane patient;
    @FXML
    private AnchorPane medicalRecord;
    @FXML
    private AnchorPane doctors;
    @FXML
    private AnchorPane surgery;
    @FXML
    private AnchorPane surgeryRoom;
    @FXML
    private AnchorPane department;
    @FXML
    private AnchorPane userPane;
    // Tab
    @FXML
    private Button tabPatient;
    @FXML
    private Button tabMedicalRecord;
    @FXML
    private Button tabDoctor;
    @FXML
    private Button tabSurgeryRoom;
    @FXML
    private Button tabDepartment;
    @FXML
    private Button tabSurgery;
    @FXML
    private Button tabUser;

    // Bệnh nhân
    @FXML
    private Button createPatient;
    @FXML
    private TableView<PatientGetListDTO> patientTable;
    @FXML
    private TableColumn<PatientGetListDTO, String> patientActionColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientCodeColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientEmailColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientHealthInsuranceNumberColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientIBirthdayColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientINameColumn;
    @FXML
    private TableColumn<PatientGetListDTO, String> patientIFirstNameColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientILastNameColumn;

    @FXML
    private TableColumn<PatientGetListDTO, Long> patientIdColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientPhoneColumn;

    //  Ca phẫu thuật
    @FXML
    private Button createSurgery;
    @FXML
    private TableView<SurgeryGetListDTO> surgeryTable;
    @FXML
    private ScrollPane surgeryTableView;
    // column
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> id;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> surgeryCodeColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> surgeryNameColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> surgeryPatientNameColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> surgeryResultColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> surgeryRoomColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> surgeryStatusColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, ?> typeSurgeryColumn;

    // controller
    //TabController
    private TabController tabController = new TabController();
    private PatientController patientController = new PatientController();
    private SurgeryController surgeryController = new SurgeryController();

    // Các hàm xử lý
    // Xử lý khi click icon thu nhỏ múc leftMenu
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

    // CHuyển giữa các màn
    @FXML
    private void switchTab(ActionEvent event) {
        tabController = new TabController(
            patient,
            medicalRecord,
            doctors,
            surgery,
            surgeryRoom,
            department,
            userPane,
            tabPatient,
            tabMedicalRecord,
            tabDoctor,
            tabSurgeryRoom,
            tabDepartment,
            tabSurgery,
            tabUser);
        tabController.switchTab(event);
    }


    // Hiển thị các modal: tạo, sửa, xem chi tiết
    @FXML
    private void showModal(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        if (selectedButton == createPatient) {
            patientController.showModal(event);
        } else if (selectedButton == createSurgery) {
            surgeryController.showModal(event);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patientController = new PatientController(patientTable, patientCodeColumn, patientEmailColumn, patientHealthInsuranceNumberColumn,
            patientIBirthdayColumn, patientINameColumn, patientIFirstNameColumn, patientILastNameColumn, patientIdColumn, patientPhoneColumn,
            patientActionColumn);
        patientController.initializeTable();
    }
}//