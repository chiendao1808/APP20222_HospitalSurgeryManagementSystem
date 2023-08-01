package com.app20222.app20222_fxapp.app_controllers.UserController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class AddUserController {

    @FXML
    private TextField CodeNumberView;

    @FXML
    private TextField DepartmentIdView;

    @FXML
    private ComboBox<?> RoleListIdView;

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
    private ComboBox<?> identityTypeView;

    @FXML
    private TextField lastNameView;

    @FXML
    private TextField phoneNumberView;

    @FXML
    void handleEditPatient(ActionEvent event) {

    }

}

