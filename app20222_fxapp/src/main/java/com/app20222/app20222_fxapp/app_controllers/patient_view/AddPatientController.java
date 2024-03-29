package com.app20222.app20222_fxapp.app_controllers.patient_view;

import com.app20222.app20222_fxapp.dto.requests.patient.PatientCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.patient.PatientUpdateDTO;
import com.app20222.app20222_fxapp.enums.users.IdentityTypeEnum;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.patient.PatientAPIService;
import com.app20222.app20222_fxapp.utils.DateUtils;
import com.app20222.app20222_fxapp.utils.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddPatientController implements Initializable {

    @FXML
    private DialogPane createPatientPane;

    @FXML
    private TextField addressView;

    @FXML
    private DatePicker birthDateView;

    @FXML
    private TextField emailView;

    @FXML
    private TextField firstNameView;

    @FXML
    private TextField healthInsuranceNumberView;

    @FXML
    private TextField identificationNumberView;

    @FXML
    private ComboBox<String> identityTypeView;

    @FXML
    private TextField lastNameView;

    @FXML
    private TextField phoneNumberView;

    @FXML
    private Button editPatient;

    private Map<String, String> params;
    private boolean editMode = false;
    private boolean createMode = false;
    private Boolean reloadRequired = false;
    // dùng cho loại gấy chưn thực
    private final Map<String, String> identityTypeMap = new HashMap<>();


    private PatientAPIService patientAPIService;
    public AddPatientController() {
    }

    public void setPatientId(Map<String, String> params) {
        this.params = params;
    }
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setCreateMode(boolean createMode) {
        this.createMode = createMode;
    }




    // disable khi mở xem chi tiết
    public void disableAllFields() {
        String boldStyle = "-fx-font-weight: bold;";

        identificationNumberView.setDisable(true);
        firstNameView.setDisable(true);
        lastNameView.setDisable(true);
        identityTypeView.setDisable(true);
        healthInsuranceNumberView.setDisable(true);
        birthDateView.setDisable(true);
        addressView.setDisable(true);
        phoneNumberView.setDisable(true);
        emailView.setDisable(true);
        applyBoldStylingToDisabledFields(boldStyle);
    }
    // Khởi tạo ban đầu
    public void initialize(URL location, ResourceBundle resources) {
        setupIdentityTypes();
        setBirthDateView();
        setupButtonEventFilters();
        patientAPIService = new PatientAPIService();
        if (createMode) {
            editPatient.setVisible(false); // Hide the button for create mode
            Button okButton = (Button) createPatientPane.lookupButton(ButtonType.OK);
            okButton.setVisible(true);
            Button cancelButton = (Button) createPatientPane.lookupButton(ButtonType.CANCEL);
            cancelButton.setVisible(true);
        } else {
            editPatient.setVisible(true); // Show the button for view mode
            Button okButton = (Button) createPatientPane.lookupButton(ButtonType.OK);
            okButton.setVisible(false);
            Button cancelButton = (Button) createPatientPane.lookupButton(ButtonType.CANCEL);
            cancelButton.setVisible(false);
        }

    }

    // bôi đậm các trường khi xem chi tiết
    public void applyBoldStylingToDisabledFields(String boldStyle) {
        identificationNumberView.setStyle(boldStyle);
        firstNameView.setStyle(boldStyle);
        lastNameView.setStyle(boldStyle);
        identityTypeView.setStyle(boldStyle);
        healthInsuranceNumberView.setStyle(boldStyle);
        birthDateView.setStyle(boldStyle);
        addressView.setStyle(boldStyle);
        phoneNumberView.setStyle(boldStyle);
        emailView.setStyle(boldStyle);

    }
    // sét up du lieu cho loai giay chung thuc
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

    // set up 2 nut submit và cancel
    private void setupButtonEventFilters() {
        Button okButton = (Button) createPatientPane.lookupButton(ButtonType.OK);
        okButton.setOnAction(event -> handleButtonAction(ButtonType.OK));

        Button cancelButton = (Button) createPatientPane.lookupButton(ButtonType.CANCEL);
        cancelButton.setOnAction(event -> handleButtonAction(ButtonType.CANCEL));
    }

    // validate
    public boolean isAllFieldsFilled() {
            String identificationNumber = identificationNumberView.getText();
            String firstName = firstNameView.getText();
            String lastName = lastNameView.getText();
            String identityType = identityTypeView.getValue();
            String healthInsuranceNumber = healthInsuranceNumberView.getText();
            LocalDate birthDate = birthDateView.getValue();
            String address = addressView.getText();
            String phoneNumber = phoneNumberView.getText();
            String email = emailView.getText();
            return Validation.isAllFieldsFilledPatient(identificationNumber, firstName, lastName, identityType,
                    healthInsuranceNumber, birthDate, address, phoneNumber, email);

    }
    // validate
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

    // Khi click button chỉnh sửa
    @FXML
    private void handleEditPatient(ActionEvent event) {
        setEditMode(true);
        // Enable all fields for editing
        identificationNumberView.setDisable(false);
        firstNameView.setDisable(false);
        lastNameView.setDisable(false);
        identityTypeView.setDisable(false);
        healthInsuranceNumberView.setDisable(false);
        birthDateView.setDisable(false);
        addressView.setDisable(false);
        phoneNumberView.setDisable(false);
        emailView.setDisable(false);
        String boldStyle = "-fx-font-weight: normal;";

        applyBoldStylingToDisabledFields(boldStyle);

        // Show the "OK" and "Cancel" buttons
        Button okButton = (Button) createPatientPane.lookupButton(ButtonType.OK);
        okButton.setVisible(true);

        Button cancelButton = (Button) createPatientPane.lookupButton(ButtonType.CANCEL);
        cancelButton.setVisible(true);

        // Hide the "editPatient" button
        editPatient.setVisible(false);
    }


    public void handleButtonAction(ButtonType buttonType) {
        if (buttonType.getButtonData() == ButtonType.OK.getButtonData() ) {
            reloadRequired = handleOkButton();
            System.out.println(reloadRequired);
            if (reloadRequired) {
                createPatientPane.getScene().getWindow().hide();
            }
        } else {
            createPatientPane.getScene().getWindow().hide();
        }

        // For "Cancel" or when reload is not required, just close the alert without affecting the main pane
    }


    public Boolean handleOkButton() {
        Boolean result = false;
        // Kiểm tra xem tất cả các trường đã được nhập
        if (isAllFieldsFilled()) {
            // Lấy giá trị từ các thành phần
            String identificationNumber = identificationNumberView.getText();
            String firstName = firstNameView.getText();
            String lastName = lastNameView.getText();
            String identityTypeLabel = identityTypeView.getValue();
            String healthInsuranceNumber = healthInsuranceNumberView.getText();
            LocalDate birthDateRaw = birthDateView.getValue();
            String address = addressView.getText();
            String phoneNumber = phoneNumberView.getText();
            String email = emailView.getText();
            String identityType = identityTypeView.getConverter().toString(identityTypeLabel);
            String birthDate = birthDateView.getConverter().toString(birthDateRaw);
            // Kiểm tra tính hợp lệ của email
            boolean isEmailValid = isValidEmail(email);
            // Kiểm tra tính hợp lệ của số điện thoại
            boolean isPhoneNumberValid = isValidPhoneNumber(phoneNumber);

            if (isEmailValid && isPhoneNumberValid) {
                // Gather the information from the input fields
                if (editMode) {
                    PatientUpdateDTO newPatient = PatientUpdateDTO.builder()
                        .identityType(IdentityTypeEnum.typeOf(identityType))
                        .identificationNumber(identificationNumber)
                        .firstName(firstName)
                        .lastName(lastName)
                        .healthInsuranceNumber(healthInsuranceNumber)
                        .phoneNumber(phoneNumber)
                        .email(email)
                        .address(address)
                        .birthDate(DateUtils.asDate(birthDateRaw))
                        .build();
                    // API Call
                    try {
                        result = patientAPIService.updatePatient(newPatient,params);
                    } catch (ApiResponseException exception) {
                        exception.printStackTrace();
                        System.out.println(exception.getExceptionResponse());
                    }
                } else {
                    PatientCreateDTO newPatient = PatientCreateDTO.builder()
                        .identityType(IdentityTypeEnum.typeOf(identityType))
                        .identificationNumber(identificationNumber)
                        .firstName(firstName)
                        .lastName(lastName)
                        .healthInsuranceNumber(healthInsuranceNumber)
                        .phoneNumber(phoneNumber)
                        .email(email)
                        .address(address)
                        .birthDate(DateUtils.asDate(birthDateRaw))
                        .build();
                    // API Call
                    try {
                        result = patientAPIService.createPatient(newPatient);
                    } catch (ApiResponseException exception) {
                        exception.printStackTrace();
                        System.out.println(exception.getExceptionResponse());
                    }
                }
            } else {
                // Hiển thị thông báo lỗi cho các trường không hợp lệ
                StringBuilder errorMessages = new StringBuilder();
                if (!isEmailValid) {
                    errorMessages.append("- Email không hợp lệ\n");
                }
                if (!isPhoneNumberValid) {
                    errorMessages.append("- Số điện thoại không hợp lệ\n");
                }
                displayErrorAlert(errorMessages.toString());
                return false;
            }
        } else {
            // Hiển thị thông báo lỗi nếu chưa nhập đủ các trường
            displayErrorAlert("Vui lòng nhập đầy đủ thông tin trước khi tiếp tục.");
            return false;
        }
        return result;
    }


    public void setText(String identificationNumber, String firstName, String lastName, String identityType,
        String healthInsuranceNumber, Date birthDate, String address, String phoneNumber, String email) {
        if (identificationNumber != null) {
            identificationNumberView.setText(identificationNumber);
        }
        if (firstName != null) {
            firstNameView.setText(firstName);
        }
        if (lastName != null) {
            lastNameView.setText(lastName);
        }
        if (identityType != null) {
            identityTypeView.setValue(identityType);
        }
        if (healthInsuranceNumber != null) {
            healthInsuranceNumberView.setText(healthInsuranceNumber);
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
    }

    /**
     * Commit reload list patient request to PatientController
     */
    public Boolean submit() {
       return reloadRequired;
    }

}


