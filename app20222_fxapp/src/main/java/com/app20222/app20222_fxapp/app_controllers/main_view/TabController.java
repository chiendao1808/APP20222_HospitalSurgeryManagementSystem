package com.app20222.app20222_fxapp.app_controllers.main_view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class TabController {

    private AnchorPane patient;
    private AnchorPane medicalRecord;
    private AnchorPane doctors;
    private AnchorPane surgery;
    private AnchorPane surgeryRoom;
    private AnchorPane department;
    private AnchorPane userPane;
    private Button tabPatient;
    private Button tabMedicalRecord;
    private Button tabDoctor;
    private Button tabSurgeryRoom;
    private Button tabDepartment;
    private Button tabSurgery;
    private Button tabUser;

    public TabController(
        AnchorPane patient,
        AnchorPane medicalRecord,
        AnchorPane doctors,
        AnchorPane surgery,
        AnchorPane surgeryRoom,
        AnchorPane department,
        AnchorPane userPane,
        Button tabPatient,
        Button tabMedicalRecord,
        Button tabDoctor,
        Button tabSurgeryRoom,
        Button tabDepartment,
        Button tabSurgery,
        Button tabUser) {
        this.patient = patient;
        this.medicalRecord = medicalRecord;
        this.doctors = doctors;
        this.surgery = surgery;
        this.surgeryRoom = surgeryRoom;
        this.department = department;
        this.tabPatient = tabPatient;
        this.tabMedicalRecord = tabMedicalRecord;
        this.tabDoctor = tabDoctor;
        this.tabSurgeryRoom = tabSurgeryRoom;
        this.tabDepartment = tabDepartment;
        this.tabSurgery = tabSurgery;
        this.tabUser = tabUser;
        this.userPane = userPane;
    }

    public TabController() {
    }

    @FXML
    public void switchTab(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        if (selectedButton == tabPatient) {
            switchToTab(tabPatient, patient);
        } else if (selectedButton == tabMedicalRecord) {
            switchToTab(tabMedicalRecord, medicalRecord);
        } else if (selectedButton == tabDoctor) {
            switchToTab(tabDoctor, doctors);
        } else if (selectedButton == tabSurgery) {
            switchToTab(tabSurgery, surgery);
        } else if (selectedButton == tabSurgeryRoom) {
            switchToTab(tabSurgeryRoom, surgeryRoom);
        } else if (selectedButton == tabDepartment) {
            switchToTab(tabDepartment, department);
        } else if (selectedButton == tabUser) {
            switchToTab(tabUser, userPane);
        }
    }

    @FXML
    public void switchToTab(Button selectedButton, AnchorPane selectedPane) {
        patient.setVisible(selectedPane == patient);
        medicalRecord.setVisible(selectedPane == medicalRecord);
        doctors.setVisible(selectedPane == doctors);
        surgery.setVisible(selectedPane == surgery);
        surgeryRoom.setVisible(selectedPane == surgeryRoom);
        department.setVisible(selectedPane == department);
        userPane.setVisible(selectedPane == userPane);
        // Reset the background for all tabs
        tabPatient.setStyle("-fx-background-color: transparent");
        tabMedicalRecord.setStyle("-fx-background-color: transparent");
        tabDoctor.setStyle("-fx-background-color: transparent");
        tabSurgery.setStyle("-fx-background-color: transparent");
        tabSurgeryRoom.setStyle("-fx-background-color: transparent");
        tabDepartment.setStyle("-fx-background-color: transparent");
        tabUser.setStyle("-fx-background-color: transparent");
        if (selectedButton == tabUser) {
            selectedButton.setStyle("-fx-background-color: transparent");
        } else {
            selectedButton.setStyle("-fx-background-color: #2C3D94");

        }
        // Set the background for the selected tab
        selectedButton.requestFocus();
    }

}
