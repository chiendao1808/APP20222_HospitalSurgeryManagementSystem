package com.app20222.app20222_fxapp.app_controllers.user_view;

import com.app20222.app20222_fxapp.enums.users.IdentityTypeEnum;
import com.app20222.app20222_fxapp.services.patient.PatientAPIService;
import com.app20222.app20222_fxapp.services.users.UserAPIService;
import com.app20222.app20222_fxapp.utils.DateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

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
    private TextField DepartmentIdView;

    @FXML
    private ComboBox<String> RoleListIdView;

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
    public void disableAllFields(){
        String boldStyle = "-fx-font-weight: bold;";
        identificationNumberView.setDisable(true);
        identityTypeView.setDisable(true);
        firstNameView.setDisable(true);
        lastNameView.setDisable(true);
        CodeNumberView.setDisable(true);
        birthDateView.setDisable(true);
        addressView.setDisable(true);
        phoneNumberView.setDisable(true);
        emailView.setDisable(true);
        RoleListIdView.setDisable(true);
        DepartmentIdView.setDisable(true);
        applyBoldStylingToDisabledFields(boldStyle);
    }
    // boi dam cac truong khi xem chi tiet
    public void applyBoldStylingToDisabledFields(String boldStyle) {
        identificationNumberView.setStyle(boldStyle);
        identityTypeView.setStyle(boldStyle);
        firstNameView.setStyle(boldStyle);
        lastNameView.setStyle(boldStyle);
        CodeNumberView.setStyle(boldStyle);
        birthDateView.setStyle(boldStyle);
        addressView.setStyle(boldStyle);
        phoneNumberView.setStyle(boldStyle);
        emailView.setStyle(boldStyle);
        RoleListIdView.setStyle(boldStyle);
        DepartmentIdView.setStyle(boldStyle);
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

////    public void initialize(URL location, ResourceBundle resources) {
////        setupIdentityTypes();
////        setBirthDateView();
////        setupButtonEventFilters();
////        patientAPIService = new PatientAPIService();
////        if (createMode) {
////            editPatient.setVisible(false); // Hide the button for create mode
////            Button okButton = (Button) createPatientPane.lookupButton(ButtonType.OK);
////            okButton.setVisible(true);
////            Button cancelButton = (Button) createPatientPane.lookupButton(ButtonType.CANCEL);
////            cancelButton.setVisible(true);
////        } else {
////            editPatient.setVisible(true); // Show the button for view mode
////            Button okButton = (Button) createPatientPane.lookupButton(ButtonType.OK);
////            okButton.setVisible(false);
////            Button cancelButton = (Button) createPatientPane.lookupButton(ButtonType.CANCEL);
////            cancelButton.setVisible(false);
////        }
//
//    }
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
    public void initialize(URL location, ResourceBundle resources) {
        setupIdentityTypes();
        setBirthDateView();
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
}

