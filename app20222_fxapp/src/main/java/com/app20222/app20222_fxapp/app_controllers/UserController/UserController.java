package com.app20222.app20222_fxapp.app_controllers.UserController;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListNewDTO;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserController {

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
    private UserAPIService userAPIService ;

    public UserController(TableView<UserListDTO> userTableView, TableColumn<UserListDTO,?> userActionColumn,
                          TableColumn<UserListDTO,?> userAddressColumn,
                          TableColumn<UserListDTO,?> userDateColumn,
                          TableColumn<UserListDTO,?> userDepartmentColumn, TableColumn<UserListDTO,?> userEmailColumn,
                          TableColumn<UserListDTO,?> userIdentificationNumColumn, TableColumn<UserListDTO,?> userNameColumn,
                          TableColumn<UserListDTO,?> userPhoneColumn) {
        this.UserTableView = userTableView ;
        this.UserActionColumn = userActionColumn ;
        this.UserAddressColumn = userAddressColumn ;
        this.UserDateColumn = userDateColumn ;
         this.UserDepartmentColumn = userDepartmentColumn ;
         this.UserEmailColumn = userEmailColumn ;
         this.UserIdentificationNumColumn = userIdentificationNumColumn ;
         this.UserNameColumn = userNameColumn ;
         this.UserPhoneColumn = userPhoneColumn ;
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
//            dialogStage.setOnHidden(e -> {
//                Boolean resultSubmit = addPatientController.submit();
//                if (Objects.equals(resultSubmit, true)) {
//                    reloadTable();
//                }
//            });
            dialogStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Show dialogStage thêm mới bệnh nhân
    @FXML
    public void showModal(ActionEvent event) {
        openCreateDialog();
    }
    // Tạo list user (api gọi lấy danh sách ở đây)
    private ObservableList<PatientGetListNewDTO> getDataFromDataSource() {
        // Replace this method with your actual logic to fetch data from the data source
        List<PatientGetListNewDTO> lstPatient = new ArrayList<>();
        try {
//            lstPatient = userAPIService.getListPatient(new HashMap<>());
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        return FXCollections.observableArrayList(lstPatient);
    }
}