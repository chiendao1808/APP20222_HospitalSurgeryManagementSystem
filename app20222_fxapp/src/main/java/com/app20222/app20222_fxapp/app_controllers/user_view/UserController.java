package com.app20222.app20222_fxapp.app_controllers.user_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.dto.common.CommonIdCodeName;
import com.app20222.app20222_fxapp.dto.responses.users.RoleDTO;
import com.app20222.app20222_fxapp.dto.responses.users.UserDetailsDTO;
import com.app20222.app20222_fxapp.dto.responses.users.UserListDTO;
import com.app20222.app20222_fxapp.enums.users.RoleEnum;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.comboBox.ComboBoxAPIService;
import com.app20222.app20222_fxapp.services.users.UserAPIService;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class UserController {

    @FXML
    private TableColumn<UserListDTO, String> UserActionColumn;

    @FXML
    private TableColumn<UserListDTO, String> UserAddressColumn;

    @FXML
    private TableColumn<UserListDTO, Date> UserDateColumn;

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
    // Tìm kiếm
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
    private Button userClearSearch ;
    @FXML
    void onUserClearSearch(ActionEvent event) {

    }


    @FXML
    public void onUserSubmitSearch(ActionEvent event) {
        String code = userSearchCode.getText();
        String email = userSearchEmail.getText();
        String phone = userSearchPhone.getText();
        String name = userSearchName.getText();
        String roleLabel = userSearchRole.getValue();
        String departmentLabel = userSearchDepartment.getValue();
        assert RoleEnum.typeOf(roleLabel) != null;
        Long roleId = RoleEnum.typeOf(roleLabel).getId();
        Long departmentId = getIdDepartmentFromName(departmentLabel);
        searchParams.put("code",code);
        searchParams.put("name",name);
        searchParams.put("email",email);
        searchParams.put("phone",phone);
        searchParams.put("departmentId", String.valueOf(departmentId));
        searchParams.put("roleId", String.valueOf(roleId));
        reloadTable();

    }


    private UserAPIService userAPIService;
    private ComboBoxAPIService comboBoxAPIService ;
    Map<String,String> map = new HashMap<>();
    private Map<String, String> searchParams = new HashMap<>();
    private final Map<String,String> roleTypeMap = new HashMap<>();

    public UserController( ){}

    public UserController(TableView<UserListDTO> userTableView, TableColumn<UserListDTO, String> userActionColumn,
                          TableColumn<UserListDTO, String> userAddressColumn,
                          TableColumn<UserListDTO, Date> userDateColumn,
                          TableColumn<UserListDTO, String> userDepartmentColumn, TableColumn<UserListDTO, String> userEmailColumn,
                          TableColumn<UserListDTO, String> userIdentificationNumColumn, TableColumn<UserListDTO, String> userNameColumn,
                          TableColumn<UserListDTO, String> userPhoneColumn , TableColumn<UserListDTO,Long> userSttColumn,
                          TableColumn<UserListDTO,String> userIdentityTypeColumn,
                          TextField userSearchCode, ComboBox<String> userSearchDepartment,
                          TextField userSearchEmail, TextField userSearchName,
                          TextField userSearchPhone, ComboBox<String> userSearchRole,
                          Button userSubmitSearch, Button userClearSearch) {
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
        this.userSearchCode = userSearchCode;
        this.userSearchDepartment = userSearchDepartment;
        this.userSearchEmail = userSearchEmail;
        this.userSearchName = userSearchName;
        this.userSearchPhone = userSearchPhone;
        this.userSearchRole = userSearchRole;
        this.userSubmitSearch = userSubmitSearch ;
        this.userClearSearch = userClearSearch;
    }

    public TableView<UserListDTO> getUserTableView() {
        return this.UserTableView;
    }

    public void initializeUser(){
        userAPIService = new UserAPIService();
        comboBoxAPIService = new ComboBoxAPIService();
        initializeTable();
        initSearchUser();
    }
    // fomat date về string
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
    public void initializeTable() {
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        ObservableList<UserListDTO> userList = getDataFromDataSource();
        setupTableColumns();
        setUpEditDeleteButton();

        if (UserTableView != null) {
            this.UserTableView.setItems(userList);
            UserDateColumn.setCellFactory(column -> new TableCell<>() {
                @Override
                protected void updateItem(Date date, boolean empty) {
                    super.updateItem(date, empty);
                    if (empty || date == null) {
                        setText(null);
                    } else {
                        setText(formatDate(date));
                    }
                }
            });
        }
    }

    public void openEditDialog(UserDetailsDTO userDetailsDTO , Map<String,String> param) {
        String fxmlPath = "views/user_view/view-detail.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            ViewUserDeatailController viewUserDeatailController = loader.getController();
            viewUserDeatailController.disableAllFields();
            viewUserDeatailController.initialize(null,null);
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
                    userDetailsDTO.getRoles(),
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
        if(UserTableView != null){
            this.UserTableView.setItems(lstUsers);
        }
        setUpEditDeleteButton();
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
            lstUsers = userAPIService.getListUsers(searchParams);
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        
        return FXCollections.observableArrayList(lstUsers);
    }

    // Khởi tạo các thành phần tìm kiếm
    public void initSearchUser(){
        setUpDepartment();
        setupRoleType();
    }
    // list Role
    public ObservableList<CommonIdCodeName> getDataDepartment(){
        List<CommonIdCodeName> listDepartment = new ArrayList<>();
        try {
            Map<String,String> map = new HashMap<>();
            listDepartment = comboBoxAPIService.getComboBoxDepartments(map);
        }catch (ApiResponseException e){
            e.getStackTrace();
            System.out.println(e.getExceptionResponse());
        }
        return FXCollections.observableArrayList(listDepartment);
    }
    public void setupRoleType(){
        roleTypeMap.put(RoleEnum.DEPARTMENT_ADMIN.name() ,RoleEnum.DEPARTMENT_ADMIN.getRoleName());
        roleTypeMap.put(RoleEnum.SUPER_ADMIN.name(),RoleEnum.SUPER_ADMIN.getRoleName());
        roleTypeMap.put(RoleEnum.HOSPITAL_ADMIN.name(), RoleEnum.HOSPITAL_ADMIN.getRoleName());
        roleTypeMap.put(RoleEnum.HOSPITAL_MANAGER.name(), RoleEnum.HOSPITAL_MANAGER.getRoleName());
        roleTypeMap.put(RoleEnum.DEPARTMENT_MANAGER.name(), RoleEnum.DEPARTMENT_MANAGER.getRoleName());
        roleTypeMap.put(RoleEnum.DOCTOR.name(), RoleEnum.DOCTOR.getRoleName());
        roleTypeMap.put(RoleEnum.NURSE.name(), RoleEnum.NURSE.getRoleName());
        roleTypeMap.put(RoleEnum.STAFF.name(), RoleEnum.STAFF.getRoleName());
        ObservableList<String> roleTypeLabels = FXCollections.observableArrayList(roleTypeMap.values());

        userSearchRole.setItems(roleTypeLabels);

        userSearchRole.setConverter(new StringConverter<>() {
            @Override
            public String toString(String label) {
                return label; // Display the label in the ComboBox
            }

            @Override
            public String fromString(String string) {
                // Convert the label back to its corresponding value (name of the enum)
                for (Map.Entry<String, String> entry : roleTypeMap.entrySet()) {
                    if (entry.getValue().equals(string)) {
                        return entry.getKey();
                    }
                }
                return "";
            }
        });

        userSearchRole.valueProperty().addListener((observable, oldValue, newValue) -> {
            String roleId = RoleEnum.valueOf(newValue).getId().toString();
            // Now you have the selected role ID (roleId)
            // You can use it for further processing if needed
            // For example, you can store it in your searchParams map
            searchParams.put("roleId", roleId);
        });

    }
    // Khởi tạo ist khoa/ bộ phận
    public void setUpDepartment(){
        ObservableList<CommonIdCodeName> listDepartment = getDataDepartment();
        System.out.println("List department " + listDepartment);
        ObservableList<String> names = FXCollections.observableArrayList();
        for(CommonIdCodeName cic : listDepartment){
            names.add(cic.getName());
        }
        userSearchDepartment.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String item) {
                return item ;
            }

            @Override
            public String fromString(String s) {
                return null;
            }
        });
        userSearchDepartment.setItems(names);
    }

    public Long getIdDepartmentFromName(String department){
        ObservableList<CommonIdCodeName> listDepartment = getDataDepartment();
        for(CommonIdCodeName cic : listDepartment){
            if(cic.getName().equals(department)){
                return cic.getId();
            }
        }
        return null;
    }


    @FXML
    public void onDashBoardSubmitSearch(ActionEvent event) {
        String userName = userSearchName.getText();
        String userCode = userSearchCode.getText();
        String userEmail = userSearchEmail.getText();
        String userPhone = userSearchPhone.getText();
        String departmentLabel = userSearchDepartment.getValue();
        Long department = getIdDepartmentFromName(departmentLabel);
        searchParams.put("name", userName);
        searchParams.put("code", userCode);
        searchParams.put("email", userEmail);
        searchParams.put("phone", userPhone);
        searchParams.put("department", String.valueOf(department));
        reloadTable();
    }

    @FXML
    public void clearParams(ActionEvent event) {
        resetSearchParams();
        searchParams.clear(); // Clear the search parameters
        reloadTable();
    }

    public void resetSearchParams() {
        userSearchEmail.setText("");
        userSearchCode.setText("");
        userSearchName.setText("");
        userSearchPhone.setText("");
        userSearchDepartment.setValue(null);
        userSearchRole.setValue(null);

    }
}
