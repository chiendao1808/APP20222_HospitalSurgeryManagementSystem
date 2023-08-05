package com.app20222.app20222_fxapp.app_controllers.department_view;

import com.app20222.app20222_fxapp.dto.common.CommonIdCodeName;
import com.app20222.app20222_fxapp.dto.requests.department.DepartmentCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.patient.PatientCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.patient.PatientUpdateDTO;
import com.app20222.app20222_fxapp.enums.users.IdentityTypeEnum;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.comboBox.ComboBoxAPIService;
import com.app20222.app20222_fxapp.services.department.DepartmentAPIService;
import com.app20222.app20222_fxapp.utils.DateUtils;
import com.app20222.app20222_fxapp.utils.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AddDepartmentController implements Initializable {
    @FXML
    private DialogPane createDepartmentPane;

    @FXML
    private TextField departmentAddress;

    @FXML
    private TextField departmentCode;

    @FXML
    private TextField departmentDescription;

    @FXML
    private TextField departmentEmail;

    @FXML
    private TextField departmentLogoPath;

    @FXML
    private TextField departmentName;

    @FXML
    private TextField departmentPhone;
    private Boolean reloadRequired = false;

    private String messageError;
    private DepartmentAPIService departmentAPIService;
    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
    public String getMessageError() {
        return messageError;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupButtonEventFilters();
        departmentAPIService = new DepartmentAPIService();
    }


    // lấy list danh sách bệnh nhân combobox

    private void setupButtonEventFilters() {
        Button okButton = (Button) createDepartmentPane.lookupButton(ButtonType.OK);
        okButton.setOnAction(event -> handleButtonAction(ButtonType.OK));

        Button cancelButton = (Button) createDepartmentPane.lookupButton(ButtonType.CANCEL);
        cancelButton.setOnAction(event -> handleButtonAction(ButtonType.CANCEL));
    }
    // Hàm hiển thị thông báo validate
    public void displayErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Thông tin không hợp lệ");
        alert.setContentText(getMessageError());
        alert.showAndWait();
    }

    // Kiểm tra xem đã nhập đủ các trường chưa
    public boolean isAllFieldsFilled() {
        String name = departmentName.getText();
        String code = departmentCode.getText();
        String description = departmentDescription.getText();
        String email = departmentEmail.getText();
        String address = departmentAddress.getText();
        String phone = departmentPhone.getText();
        String logoPath = departmentLogoPath.getText();
        if (name == null || name.trim().isEmpty()
                || code == null || code.trim().isEmpty()
                || description == null || description.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || phone == null || phone.trim().isEmpty()
                || logoPath == null || logoPath.trim().isEmpty()
                || address == null || address.trim().isEmpty() ) {
            this.setMessageError("Vui lòng nhập đầy đủ thông tin trước khi tiếp tục.");
            return false;
        }
        return true;
    }

    public boolean isValidEmail(String email) {
        return Validation.isValidEmail(email);
    }
    // validate
    public boolean isValidPhoneNumber(String phoneNumber) {
        return Validation.isValidPhoneNumber(phoneNumber);
    }
    public void handleButtonAction(ButtonType buttonType) {
        if (buttonType.getButtonData() == ButtonType.OK.getButtonData() ) {
            reloadRequired = handleOkButton();
            System.out.println(reloadRequired);
            if (reloadRequired) {
                createDepartmentPane.getScene().getWindow().hide();
            }
        } else {
            createDepartmentPane.getScene().getWindow().hide();
        }

    }

    private Boolean handleOkButton() {
        Boolean result = false;
        if(isAllFieldsFilled()){
            String name = departmentName.getText();
            String code = departmentCode.getText();
            String description = departmentDescription.getText();
            String email = departmentEmail.getText();
            String address = departmentAddress.getText();
            String phoneNumber = departmentPhone.getText();
            String logoPath = departmentLogoPath.getText();
            // Kiểm tra tính hợp lệ của email
            boolean isEmailValid = isValidEmail(email);
            // Kiểm tra tính hợp lệ của số điện thoại
            boolean isPhoneNumberValid = isValidPhoneNumber(phoneNumber);

            if (isEmailValid && isPhoneNumberValid) {
                // Gather the information from the input fields
                    DepartmentCreateDTO newDepartment = DepartmentCreateDTO.builder()
                            .name(name)
                            .code(code)
                            .email(email)
                            .phoneNumber(phoneNumber)
                            .address(address)
                            .description(description)
                            .logoPath(logoPath)
                            .build();
                    // API Call
                    try {
                        result = departmentAPIService.createDepartment(newDepartment);
                    } catch (ApiResponseException exception) {
                        exception.printStackTrace();
                        System.out.println(exception.getExceptionResponse());
                    }
            } else {
                StringBuilder errorMessages = new StringBuilder();
                if (!isEmailValid) {
                    errorMessages.append("- Email không hợp lệ\n");
                    this.setMessageError(String.valueOf(errorMessages));
                }
                if (!isPhoneNumberValid) {
                    errorMessages.append("- Số điện thoại không hợp lệ\n");
                    this.setMessageError(String.valueOf(errorMessages));
                }
                displayErrorAlert();
                return false;
            }
        } else {
            displayErrorAlert();
            return false;
        }
        return result;
    }
    public Boolean submit() {
        return reloadRequired;
    }
}
