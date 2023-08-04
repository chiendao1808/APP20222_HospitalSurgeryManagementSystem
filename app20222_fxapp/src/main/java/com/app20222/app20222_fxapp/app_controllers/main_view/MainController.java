package com.app20222.app20222_fxapp.app_controllers.main_view;

import com.app20222.app20222_fxapp.app_controllers.medicalRecord_view.MedicalRecordController;
import com.app20222.app20222_fxapp.app_controllers.profile_view.ProfileController;
import com.app20222.app20222_fxapp.app_controllers.surgeryRoom_view.SurgeryRoomController;
import com.app20222.app20222_fxapp.app_controllers.user_view.UserController;
import com.app20222.app20222_fxapp.app_controllers.patient_view.PatientController;
import com.app20222.app20222_fxapp.app_controllers.surgery_view.SurgeryController;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordGetListDTO;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordListDTO;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListNewDTO;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryGetListDTO;
import com.app20222.app20222_fxapp.dto.responses.surgeryRoom.SurgeryRoomListDTO;
import com.app20222.app20222_fxapp.dto.responses.users.UserListDTO;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;


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

    // thông tin cá nhân

    @FXML
    private Text profileUser;

    @FXML
    private Text profileUserAddress;

    @FXML
    private Text profileUserBirthDate;

    @FXML
    private Text profileUserCode;

    @FXML
    private Text profileUserDepartment;

    @FXML
    private Text profileUserEmail;

    @FXML
    private Text profileUserIdentificationNum;

    @FXML
    private Text profileUserIdentityType;

    @FXML
    private Text profileUserName;

    @FXML
    private Text profileUserPhone;

    @FXML
    private Text profileUserRole;

    @FXML
    private Text profileUserId;


    // Bệnh nhân
    @FXML
    private Button createPatient;
    @FXML
    private TableView<PatientGetListNewDTO> patientTable;
    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientActionColumn;

    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientCodeColumn;

    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientAddressColumn;


    @FXML
    private TableColumn<PatientGetListNewDTO, Date> patientIBirthdayColumn;

    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientINameColumn;

    @FXML
    private TableColumn<PatientGetListNewDTO, Long> patientIdColumn;

    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientPhoneColumn;

    // Hồ sơ bệnh án
    @FXML
    private TableView<MedicalRecordListDTO> medicalRecordTable;

    @FXML
    private TableColumn<MedicalRecordGetListDTO,Long> medicalRecordStt;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, String> medicalRecordAction;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, Date> medicalRecordCreateAt;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, Long> medicalRecordCreatedById;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, String> medicalRecordCreatedByName;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, String> medicalRecordPatientCode;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, Long> medicalRecordPatientID;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, String> medicalRecordPatientName;

    @FXML
    private Button createMedicalRecord;

    //  Ca phẫu thuật
    @FXML
    private Button createSurgery;
    @FXML
    private ScrollPane surgeryTableView;
    // column
    @FXML
    private TableView<SurgeryGetListDTO> surgeryTable;

    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryActionColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, Long> surgerySttColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryCodeColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryNameColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryPatientNameColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryResultColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryRoomColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO,String> surgeryStatusColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> typeSurgeryColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryDiseaseGroupNameColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, Date> surgeryStartedAtColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, Date> surgeryEndedAtColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, Date> surgeryEstimatedEndAtColumn;
    // người dùng
    @FXML
    private TableColumn<UserListDTO, ?> UserActionColumn;

    @FXML
    private TableColumn<UserListDTO, ?> UserAddressColumn;

    @FXML
    private TableColumn<UserListDTO, ?> UserDateColumn;

    @FXML
    private TableColumn<UserListDTO, ?> UserDepartmentColumn;

    @FXML
    private TableColumn<UserListDTO, ?> UserEmailColumn;

    @FXML
    private TableColumn<UserListDTO, ?> UserIdentificationNumColumn;

    @FXML
    private TableColumn<UserListDTO, ?> UserNameColumn;

    @FXML
    private TableColumn<UserListDTO, ?> UserPhoneColumn;

    @FXML
    private TableView<UserListDTO> UserTableView;
    @FXML
    private Button createUserBtn;

    // phòng phẫu thuật
    @FXML
    private TableColumn<SurgeryRoomListDTO, String> surgeryRoomAction;

    @FXML
    private TableColumn<SurgeryRoomListDTO, String> surgeryRoomAddress;

    @FXML
    private TableColumn<SurgeryRoomListDTO, String> surgeryRoomCode;

    @FXML
    private TableColumn<SurgeryRoomListDTO, Boolean> surgeryRoomCurrentAvailable;

    @FXML
    private TableColumn<SurgeryRoomListDTO, String> surgeryRoomDescription;

    @FXML
    private TableColumn<SurgeryRoomListDTO, String> surgeryRoomName;

    @FXML
    private TableColumn<SurgeryRoomListDTO, Date> surgeryRoomOnServiceAt;

    @FXML
    private TableColumn<SurgeryRoomListDTO, Long> surgeryRoomStt;

    @FXML
    private TableView<SurgeryRoomListDTO> surgeryRoomTable;
    @FXML
    private Button createSurgeryRoom;

    // controller
    //TabController
    private TabController tabController = new TabController();
    private PatientController patientController;
    private MedicalRecordController medicalRecordController;
    private SurgeryController surgeryController;
    private UserController userController;
    private ProfileController profileController;
    private SurgeryRoomController surgeryRoomController;

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
        System.out.println(logoutBtn);
        logoutController.setLogoutBtn(logoutBtn);
        logoutController.logout(event);
    }

    //  Khi co lại nav_left
    @FXML
    public void logout1(ActionEvent event) {
        LogoutController logoutController = new LogoutController();
        logoutController.setLogoutBtn(logoutBtn1);
        logoutController.logout(event);
        System.out.println(logoutBtn);

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
        else if(selectedButton == createUserBtn){
            userController.showModal(event);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Benh nhan
        patientController = new PatientController(patientTable, patientIdColumn,patientINameColumn,patientCodeColumn,
                patientIBirthdayColumn,patientPhoneColumn,patientAddressColumn,
            patientActionColumn);
        patientController.initializeTable();
        // Nguoi dung
        userController = new UserController(UserTableView,UserActionColumn,UserAddressColumn,UserDateColumn,UserDepartmentColumn,
                UserEmailColumn,UserIdentificationNumColumn,UserNameColumn,UserPhoneColumn);
        profileController = new ProfileController(profileUser, profileUserId, profileUserCode, profileUserName,
                profileUserIdentityType, profileUserIdentificationNum, profileUserBirthDate, profileUserEmail,
                profileUserPhone, profileUserAddress, profileUserDepartment, profileUserRole);
        profileController.initializeProfile();
        // Ho so benh an
        medicalRecordController = new MedicalRecordController(medicalRecordTable, medicalRecordStt,medicalRecordPatientID, medicalRecordPatientName,
                medicalRecordPatientCode, medicalRecordCreateAt,medicalRecordCreatedById,medicalRecordCreatedByName,medicalRecordAction);
        medicalRecordController.initializeMedicalRecord();
        // Ca phau thuat
        surgeryController = new SurgeryController(surgeryTable,surgerySttColumn,surgeryCodeColumn,
                surgeryNameColumn,surgeryPatientNameColumn, surgeryResultColumn,surgeryRoomColumn,
                surgeryStatusColumn,typeSurgeryColumn,surgeryActionColumn,  surgeryDiseaseGroupNameColumn,
               surgeryStartedAtColumn,surgeryEndedAtColumn,
               surgeryEstimatedEndAtColumn );
        surgeryController.initializeSurgery();

        // phòng phẫu thuật
        surgeryRoomController = new SurgeryRoomController(surgeryRoomTable,surgeryRoomStt,surgeryRoomName,surgeryRoomCode,
                surgeryRoomDescription,surgeryRoomAddress,surgeryRoomCurrentAvailable,surgeryRoomOnServiceAt,surgeryRoomAction);
        surgeryRoomController.initializeMedicalRecord();
    }
}//