package com.app20222.app20222_fxapp.app_controllers.user_view;

import com.app20222.app20222_fxapp.dto.common.CommonIdCodeName;
import com.app20222.app20222_fxapp.enums.users.IdentityTypeEnum;
import com.app20222.app20222_fxapp.enums.users.RoleEnum;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.comboBox.ComboBoxAPIService;
import com.app20222.app20222_fxapp.services.users.UserAPIService;
import com.app20222.app20222_fxapp.utils.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddUserController {

    @FXML
    private TextField CodeNumberView;

    @FXML
    private ComboBox<CommonIdCodeName> DepartmentIdView;

    @FXML
    private CheckComboBox<String> RoleListIdView;

    @FXML
    private TextField addressView;

    @FXML
    private DatePicker birthDateView;

    @FXML
    private DialogPane createUserPane;

    @FXML
    private Button editUser;

    @FXML
    private TextField emailView;

    @FXML
    private TextField firstNameView;

    @FXML
    private TextField identificationNumberView;

    @FXML
    private ComboBox<String> identityTypeView;

    @FXML
    private TextField lastNameView;

    @FXML
    private TextField phoneNumberView;
    private Map<String, String> params;
    private boolean editMode = false;
    private boolean createMode = false;
    private Boolean reloadRequired = false;
    private final Map<String,String> identityTypeMap = new HashMap<>();
    private final Map<String,String> roleTypeMap = new HashMap<>();
    private ComboBoxAPIService comboBoxAPIService  ;


    @FXML
    void handleEditPatient(ActionEvent event) {
    }
    private UserAPIService userAPIService ;


    public void setCreateMode(boolean createMode) {
        this.createMode = createMode ;
    }
    public void setEditMode(boolean editMode){
        this.editMode = editMode ;
    }
    public void setUserId(Map<String,String> map){
        this.params = map ;
    }

    // boi dam cac truong khi xem chi tiet

    public void setBirthDateView() {
        // Set the date format for the DatePicker to "dd/MM/yyyy"
        birthDateView.setConverter(new StringConverter<>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate != null) {
                    return dateFormatter.format(localDate);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString != null && !dateString.trim().isEmpty()) {
                    try {
                        Date date = simpleDateFormat.parse(dateString);
                        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    return null;
                }
            }
        });
    }

public Boolean submit() {
    return reloadRequired;
}

    public void setupIdentityTypes() {
        identityTypeMap.put(IdentityTypeEnum.CITIZEN_ID_CARD.name(), IdentityTypeEnum.CITIZEN_ID_CARD.getType());
        identityTypeMap.put(IdentityTypeEnum.ID_CARD.name(), IdentityTypeEnum.ID_CARD.getType());
        identityTypeMap.put(IdentityTypeEnum.PASSPORT.name(), IdentityTypeEnum.PASSPORT.getType());
        // Create an ObservableList to hold the labels for the identity types
        ObservableList<String> identityTypeLabels = FXCollections.observableArrayList(identityTypeMap.values());

        // Set the items in the ComboBox to display the identity type labels
        identityTypeView.setItems(identityTypeLabels);

        // Set a StringConverter to map the selected identityType to its corresponding value
        identityTypeView.setConverter(new StringConverter<>() {
            @Override
            public String toString(String label) {
                return label; // Display the label in the ComboBox
            }

            @Override
            public String fromString(String string) {
                // Convert the label back to its corresponding value
                IdentityTypeEnum type = IdentityTypeEnum.typeOf(string);
                return type == null ? "" : type.name();
            }
        });
    }
    public void handleButtonAction(ButtonType buttonType) {
        if (buttonType.getButtonData() == ButtonType.OK.getButtonData() ) {
            reloadRequired = handleOkButton();
            System.out.println(reloadRequired);
            if (reloadRequired) {
                createUserPane.getScene().getWindow().hide();
            }
        } else {
            createUserPane.getScene().getWindow().hide();
        }

        // For "Cancel" or when reload is not required, just close the alert without affecting the main pane
    }
    private void setupButtonEventFilters() {
        Button okButton = (Button) createUserPane.lookupButton(ButtonType.OK);
        okButton.setOnAction(event -> handleButtonAction(ButtonType.OK));

        Button cancelButton = (Button) createUserPane.lookupButton(ButtonType.CANCEL);
        cancelButton.setOnAction(event -> handleButtonAction(ButtonType.CANCEL));
    }
    public void initialize(URL location, ResourceBundle resources) {
        setupIdentityTypes();
        setBirthDateView();
        setUpDepartment();
        setupRoleType();
        setupButtonEventFilters();
        comboBoxAPIService = new ComboBoxAPIService();
        userAPIService = new UserAPIService();
        if (createMode) {
            editUser.setVisible(false); // Hide the button for create mode
            Button okButton = (Button) createUserPane.lookupButton(ButtonType.OK);
            okButton.setVisible(true);
            Button cancelButton = (Button) createUserPane.lookupButton(ButtonType.CANCEL);
            cancelButton.setVisible(true);
        } else {
            editUser.setVisible(true); // Show the button for view mode
            Button okButton = (Button) createUserPane.lookupButton(ButtonType.OK);
            okButton.setVisible(false);
            Button cancelButton = (Button) createUserPane.lookupButton(ButtonType.CANCEL);
            cancelButton.setVisible(false);
        }
    }
    public void setUpDepartment(){
        ObservableList<CommonIdCodeName> listPatientId = getDataFromDataDepartmentSource();
        ObservableList<CommonIdCodeName> observableList = FXCollections.observableArrayList(listPatientId);
        DepartmentIdView.setConverter(new StringConverter<CommonIdCodeName>() {
            @Override
            public String toString(CommonIdCodeName item) {
                return item.getName() + " - " + item.getCode(); // Hiển thị tên bệnh nhân trong ComboBox
            }

            @Override
            public CommonIdCodeName fromString(String string) {
                // Chuyển đổi ngược từ chuỗi (nếu cần)
                return null;
            }
        });
        DepartmentIdView.setItems(observableList);
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
        // Create an ObservableList to hold the labels for the identity types
        ObservableList<String> roleTypeLabels = FXCollections.observableArrayList();
        for (String s : roleTypeMap.values()){
            roleTypeLabels.add(s);
        }
//        final CheckComboBox<String> checkComboBox = new CheckComboBox<String>() ;
//        checkComboBox.getItems().addAll(roleTypeLabels);
        RoleListIdView.getItems().addAll(roleTypeLabels);

        // Set the items in the CheckComboBox to display the identity type labels
        RoleListIdView.setConverter(new StringConverter<>() {
            @Override
            public String toString(String label) {
                return label; // Display the label in the ComboBox
            }

            @Override
            public String fromString(String string) {
                // Convert the label back to its corresponding value
                RoleEnum type = RoleEnum.typeOf(string);
                return type == null ? "" : type.name();
            }
        });

    }
    public ObservableList<CommonIdCodeName> getDataFromDataDepartmentSource(){
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
    public Boolean handleOkButton() {
        Boolean result = false;
        // Kiểm tra xem tất cả các trường đã được nhập
        boolean isEmailValid = false;
        if (isAllFieldsFilled()) {
            // Lấy giá trị từ các thành phần
            String identificationNumber = identificationNumberView.getText();
            String firstName = firstNameView.getText();
            String lastName = lastNameView.getText();
            String identityTypeLabel = identityTypeView.getValue();
            String codeNumber = CodeNumberView.getText();
            LocalDate birthDateRaw = birthDateView.getValue();
            String address = addressView.getText();
            String phoneNumber = phoneNumberView.getText();
            String email = emailView.getText();
            String identityType = identityTypeView.getConverter().toString(identityTypeLabel);
            String birthDate = birthDateView.getConverter().toString(birthDateRaw);
            ObservableList<String> roleListLabel  = RoleListIdView.getCheckModel().getCheckedItems();
            String role = RoleListIdView.getConverter().toString(String.valueOf(roleListLabel));
            CommonIdCodeName department = DepartmentIdView.getValue();

            System.out.println("Mã số chứng thức: " + identificationNumber);
            System.out.println("Loaị giấy tờ" + identityType);
            System.out.println("Tên: " + firstName);
            System.out.println("Họ:" + lastName);
            System.out.println("Mã nhân viên : " + codeNumber);
            System.out.println("Ngày sinh: " + birthDate);
            System.out.println("Địa chỉ: " + address);
            System.out.println("Số điện thoại: " + phoneNumber);
            System.out.println("Email: " + email);
            System.out.println("Quyền : " + role);
            System.out.println("Phòng ban : " + department);





//            // Kiểm tra tính hợp lệ của email
//            boolean isEmailValid = isValidEmail(email);
//            // Kiểm tra tính hợp lệ của số điện thoại
//            boolean isPhoneNumberValid = isValidPhoneNumber(phoneNumber);

//            if (isEmailValid && isPhoneNumberValid) {
////                 Gather the information from the input fields
////                    UserCreateDTO newUser = UserCreateDTO.builder()
////                            .identityType(IdentityTypeEnum.typeOf(identityType))
////                            .identificationNumber(identificationNumber)
////                            .firstName(firstName)
////                            .lastName(lastName)
////                            .code(codeNumber)
////                            .phoneNumber(phoneNumber)
////                            .email(email)
////                            .address(address)
////                            .birthDate(DateUtils.asDate(birthDateRaw))
////                            .lstRoleId()
////                            .build(); }
//                    // API Call
//                    try {
//                        result = patientAPIService.createPatient(newPatient);
//                    } catch (ApiResponseException exception) {
//                        exception.printStackTrace();
//                        System.out.println(exception.getExceptionResponse());
//                    }
//                }
//        } else {
//            // Hiển thị thông báo lỗi cho các trường không hợp lệ
//            StringBuilder errorMessages = new StringBuilder();
//            if (!isEmailValid) {
//                errorMessages.append("- Email không hợp lệ\n");
//            }
//            if (! isValidPhoneNumber(phoneNumber)) {
//                errorMessages.append("- Số điện thoại không hợp lệ\n");
//            }
//            displayErrorAlert(errorMessages.toString());
//            return false;
//        }
    } else {
            // Hiển thị thông báo lỗi nếu chưa nhập đủ các trường
            displayErrorAlert("Vui lòng nhập đầy đủ thông tin trước khi tiếp tục.");
            return false;
        }
        return result;
    }
    public Boolean isAllFieldsFilled(){
        String identificationNumber = identificationNumberView.getText();
        String identifyType = identityTypeView.getValue();
        String firstName = firstNameView.getText();
        String lastName = lastNameView.getText();
        String code = CodeNumberView.getText() ;
        LocalDate birthDate = birthDateView.getValue();
        String address = addressView.getText();
        String phone = phoneNumberView.getText();
        String email = emailView.getText();
        ObservableList<String> roleList = RoleListIdView.getItems();
        String department = String.valueOf(DepartmentIdView.getValue());
        return Validation.isAllFieldsFilledAddUser(identificationNumber,firstName,lastName,identifyType,department,roleList,code
                ,birthDate,address,phone,email);
    }
    public boolean isValidEmail(String email) {
        return Validation.isValidEmail(email);
    }
    // validate
    public boolean isValidPhoneNumber(String phoneNumber) {
        return Validation.isValidPhoneNumber(phoneNumber);
    }
    public void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Thông tin không hợp lệ");
        alert.setContentText(message);
        alert.showAndWait();
    }

}

