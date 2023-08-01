package com.app20222.app20222_fxapp.app_controllers.user_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.dto.responses.users.UserListDTO;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.users.UserAPIService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class UserController {

    @FXML
    private TableColumn<UserListDTO, ?> userActionColumn;

    @FXML
    private TableColumn<UserListDTO, ?> userAddressColumn;

    @FXML
    private TableColumn<UserListDTO, ?> userDateColumn;

    @FXML
    private TableColumn<UserListDTO, ?> userDepartmentColumn;

    @FXML
    private TableColumn<UserListDTO, ?> userEmailColumn;

    @FXML
    private TableColumn<UserListDTO, ?> userIdentificationNumColumn;

    @FXML
    private TableColumn<UserListDTO, ?> userNameColumn;

    @FXML
    private TableColumn<UserListDTO, ?> userPhoneColumn;

    @FXML
    private TableView<UserListDTO> userTableView;

    @FXML
    private Button createUserBtn;

    private UserAPIService userAPIService;

    public UserController(){}

    public UserController(TableView<UserListDTO> userTableView, TableColumn<UserListDTO, ?> userActionColumn,
        TableColumn<UserListDTO, ?> userAddressColumn,
        TableColumn<UserListDTO, ?> userDateColumn,
        TableColumn<UserListDTO, ?> userDepartmentColumn, TableColumn<UserListDTO, ?> userEmailColumn,
        TableColumn<UserListDTO, ?> userIdentificationNumColumn, TableColumn<UserListDTO, ?> userNameColumn,
        TableColumn<UserListDTO, ?> userPhoneColumn) {
        this.userTableView = userTableView;
        this.userActionColumn = userActionColumn;
        this.userAddressColumn = userAddressColumn;
        this.userDateColumn = userDateColumn;
        this.userDepartmentColumn = userDepartmentColumn;
        this.userEmailColumn = userEmailColumn;
        this.userIdentificationNumColumn = userIdentificationNumColumn;
        this.userNameColumn = userNameColumn;
        this.userPhoneColumn = userPhoneColumn;
        userAPIService = new UserAPIService();
    }

    public TableView<UserListDTO> getUserTableView() {
        return this.userTableView;
    }

//    public void initializeTable() {
//        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
//        ObservableList<UserListDTO> userList = getDataFromDataSource();
//        setupTableColumns();
//        setupEditDeleteButtons();
//
//        if (patientTable != null) {
//            this.patientTable.setItems(patientList);
//        }
//    }

    public void openCreateDialog() {
        String fxmlPath = "views/user_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            AddUserController addUserController = loader.getController();
//            addUserController.setCreateMode(true);
//            addUserController.initialize(null, null);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tạo mới người dùng");
            dialogStage.setScene(new Scene(root));
            dialogStage.setOnHidden(e -> {
                Boolean resultSubmit = true;
                if (Objects.equals(resultSubmit, true)) {
                    reloadTable();
                }
            });
            dialogStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadTable() {
        ObservableList<UserListDTO> lstUsers = getDataFromDataSource();
        this.userTableView.setItems(lstUsers);
    }


    // Show dialogStage thêm mới người dùng
    @FXML
    public void showModal(ActionEvent event) {
        openCreateDialog();
    }

    // Tạo list user (api gọi lấy danh sách ở đây)
    private ObservableList<UserListDTO> getDataFromDataSource() {
        // Fetch lstUsers from API
        List<UserListDTO> lstUsers = new ArrayList<>();
        try {
            Map<String, String> params = new HashMap<>();
            lstUsers = userAPIService.getListUsers(params);
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        return FXCollections.observableArrayList(lstUsers);
    }
}