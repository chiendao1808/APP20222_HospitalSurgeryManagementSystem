package com.app20222.app20222_fxapp.app_controllers.patient_view;

import com.app20222.app20222_fxapp.dto.requests.patient.PatientCreateDTO;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListDTO;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.enums.users.IdentityTypeEnum;
import com.app20222.app20222_fxapp.utils.DateUtils;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.http.HttpStatusCodes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.net.http.HttpResponse;
import java.time.LocalDate;
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

    private boolean editMode = false;
    private PatientGetListDTO originalPatient;
    // dùng cho loại gấy chưn thực
    private final Map<String, String> identityTypeMap = new HashMap<>();

    public AddPatientController() {
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public void setPatient(PatientGetListDTO patient) {
        this.originalPatient = patient;
    }

    public PatientGetListDTO getOriginalPatient() {
        return originalPatient;
    }

    public void initialize(URL location, ResourceBundle resources) {
        setupIdentityTypes();
        setBirthDateView();
        setupButtonEventFilters();
    }

    public void setupIdentityTypes() {
        identityTypeMap.put(IdentityTypeEnum.CITIZEN_ID_CARD.name(), IdentityTypeEnum.CITIZEN_ID_CARD.getType());
        identityTypeMap.put(IdentityTypeEnum.ID_CARD.name(), IdentityTypeEnum.ID_CARD.getType());
        identityTypeMap.put(IdentityTypeEnum.PASSPORT.name(), IdentityTypeEnum.PASSPORT.getType());
        System.out.println(identityTypeMap);
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
                    return LocalDate.parse(dateString, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    private void setupButtonEventFilters() {
        createPatientPane.getButtonTypes().stream()
            .filter(buttonType -> buttonType.getButtonData() == ButtonType.OK.getButtonData() ||
                buttonType.getButtonData() == ButtonType.CANCEL.getButtonData())
            .forEach(buttonType -> createPatientPane.lookupButton(buttonType).addEventFilter(ActionEvent.ACTION, event ->
                handleButtonAction(buttonType)));
    }

    public boolean isAllFieldsFilled() {
        return !identificationNumberView.getText().isEmpty() &&
            !firstNameView.getText().isEmpty() &&
            !lastNameView.getText().isEmpty() &&
            identityTypeView.getValue() != null &&
            !healthInsuranceNumberView.getText().isEmpty() &&
            birthDateView.getValue() != null &&
            !addressView.getText().isEmpty() &&
            !phoneNumberView.getText().isEmpty() &&
            !emailView.getText().isEmpty();
    }

    public boolean isValidEmail(String email) {
        // Kiểm tra tính hợp lệ của email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra tính hợp lệ của số điện thoại
        String phoneRegex = "^[0-9]{10}$";
        return phoneNumber.matches(phoneRegex);
    }

    public void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Thông tin không hợp lệ");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void handleButtonAction(ButtonType buttonType) {
        if (buttonType.getButtonData() == ButtonType.OK.getButtonData()) {
            handleOkButton();
        } else if (buttonType.getButtonData() == ButtonType.CANCEL.getButtonData()) {
            createPatientPane.getScene().getWindow().hide();
        }
    }

    public PatientGetListDTO handleOkButton() {
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
                // Hiển thị giá trị đã nhập
                System.out.println("Mã số chứng thức: " + identificationNumber);
                System.out.println("Loaị giấy tờ" + identityType);
                System.out.println("Họ: " + lastName);
                System.out.println("Tên: " + firstName);
                System.out.println("Thẻ bảo hiểm y tế: " + healthInsuranceNumber);
                System.out.println("Ngày sinh: " + birthDate);
                System.out.println("Địa chỉ: " + address);
                System.out.println("Số điện thoại: " + phoneNumber);
                System.out.println("Email: " + email);
                // Gather the information from the input fields
                if (editMode) {
                    PatientGetListDTO newPatient = new PatientGetListDTO();
                    createPatientPane.getScene().getWindow().hide();
                    return newPatient;
                } else {
                    String uri = ApiUtils.buildURI(APIDetails.PATIENT_CREATE.getRequestPath() + APIDetails.PATIENT_CREATE.getDetailPath(),
                        new HashMap<>());
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
                    System.out.println("new patient " + newPatient.toString());
                    HttpResponse<String> response = HttpUtils.doRequest(uri, APIDetails.PATIENT_CREATE.getMethod(), newPatient, new HashMap<>());
                    if (Objects.isNull(response)) {
                        return null;
                    }
                    if (Objects.equals(response.statusCode(), HttpStatusCodes.STATUS_CODE_OK)) {
                        System.out.println("response" + response.body());
                        createPatientPane.getScene().getWindow().hide();
                    } else {
                        // Log error from BE
                        System.out.println(HttpUtils.handleResponse(response, new TypeReference<ExceptionResponse>() {}));
                    }
                    return null;
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

            }
        } else {
            // Hiển thị thông báo lỗi nếu chưa nhập đủ các trường
            displayErrorAlert("Vui lòng nhập đầy đủ thông tin trước khi tiếp tục.");
        }
        return null;
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
            birthDateView.setValue(LocalDate.parse((CharSequence) birthDate));
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

    public PatientGetListDTO submit() {
        if (isAllFieldsFilled() && isValidEmail(emailView.getText()) && isValidPhoneNumber(phoneNumberView.getText())) {
            // Call handleOkButton to create a new patient
            return handleOkButton();
        } else {
            // If the information is not valid, return null
            return null;
        }
    }

}


