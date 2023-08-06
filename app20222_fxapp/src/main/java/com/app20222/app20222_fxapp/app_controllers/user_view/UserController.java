package com.app20222.app20222_fxapp.app_controllers.user_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.app_controllers.patient_view.AddPatientController;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientDetailsDTO;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListNewDTO;
import com.app20222.app20222_fxapp.dto.responses.users.UserDetailsDTO;
import com.app20222.app20222_fxapp.dto.responses.users.UserListDTO;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.users.UserAPIService;
import com.app20222.app20222_fxapp.utils.DateUtils;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotResult;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;


public class UserController {

    @FXML
    private TableColumn<UserListDTO, String> UserActionColumn;

    @FXML
    private TableColumn<UserListDTO, String> UserAddressColumn;

    @FXML
    private TableColumn<UserListDTO, String> UserDateColumn;

    @FXML
    private TableColumn<UserListDTO, String> UserDepartmentColumn;
    @FXML
    private TableColumn<UserListDTO,String> UserIdentityTypeColumn;
    @FXML
    private TableColumn<UserListDTO, String> UserIdentificationNumColumn;

    @FXML
    private TableColumn<UserListDTO, String> UserMailColumn;

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

    private UserAPIService userAPIService;
    Map<String,String> map = new HashMap<>();

    public UserController(){}

    public UserController(TableView<UserListDTO> userTableView, TableColumn<UserListDTO, String> userActionColumn,
        TableColumn<UserListDTO, String> userAddressColumn,
        TableColumn<UserListDTO, String> userDateColumn,
        TableColumn<UserListDTO, String> userDepartmentColumn, TableColumn<UserListDTO, String> userEmailColumn,
        TableColumn<UserListDTO, String> userIdentificationNumColumn, TableColumn<UserListDTO, String> userNameColumn,
        TableColumn<UserListDTO, String> userPhoneColumn , TableColumn<UserListDTO,Long> userSttColumn,
                          TableColumn<UserListDTO,String> userIdentityTypeColumn ) {
        this.UserTableView = userTableView;
        this.UserActionColumn = userActionColumn;
        this.UserAddressColumn = userAddressColumn;
        this.UserDateColumn = userDateColumn;
        this.UserDepartmentColumn = userDepartmentColumn;
        this.UserMailColumn = userEmailColumn;
        this.UserIdentificationNumColumn = userIdentificationNumColumn;
        this.UserNameColumn = userNameColumn;
        this.UserPhoneColumn = userPhoneColumn;
        this.UserSttColumn = userSttColumn;
        this.UserIdentityTypeColumn = userIdentityTypeColumn;
        userAPIService = new UserAPIService();
    }

    public TableView<UserListDTO> getUserTableView() {
        return this.UserTableView;
    }

    public void initializeTable() {
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        ObservableList<UserListDTO> userList = getDataFromDataSource();
        setupTableColumns();
        setUpEditDeleteButton();
        setUpEditDeleteButton();

        if (UserTableView != null) {
            this.UserTableView.setItems(userList);
        }
    }

    public void openEditDialog(UserDetailsDTO userDetailsDTO , Map<String,String> param) {
        String fxmlPath = "views/user_view/view-detail.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            ViewUserDeatailController viewUserDeatailController = loader.getController();
            viewUserDeatailController.disableAllFields();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Xem chi tiết người dùng");
            dialogStage.setScene(new Scene(root));
            viewUserDeatailController.setUserId(param);
            viewUserDeatailController.setText(
                    userDetailsDTO.getIdentificationNum(),
                    userDetailsDTO.getIdentityType(),
                    userDetailsDTO.getName(),
                    userDetailsDTO.getCode(),
                    userDetailsDTO.getBirthDate(),
                    userDetailsDTO.getAddress(),
                    userDetailsDTO.getPhoneNumber(),
                    userDetailsDTO.getEmail(),
                    userDetailsDTO.getRole(),
                    userDetailsDTO.getDepartment()
            );
            dialogStage.setOnHidden(e->{
                boolean resultSubmit = viewUserDeatailController.submit();
                if(Objects.equals(resultSubmit,true)){
                    reloadTable();
                }
            });
            dialogStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setupTableColumns() {
        this.UserSttColumn.setCellValueFactory(param -> {
            int index = param.getTableView().getItems().indexOf(param.getValue());
            return new SimpleLongProperty(index + 1).asObject();
        });
        this.UserNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.UserIdentityTypeColumn.setCellValueFactory(new PropertyValueFactory<>("identityType"));
        this.UserIdentificationNumColumn.setCellValueFactory(new PropertyValueFactory<>("identificationNum"));
        this.UserDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        this.UserPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.UserAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.UserMailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.UserDepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
    }

    public void reloadTable() {
        ObservableList<UserListDTO> lstUsers = getDataFromDataSource();
        this.UserTableView.setItems(lstUsers);
    }
    public void setUpEditDeleteButton() {
        Callback<TableColumn<UserListDTO, String>, TableCell<UserListDTO, String>> cellCallback = new Callback<TableColumn<UserListDTO, String>, TableCell<UserListDTO, String>>() {
            @Override
            public TableCell<UserListDTO, String> call(TableColumn<UserListDTO, String> userListDTOStringTableColumn) {
                final TableCell<UserListDTO, String> cell = new TableCell<>() {
                    private final Button viewButton = new Button();

                    {
                        FontAwesomeIconView viewIcon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                        viewIcon.setStyle(" -fx-cursor: hand ;"
                                + "-glyph-size:24px;"
                                + "-fx-fill:#00E676;");

                        viewButton.setGraphic(viewIcon);
                        viewButton.getStyleClass().add("edit-button");
                        viewButton.setOnAction(actionEvent -> {
                            UserListDTO userDTO = getTableView().getItems().get(getIndex());
                            System.out.println("user" + userDTO);
                            Map<String, String> map = new HashMap<>();
                            map.put("id", String.valueOf(userDTO.getId()));
                            try {
                                UserDetailsDTO userDetail = userAPIService.getDetailsUser(map);
                                System.out.println("userDetail " + userDetail);
                                openEditDialog(userDetail,map);

                            } catch (ApiResponseException exception) {
                                System.out.println(exception.getExceptionResponse());
                            }
                        });
                        setGraphic(viewButton);
                    }
                    @Override
                    protected void updateItem(String item , boolean empty){
                        super.updateItem(item,empty);
                        if(empty){
                            setGraphic(null);
                        }
                        else {
                            setGraphic(viewButton);
                        }
                    }
                };
                return cell ;
            }
        };
        UserActionColumn.setCellFactory(cellCallback);
    }


public void openCreateDialog() {
    String fxmlPath = "views/user_view/create.fxml";
    try {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
        Parent root = loader.load();
        AddUserController addUserController = loader.getController();
        addUserController.setCreateMode(true);
//        addUserController.initialize(null, null);
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Tạo mới người dùng");
        dialogStage.setScene(new Scene(root));
        dialogStage.setOnHidden(e -> {
            Boolean resultSubmit = addUserController.submit();
            if (Objects.equals(resultSubmit, true)) {
                reloadTable();
            }
        });
        dialogStage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
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

    public void handleEditUser(ActionEvent actionEvent) {
    }



    }
