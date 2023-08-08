package com.app20222.app20222_fxapp.app_controllers.user_view;

import com.app20222.app20222_fxapp.dto.common.CommonIdCodeName;
import com.app20222.app20222_fxapp.dto.requests.users.UserCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.users.UserUpdateDTO;
import com.app20222.app20222_fxapp.dto.responses.users.RoleDTO;
import com.app20222.app20222_fxapp.enums.users.IdentityTypeEnum;
import com.app20222.app20222_fxapp.enums.users.RoleEnum;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.comboBox.ComboBoxAPIService;
import com.app20222.app20222_fxapp.services.users.UserAPIService;
import com.app20222.app20222_fxapp.utils.DateUtils;
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

public class ViewUserDeatailController {
    @FXML
    private TextField addressView;

    @FXML
    private DatePicker birthDateView;

    @FXML
    private TextField codeNumberView;

    @FXML
    private DialogPane createUserPane;

    @FXML
    private ComboBox<String> departmentIdView;

    @FXML
    private Button editUser;

    @FXML
    private TextField emailView;

    @FXML
    private TextField identificationNumberView;

    @FXML
    private ComboBox<String> identityTypeView;

    @FXML
    private TextField nameView;

    @FXML
    private TextField phoneNumberView;

    @FXML
    private CheckComboBox<String> roleListIdView;
    private UserAPIService userAPIService ;
    private boolean editMode = false;
    private boolean createMode = false;
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    private final Map<String, String> identityTypeMap = new HashMap<>();
    private final Map<String,String> roleTypeMap = new HashMap<>();
    private ComboBoxAPIService comboBoxAPIService = new ComboBoxAPIService();

    @FXML
    void handleEditUser(ActionEvent event) {
        setEditMode(true);
        identificationNumberView.setDisable(false);
        identityTypeView.setDisable(false);
        nameView.setDisable(false);
        codeNumberView.setDisable(false);
        birthDateView.setDisable(false);
        addressView.setDisable(false);
        phoneNumberView.setDisable(false);
        roleListIdView.setDisable(false);
        departmentIdView.setDisable(false);
        String boldStyle = "-fx-font-weight: normal;";
        applyBoldStylingToDisabledFields(boldStyle);
        Button OkButton = (Button) createUserPane.lookupButton(ButtonType.OK);
        OkButton.setVisible(true);
        Button CancelButton = (Button) createUserPane.lookupButton(ButtonType.CANCEL);
        CancelButton.setVisible(true);
        editUser.setVisible(false);
    }
    public Boolean isAllFieldsFilled(){
        String identificationNumber = identificationNumberView.getText();
        String identifyType = identityTypeView.getValue();
        String name = nameView.getText();
        String code = codeNumberView.getText();
        LocalDate birthDate = birthDateView.getValue();
        String address = addressView.getText();
        String phone = phoneNumberView.getText();
        String email = emailView.getText();
        ObservableList<String> roleList = roleListIdView.getItems();
        String department = String.valueOf(departmentIdView.getValue());
        return Validation.isAllFieldsFilledUser(identificationNumber,name,identifyType,department,roleList,code
        ,birthDate,address,phone,email);
    }
    // xử lý thông tin nhập vào khi chỉnh sửa
    public Boolean handleOkButton(){
        Boolean result = false ;
        if(isAllFieldsFilled()){
            String identificationNumber = identificationNumberView.getText();
            String identifyTypeLabel = identityTypeView.getValue();
            String name = nameView.getText();
            String code = codeNumberView.getText();
            LocalDate birthDateRaw = birthDateView.getValue();
            String address = addressView.getText();
            String phone = phoneNumberView.getText();
            String email = emailView.getText();
            ObservableList<String> roleListLabel  = roleListIdView.getCheckModel().getCheckedItems();
            String department = departmentIdView.getValue();
            String identifyType = identityTypeView.getConverter().toString(identifyTypeLabel);
            String role = roleListIdView.getConverter().toString(String.valueOf(roleListLabel));
            String birthDate = birthDateView.getConverter().toString(birthDateRaw);
            boolean isEmailValid = isValidEmail(email) ;

            boolean isPhoneValid = isValidPhoneNumber(phone);

            if(isEmailValid && isPhoneValid ){
                System.out.println("Mã số chứng thức: " + identificationNumber);
                System.out.println("Loaị giấy tờ" + identifyType);
                System.out.println("Tên: " + name);
                System.out.println("Mã nhân viên : " + code);
                System.out.println("Ngày sinh: " + birthDate);
                System.out.println("Địa chỉ: " + address);
                System.out.println("Số điện thoại: " + phone);
                System.out.println("Email: " + email);
                System.out.println("Quyền : " + role);
                System.out.println("Phòng ban : " + department);
                System.out.println("id: " + params);
                String firstName = getFirstName(name);
                String lastName = getLastName(name);

                if(editMode){
                    UserUpdateDTO updateDTO = UserUpdateDTO.builder()
                            .identityType(IdentityTypeEnum.typeOf(identifyType))
                            .identificationNumber(identificationNumber)
                            .firstName(firstName)
                            .lastName(lastName)
                            .phoneNumber(phone)
                            .birthDate(DateUtils.asDate(birthDateRaw))
                            .address(address)
                            .email(email)
                            .departmentId(getIdDepartmentFromName(department))
                            .lstRoleId(getRoleIdFromRoleName(roleListLabel)).build();
                    try {
                        result = userAPIService.updateUser(updateDTO,params);
                    }catch (ApiResponseException e){
                        e.getStackTrace();
                        System.out.println(e.getExceptionResponse());
                    }
                }
                else {
                    UserCreateDTO createDTO = UserCreateDTO.builder()
                            .identificationNumber(identificationNumber)
                            .identityType(IdentityTypeEnum.typeOf(identifyType))
                            .firstName(firstName)
                            .lastName(lastName)
                            .phoneNumber(phone)
                            .birthDate(DateUtils.asDate(birthDateRaw))
                            .email(email)
                            .departmentId(getIdDepartmentFromName(department))
                            .address(address)
                            .code(code)
                            .lstRoleId( getRoleIdFromRoleName(roleListLabel)).build();
                            try {
                                result = userAPIService.createUser(createDTO);
                            }catch (ApiResponseException e){
                                e.getStackTrace();
                                System.out.println(e.getExceptionResponse());
                            }
                }

            }else {
                StringBuilder errorMessage = new StringBuilder();
                if (!isEmailValid) {
                    errorMessage.append("- Email không hợp lệ\n");
                }
                if (!isPhoneValid) {
                    errorMessage.append("- Số điện thoại không hợp lệ\n");
                }
                displayErrorAlert(errorMessage.toString());
                return false;
            }
        }else {
            // Hiển thị thông báo lỗi nếu chưa nhập đủ trường
            displayErrorAlert("Vui Lòng nhập đầy đủ thông tin trước khi tiếp tục");
            return false;
        }
        return result ;
    }
    Map<String,String> params = new HashMap<>();
    public void setUserId(Map<String, String> params) {
        this.params = params;
    }
    private Boolean reloadRequired = false;

    public void setText(String identificationNumber, String identifyType, String name, String codeNumber,
                        Date birthDate, String address, String phoneNumber, String email,
                        String roles, String department ) {
        if (identificationNumber != null) {
            identificationNumberView.setText(identificationNumber);
        }
        if (identifyType != null) {
            identityTypeView.setValue(identifyType);
        }
        if (name != null) {
            nameView.setText(name);
        }

        if (codeNumber != null) {
            codeNumberView.setText(codeNumber);
        }
        if (birthDate != null) {
            birthDateView.setValue(DateUtils.asLocalDate(birthDate));
        }
        if (address != null) {
            addressView.setText(address);
        }
        if (phoneNumber != null) {
            phoneNumberView.setText(phoneNumber);
        }
        if (email != null) {
            emailView.setText(email);
        }
        if (roles != null) {
            roleListIdView.getCheckModel().getCheckedItems();
        }
        if (department != null) {
            departmentIdView.setValue(department);
        }

    }
    public void disableAllFields(){
        String boldStyle = "-fx-font-weight: bold;";
        identificationNumberView.setDisable(true);
        identityTypeView.setDisable(true);
        nameView.setDisable(true);
        codeNumberView.setDisable(true);
        birthDateView.setDisable(true);
        addressView.setDisable(true);
        phoneNumberView.setDisable(true);
        emailView.setDisable(true);
        roleListIdView.setDisable(true);
        departmentIdView.setDisable(true);
        applyBoldStylingToDisabledFields(boldStyle);
    }
    public void applyBoldStylingToDisabledFields(String boldStyle) {
        identificationNumberView.setStyle(boldStyle);
        identityTypeView.setStyle(boldStyle);
        nameView.setStyle(boldStyle);
        codeNumberView.setStyle(boldStyle);
        birthDateView.setStyle(boldStyle);
        addressView.setStyle(boldStyle);
        phoneNumberView.setStyle(boldStyle);
        emailView.setStyle(boldStyle);
        roleListIdView.setStyle(boldStyle);
        departmentIdView.setStyle(boldStyle);
    }
    public Boolean submit() {
        return reloadRequired;
    }
    public boolean isValidEmail(String email) {
        return Validation.isValidEmail(email);
    }
    public boolean isValidPhoneNumber(String phoneNumber) {
        return Validation.isValidPhoneNumber(phoneNumber);
    }
    // tách firsName and LastName từ Name
    public String getFirstName(String name){
        String firstName ="";
        String []results = name.split(" ");
        for(String s : results){
            if(s == results[results.length-1]){
                firstName = s ;
            }
        }
        return firstName ;
    }
    public String getLastName(String name){
        String lastName ="" ;
        String []results = name.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : results){
            if( s == results[results.length-1]){
                continue;
            }
            lastName = String.valueOf(stringBuilder.append(s + " "));
        }
        return lastName ;
    }
    public void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Thông tin không hợp lệ");
        alert.setContentText(message);
        alert.showAndWait();
    }
    // setup dữ liệu cho các loại giấy chứng thực
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
    // Khởi tạo ban đầu
    public void initialize(URL location , ResourceBundle resourceBundle){
        setBirthDateView();
        setupButtonEventFilters();
        setUpDepartment();
        setupRoleType();
        setupIdentityTypes();
        userAPIService = new UserAPIService();
        if(createMode){
            editUser.setVisible(false); // hide the button for create mode
            Button OkButton = (Button) createUserPane.lookupButton(ButtonType.OK);
            OkButton.setVisible(true);
            Button cancelButton = (Button) createUserPane.lookupButton(ButtonType.CANCEL);
            cancelButton.setVisible(true);
        }
        else {
            editUser.setVisible(true); // Show the button for view mode
            Button okButton = (Button) createUserPane.lookupButton(ButtonType.OK);
            okButton.setVisible(false);
            Button cancelButton = (Button) createUserPane.lookupButton(ButtonType.CANCEL);
            cancelButton.setVisible(false);
        }

    }
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
    private void setupButtonEventFilters() {
        Button okButton = (Button) createUserPane.lookupButton(ButtonType.OK);
        okButton.setOnAction(event -> handleButtonAction(ButtonType.OK));

        Button cancelButton = (Button) createUserPane.lookupButton(ButtonType.CANCEL);
        cancelButton.setOnAction(event -> handleButtonAction(ButtonType.CANCEL));
    }
    public void handleButtonAction (ButtonType buttonType){
        if(buttonType.getButtonData() == ButtonType.OK.getButtonData()){
            reloadRequired = handleOkButton();
            System.out.println(reloadRequired);
            if(reloadRequired){
                createUserPane.getScene().getWindow().hide();
            }
            else {
                createUserPane.getScene().getWindow().hide();
            }
        }
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
        roleListIdView.getItems().addAll(roleTypeLabels);

        // Set the items in the CheckComboBox to display the identity type labels
        roleListIdView.setConverter(new StringConverter<>() {
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
 // lấy list department đưa vào combo box
    public ObservableList<CommonIdCodeName> getDataFromDataSource(){
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
  // setup dữ liệu department  trong combo box
    public void setUpDepartment(){
        ObservableList<CommonIdCodeName> listDepartment = getDataFromDataSource();
        System.out.println("List department " + listDepartment);
        ObservableList<String> names = FXCollections.observableArrayList();
        for(CommonIdCodeName cic : listDepartment){
            names.add(cic.getName());
        }
        departmentIdView.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String item) {
                return item ;
            }

            @Override
            public String fromString(String s) {
                return null;
            }
        });
        departmentIdView.setItems(names);
    }
    public Long getIdDepartmentFromName(String department){
        ObservableList<CommonIdCodeName> listDepartment = getDataFromDataSource();
        for(CommonIdCodeName cic : listDepartment){
            if(cic.getName().equals(department)){
                return cic.getId();
            }
        }
        return null;
    }
    public Set<Long> getRoleIdFromRoleName(ObservableList<String> roleName){
        Set<Long> listRoleId = new HashSet<>();
        for(String roleEnum : roleName){
            RoleEnum roleEnum1 = RoleEnum.typeOf(roleEnum);
            listRoleId.add(roleEnum1.getId());
        }
        return listRoleId;
    }

}
