package com.app20222.app20222_fxapp.app_controllers.main_view;

import com.app20222.app20222_fxapp.app_controllers.dashboard_view.DashboardController;
import com.app20222.app20222_fxapp.app_controllers.department_view.DepartmentController;
import com.app20222.app20222_fxapp.app_controllers.medicalRecord_view.MedicalRecordController;
import com.app20222.app20222_fxapp.app_controllers.profile_view.ProfileController;
import com.app20222.app20222_fxapp.app_controllers.surgeryRoom_view.StatusOption;
import com.app20222.app20222_fxapp.app_controllers.surgeryRoom_view.SurgeryRoomController;
import com.app20222.app20222_fxapp.app_controllers.user_view.UserController;
import com.app20222.app20222_fxapp.app_controllers.patient_view.PatientController;
import com.app20222.app20222_fxapp.app_controllers.surgery_view.SurgeryController;
import com.app20222.app20222_fxapp.dto.common.CommonIdCodeName;
import com.app20222.app20222_fxapp.dto.responses.deparment.DepartmentListDTO;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordGetListDTO;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordListDTO;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListNewDTO;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryGetListDTO;
import com.app20222.app20222_fxapp.dto.responses.surgeryRoom.SurgeryRoomListDTO;
import com.app20222.app20222_fxapp.dto.responses.users.ProfileUserDTO;
import com.app20222.app20222_fxapp.dto.responses.users.UserListDTO;
import com.app20222.app20222_fxapp.services.users.UserAPIService;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
    private Label profileUserDisplayName;

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
    @FXML
    private AnchorPane dashboard;
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
    @FXML
    private Button tabDashboard;

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
    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientIdentificationNumberColumn;
    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientIdentityTypeColumn;
    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientHealthInsuranceNumberColumn;

    // tìm kiếm bệnh nhân
    @FXML
    private TextField patientSearchCode;

    @FXML
    private TextField patientSearchEmail;

    @FXML
    private TextField patientSearchIdNumber;

    @FXML
    private ComboBox<String> patientSearchIdentityType;

    @FXML
    private TextField patientSearchName;

    @FXML
    private TextField patientSearchPhone;

    @FXML
    private Button patientSubmitSearch;
    @FXML
    private Button patientClearSearch;


    // Hồ sơ bệnh án
    @FXML
    private TableView<MedicalRecordListDTO> medicalRecordTable;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, Long> medicalRecordStt;

    @FXML
    private TableColumn<MedicalRecordListDTO, String> medicalRecordAction;

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
    // tìm kiếm hồ sơ bệnh án

    @FXML
    private TextField medicalRecordSearchCode;

    @FXML
    private DatePicker medicalRecordSearchEndAt;

    @FXML
    private TextField medicalRecordSearchName;

    @FXML
    private DatePicker medicalRecordSearchStartAt;
    @FXML
    private Button medicalRecordSubmitSearch;
    @FXML
    private Button medicalRecordClearSearch;


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
    private TableColumn<SurgeryGetListDTO, String> surgeryStatusColumn;
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

    // Tìm kiếm
    @FXML
    private Button surgeryClearSearch;
    @FXML
    private TextField surgerySearchDiseaseGroup;

    @FXML
    private ComboBox<Integer> surgerySearchEstimatedEndAtHour;

    @FXML
    private ComboBox<Integer> surgerySearchEstimatedEndAtMinute;

    @FXML
    private DatePicker surgerySearchEstimatedEndAtYear;

    @FXML
    private TextField surgerySearchName;

    @FXML
    private TextField surgerySearchPatientId;

    @FXML
    private ComboBox<Integer> surgerySearchStartedAtHour;

    @FXML
    private ComboBox<Integer> surgerySearchStartedAtMinute;

    @FXML
    private ComboBox<String> surgerySearchStatus;
    @FXML
    private TextField surgerySearchPatientIdSearch;

    @FXML
    private ComboBox<CommonIdCodeName> surgerySearchSurgeryRoomId;

    @FXML
    private DatePicker surgerySearchStartAtYear;
    @FXML
    private Button surgerySubmitSearch;
    // người dùng
    @FXML
    private TableColumn<UserListDTO, String> UserActionColumn;

    @FXML
    private TableColumn<UserListDTO, String> UserAddressColumn;

    @FXML
    private TableColumn<UserListDTO, Date> UserDateColumn;

    @FXML
    private TableColumn<UserListDTO, String> UserDepartmentColumn;

    @FXML
    private TableColumn<UserListDTO, String> UserEmailColumn;

    @FXML
    private TableColumn<UserListDTO, String> UserIdentityTypeColumn;

    @FXML
    private TableColumn<UserListDTO, String> UserIdentificationNumColumn;

    @FXML
    private TableColumn<UserListDTO, String> UserNameColumn;

    @FXML
    private TableColumn<UserListDTO, String> UserPhoneColumn;

    @FXML
    private TableColumn<UserListDTO, Long> UserSttColumn;


    @FXML
    private TableView<UserListDTO> UserTableView;

    @FXML
    private Button createUserBtn;
    // tìm kiếm
    @FXML
    private TextField userSearchCode;

    @FXML
    private ComboBox<String> userSearchDepartment;

    @FXML
    private TextField userSearchEmail;

    @FXML
    private TextField userSearchName;

    @FXML
    private TextField userSearchPhone;

    @FXML
    private ComboBox<String> userSearchRole;

    @FXML
    private Button userSubmitSearch;
    @FXML
    private Button userClearSearch;

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

    // Tìm kiếm phòng pt
    @FXML
    private TextField SurgeryRoomSearchCode;

    @FXML
    private TextField SurgeryRoomSearchName;

    @FXML
    private ComboBox<StatusOption> SurgeryRoomSearchStatus;
    @FXML
    private Button surgeryRoomSubmitSearch;
    @FXML
    private Button surgeryRoomClearSearch;

    // Khoa/ bộ phận
    @FXML
    private TableColumn<DepartmentListDTO, String> departmentAction;

    @FXML
    private TableColumn<DepartmentListDTO, String> departmentAddress;

    @FXML
    private TableColumn<DepartmentListDTO, String> departmentCode;

    @FXML
    private TableColumn<DepartmentListDTO, String> departmentDescription;

    @FXML
    private TableColumn<DepartmentListDTO, String> departmentEmail;

    @FXML
    private TableColumn<DepartmentListDTO, String> departmentName;

    @FXML
    private TableColumn<DepartmentListDTO, String> departmentPhoneNumber;

    @FXML
    private TextField departmentSearchCode;

    @FXML
    private TextField departmentSearchEmail;

    @FXML
    private TextField departmentSearchName;

    @FXML
    private TextField departmentSearchPhone;

    @FXML
    private TableColumn<DepartmentListDTO, Long> departmentStt;

    @FXML
    private TableView<DepartmentListDTO> departmentTable;

    @FXML
    private Button createDepartment;
    @FXML
    private Button departmentSubmitSearch;
    @FXML
    private Button departmentClearSearch;

    // Dashboard
    @FXML
    private TableView<SurgeryGetListDTO> surgeryTableDashboard;

    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryActionColumnDashboard;
    ;
    @FXML
    private TableColumn<SurgeryGetListDTO, Long> surgerySttColumnDashboard;
    ;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryCodeColumnDashboard;
    ;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryNameColumnDashboard;
    ;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryPatientNameColumnDashboard;
    ;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryResultColumnDashboard;
    ;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryRoomColumnDashboard;
    ;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryStatusColumnDashboard;
    ;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> typeSurgeryColumnDashboard;
    ;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryDiseaseGroupNameColumnDashboard;
    ;
    @FXML
    private TableColumn<SurgeryGetListDTO, Date> surgeryStartedAtColumnDashboard;
    ;
    @FXML
    private TableColumn<SurgeryGetListDTO, Date> surgeryEndedAtColumnDashboard;
    ;
    @FXML
    private TableColumn<SurgeryGetListDTO, Date> surgeryEstimatedEndAtColumnDashboard;
    ;

    @FXML
    Label totalSurgery;
    // chart
    @FXML
    private PieChart pieChartMonth;

    @FXML
    private Text numSurgeryCurrentMonth;

    @FXML
    private PieChart pieChartQuarter;

    @FXML
    private Text numSurgeryCurrentQuarter;

    @FXML
    private PieChart pieChatYear;

    @FXML
    private Text numSurgeryCurrentYear;

    // tìm kiếm
    @FXML
    private Button surgeryClearSearchDashboard;
    @FXML
    private DatePicker surgerySearchEstimatedEndAtYearDashboard;
    @FXML
    private DatePicker surgerySearchStartAtYearDashboard;
    @FXML
    private Button surgerySubmitSearchDashboard;

    private UserAPIService userAPIService;


    // controller
    //TabController
    private TabController tabController = new TabController();
    private PatientController patientController;
    private MedicalRecordController medicalRecordController;
    private SurgeryController surgeryController;
    private UserController userController;
    private ProfileController profileController;
    private SurgeryRoomController surgeryRoomController;
    private DepartmentController departmentController;
    private DashboardController dashboardController;

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
        Button selectedButton = (Button) event.getSource();
        tabController = new TabController(
            patient,
            medicalRecord,
            doctors,
            surgery,
            surgeryRoom,
            department,
            userPane,
            dashboard,
            tabPatient,
            tabMedicalRecord,
            tabDoctor,
            tabSurgeryRoom,
            tabDepartment,
            tabSurgery,
            tabUser, tabDashboard);
        tabController.switchTab(event);
        HashMap<Button, Runnable> buttonToInitializer = new HashMap<>();
        buttonToInitializer.put(tabPatient, this::initializePatientTab);
        buttonToInitializer.put(tabMedicalRecord, this::initializeMedicalRecordTab);
        buttonToInitializer.put(tabDoctor, this::initializeUserTab);
        buttonToInitializer.put(tabSurgery, this::initializeSurgeryTab);
        buttonToInitializer.put(tabSurgeryRoom, this::initializeSurgeryRoomTab);
        buttonToInitializer.put(tabDepartment, this::initializeDepartmentTab);
        buttonToInitializer.put(tabUser, this::initializeProfileTab);
        buttonToInitializer.put(tabDashboard, this::initializeDashboardTab);
        Runnable initializer = buttonToInitializer.get(selectedButton);
        if (initializer != null) {
            initializer.run();
        }
        // thực hiện reset params
        String currentPaneName = getCurrentAnchorPaneName();
        switch (currentPaneName) {
            case "patient":
                patientController.resetSearchParams();
                break;
            case "department":
                departmentController.resetSearchParams();
                break;
            case "medicalRecord":
                medicalRecordController.resetSearchParams();
                break;
            case "surgery":
                surgeryController.resetSearchParams();
                break;
            case "surgeryRoom":
                surgeryRoomController.resetSearchParams();
                break;
            case "dashboard":
                dashboardController.resetSearchParams();
                break;
            case "doctors":
                userController.resetSearchParams();
                break;
            default:
                break;
        }
    }

    // Lấy tên màn hình
    public String getCurrentAnchorPaneName() {
        if (patient.isVisible()) {
            return "patient";
        } else if (medicalRecord.isVisible()) {
            return "medicalRecord";
        } else if (doctors.isVisible()) {
            return "doctors";
        } else if (surgery.isVisible()) {
            return "surgery";
        } else if (surgeryRoom.isVisible()) {
            return "surgeryRoom";
        } else if (department.isVisible()) {
            return "department";
        } else if (userPane.isVisible()) {
            return "userPane";
        } else if (dashboard.isVisible()) {
            return "dashboard";
        } else {
            return "Không có AnchorPane nào đang hiển thị.";
        }
    }

    // Hiển thị các modal: tạo, sửa, xem chi tiết
    @FXML
    private void showModal(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        if (selectedButton == createPatient) {
            patientController.showModal(event);
        } else if (selectedButton == createSurgery) {
            surgeryController.showModal(event);
        } else if (selectedButton == createUserBtn) {
            userController.showModal(event);
        } else if (selectedButton == createMedicalRecord) {
            medicalRecordController.showModal(event);
        } else if (selectedButton == createDepartment) {
            departmentController.showModal(event);
        } else if (selectedButton == createSurgeryRoom) {
            surgeryRoomController.showModal(event);
        }
    }

    // Thưc thi các tab gọi api danh sách, tim kiem
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userAPIService = new UserAPIService();
        //Init data
        try {
            ProfileUserDTO profileUser = userAPIService.getCurrentUserProfile();
            if (Objects.nonNull(profileUser)) {
                profileUserDisplayName.setText(profileUser.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Mặc định gọi danh sách bệnh nhân đầu tiên
        initializePatientTab();
    }

    // Các phương thức khởi tạo tab tương ứng
    // Hồ sơ cá nhân
    private void initializeProfileTab() {
        profileController = new ProfileController(profileUser, profileUserId, profileUserCode, profileUserName,
            profileUserIdentityType, profileUserIdentificationNum, profileUserBirthDate, profileUserEmail,
            profileUserPhone, profileUserAddress, profileUserDepartment, profileUserRole);
        profileController.initializeProfile();
    }

    // Bệnh nhân
    private void initializePatientTab() {
        patientController = new PatientController(patientTable, patientIdColumn, patientINameColumn, patientCodeColumn,
            patientIBirthdayColumn, patientPhoneColumn, patientAddressColumn,
            patientActionColumn, patientIdentificationNumberColumn, patientIdentityTypeColumn, patientHealthInsuranceNumberColumn,
            patientSearchCode, patientSearchEmail, patientSearchIdNumber,
            patientSearchIdentityType, patientSearchName, patientSearchPhone, patientSubmitSearch);
        patientController.initializePatient();
    }

    // Người dùng
    private void initializeUserTab() {
        userController = new UserController(UserTableView, UserActionColumn, UserAddressColumn, UserDateColumn,
            UserDepartmentColumn, UserEmailColumn, UserIdentificationNumColumn,
            UserNameColumn, UserPhoneColumn, UserSttColumn, UserIdentityTypeColumn,userSearchCode,
                userSearchDepartment,userSearchEmail,userSearchName,userSearchPhone,userSearchRole,
                userSubmitSearch,userClearSearch);
        userController.initializeUser();
    }

    // Hồ sơ bệnh án
    private void initializeMedicalRecordTab() {
        medicalRecordController = new MedicalRecordController(medicalRecordTable, medicalRecordStt, medicalRecordPatientID,
            medicalRecordPatientName, medicalRecordPatientCode, medicalRecordCreateAt, medicalRecordCreatedById,
            medicalRecordCreatedByName, medicalRecordAction, medicalRecordSearchCode, medicalRecordSearchName, medicalRecordSearchStartAt,
            medicalRecordSearchEndAt);
        medicalRecordController.initializeMedicalRecord();
    }

    // Ca phẫu thuật
    private void initializeSurgeryTab() {
        surgeryController = new SurgeryController(surgeryTable, surgerySttColumn, surgeryCodeColumn,
            surgeryNameColumn, surgeryPatientNameColumn, surgeryResultColumn, surgeryRoomColumn,
            surgeryStatusColumn, typeSurgeryColumn, surgeryActionColumn, surgeryDiseaseGroupNameColumn,
            surgeryStartedAtColumn, surgeryEndedAtColumn,
            surgeryEstimatedEndAtColumn, surgerySearchDiseaseGroup, surgerySearchEstimatedEndAtHour,
            surgerySearchEstimatedEndAtMinute, surgerySearchEstimatedEndAtYear, surgerySearchName,
            surgerySearchPatientId, surgerySearchStartedAtHour, surgerySearchStartedAtMinute,
            surgerySearchStatus, surgerySearchSurgeryRoomId, surgerySearchStartAtYear, surgerySearchPatientIdSearch);
        surgeryController.initializeSurgery();
    }

    // Phòng phẫu thuật
    private void initializeSurgeryRoomTab() {
        surgeryRoomController = new SurgeryRoomController(surgeryRoomTable, surgeryRoomStt,
            surgeryRoomName, surgeryRoomCode, surgeryRoomDescription,
            surgeryRoomAddress, surgeryRoomCurrentAvailable, surgeryRoomOnServiceAt, surgeryRoomAction,
            SurgeryRoomSearchCode, SurgeryRoomSearchName, SurgeryRoomSearchStatus);
        surgeryRoomController.initializeSurgeryRoom();
    }

    // Khoa bộ phận
    private void initializeDepartmentTab() {
        departmentController = new DepartmentController(departmentTable, departmentAction,
            departmentAddress, departmentCode, departmentDescription, departmentEmail,
            departmentName, departmentPhoneNumber, departmentStt, departmentSearchCode,
            departmentSearchEmail, departmentSearchName, departmentSearchPhone, createDepartment);
        departmentController.initializeDepartment();
    }

    // dashboard
    private void initializeDashboardTab() {
        dashboardController = new DashboardController(surgeryTableDashboard, surgerySttColumnDashboard, surgeryCodeColumnDashboard,
            surgeryNameColumnDashboard, surgeryPatientNameColumnDashboard, surgeryResultColumnDashboard, surgeryRoomColumnDashboard,
            surgeryStatusColumnDashboard, typeSurgeryColumnDashboard, surgeryActionColumnDashboard, surgeryDiseaseGroupNameColumnDashboard,
            surgeryStartedAtColumnDashboard, surgeryEndedAtColumnDashboard,
            surgeryEstimatedEndAtColumnDashboard, surgerySearchEstimatedEndAtYearDashboard,
            surgerySearchStartAtYearDashboard, pieChartMonth, numSurgeryCurrentMonth, pieChartQuarter, numSurgeryCurrentQuarter, pieChatYear,
            numSurgeryCurrentYear, totalSurgery);
        dashboardController.initializeSurgery();
    }

    // Hàm khi tìm kiếm cho các màn
    @FXML
    private void onSubmitSearch(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        if (selectedButton == patientSubmitSearch) {
            patientController.onPatientSubmitSearch(event);
        } else if (selectedButton == departmentSubmitSearch) {
            departmentController.onDepartmentSubmitSearch(event);
        } else if (selectedButton == medicalRecordSubmitSearch) {
            medicalRecordController.onDepartmentSubmitSearch(event);
        } else if (selectedButton == surgerySubmitSearch) {
            surgeryController.onSurgerySubmitSearch(event);
        } else if (selectedButton == surgeryRoomSubmitSearch) {
            surgeryRoomController.onSurgeryRoomSubmitSearch(event);
        } else if (selectedButton == surgerySubmitSearchDashboard) {
            dashboardController.onDashBoardSubmitSearch(event);
        } else if (selectedButton == userSubmitSearch) {
            userController.onDashBoardSubmitSearch(event);
        }
    }

    // Hàm khi click button clear tìm kếm
    @FXML
    private void onSubmitClear(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        System.out.println(selectedButton);
        if (selectedButton == patientClearSearch) {
            patientController.clearParams(event);
        } else if (selectedButton == departmentClearSearch) {
            departmentController.clearParams(event);
        } else if (selectedButton == medicalRecordClearSearch) {
            medicalRecordController.clearParams(event);
        } else if (selectedButton == surgeryClearSearch) {
            surgeryController.clearParams(event);
        } else if (selectedButton == surgeryRoomClearSearch) {
            surgeryRoomController.clearParams(event);
        } else if (selectedButton == surgeryClearSearchDashboard) {
            dashboardController.clearParams(event);
        } else if (selectedButton == userClearSearch) {
            userController.clearParams(event);
        }
    }

    // Xuất file báo cáo
    @FXML
    public void handleClickExport(ActionEvent event) {
        dashboardController.handleClickExport(event);
    }
}