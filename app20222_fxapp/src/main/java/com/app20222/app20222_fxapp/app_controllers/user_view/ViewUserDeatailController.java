package com.app20222.app20222_fxapp.app_controllers.user_view;

import com.app20222.app20222_fxapp.utils.DateUtils;
import com.app20222.app20222_fxapp.utils.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private TextField departmentIdView;

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
    private ComboBox<String> roleListIdView;
    private boolean editMode = false;
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

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
//    public Boolean isAllFieldsFilled(){
//        String identificationNumber = identificationNumberView.getText();
//        String identifyType = identityTypeView.getValue();
//        String name = nameView.getText();
//        String code = codeNumberView.getText();
//        LocalDate birthDate = birthDateView.getValue();
//        String address = addressView.getText();
//        String phone = phoneNumberView.getText();
//        String email = emailView.getText();
//        String roleList = roleListIdView.getValue();
//        String department = departmentIdView.getText();
//        return Validation.isAllFieldsFilledUser(identificationNumber,name,identifyType,department,roleList,code
//        ,birthDate,address,phone,email);
//    }
//    // xử lý thông tin nhập vào khi chỉnh sửa
//    public Boolean handleOkButton(){
//        Boolean result = false ;
//        if(isAllFieldsFilled()){
//            String identificationNumber = identificationNumberView.getText();
//            String identifyTypeLabel = identityTypeView.getValue();
//            String name = nameView.getText();
//            String code = codeNumberView.getText();
//            LocalDate birthDateRaw = birthDateView.getValue();
//            String address = addressView.getText();
//            String phone = phoneNumberView.getText();
//            String email = emailView.getText();
//            String roleListLabel  = roleListIdView.getValue();
//            String department = departmentIdView.getText();
//            String identifyType = identityTypeView.getConverter().toString(identifyTypeLabel);
//            String role = roleListIdView.getConverter().toString(roleListLabel);
//            String birthDate = birthDateView.getConverter().toString(birthDateRaw);
//            boolean isEmailValid = isValidEmail(email) ;
//
//            boolean isPhoneValid = isValidPhoneNumber(phone);
//
//            if(isEmailValid && isPhoneValid ){
//                System.out.println("Mã số chứng thức: " + identificationNumber);
//                System.out.println("Loaị giấy tờ" + identifyType);
//                System.out.println("Tên: " + name);
//                System.out.println("Mã nhân viên : " + code);
//                System.out.println("Ngày sinh: " + birthDate);
//                System.out.println("Địa chỉ: " + address);
//                System.out.println("Số điện thoại: " + phone);
//                System.out.println("Email: " + email);
//                System.out.println("Quyền : " + role);
//                System.out.println("Phòng ban : " + department);
//                System.out.println("id: " + params);
//
//                if(editMode){
//
//                }
//            }
//
//        }
//    }
    Map<String,String> params = new HashMap<>();
    public void setUserId(Map<String, String> params) {
        this.params = params;
    }
    private Boolean reloadRequired = false;

    public void setText(String identificationNumber, String identifyType, String name, String codeNumber,
                        Date birthDate, String address, String phoneNumber, String email,
                        String roleList, String departmentId) {
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
        if (roleList != null) {
            roleListIdView.setValue(roleList);
        }
        if (departmentId != null) {
            departmentIdView.setText(departmentId);
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

}
