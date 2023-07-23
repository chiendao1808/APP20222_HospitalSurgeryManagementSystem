package com.app20222.app20222_fxapp.app_controllers.patient_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.app_controllers.ShowScreen;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListDTO;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class PatientController {

    @FXML
    private TableView<PatientGetListDTO> patientTable;
    @FXML
    private TableColumn<PatientGetListDTO, String> patientActionColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientCodeColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientEmailColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientHealthInsuranceNumberColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientBirthdayColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientNameColumn;
    @FXML
    private TableColumn<PatientGetListDTO, String> patientFirstNameColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientLastNameColumn;
    @FXML
    private TableColumn<PatientGetListDTO, Long> patientIdColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientPhoneColumn;


    public PatientController() {
    }

    public PatientController(TableView<PatientGetListDTO> patientTable,
        TableColumn<PatientGetListDTO, String> codeColumn,
        TableColumn<PatientGetListDTO, String> emailColumn,
        TableColumn<PatientGetListDTO, String> healthInsuranceNumberColumn,
        TableColumn<PatientGetListDTO, String> birthdayColumn,
        TableColumn<PatientGetListDTO, String> nameColumn,
        TableColumn<PatientGetListDTO, String> firstNameColumn,
        TableColumn<PatientGetListDTO, String> lastNameColumn,
        TableColumn<PatientGetListDTO, Long> idColumn,
        TableColumn<PatientGetListDTO, String> phoneColumn,
        TableColumn<PatientGetListDTO, String> patientActionColumn) {
        this.patientTable = patientTable;
        this.patientCodeColumn = codeColumn;
        this.patientEmailColumn = emailColumn;
        this.patientHealthInsuranceNumberColumn = healthInsuranceNumberColumn;
        this.patientBirthdayColumn = birthdayColumn;
        this.patientNameColumn = nameColumn;
        this.patientFirstNameColumn = firstNameColumn;
        this.patientLastNameColumn = lastNameColumn;
        this.patientIdColumn = idColumn;
        this.patientPhoneColumn = phoneColumn;
        this.patientActionColumn = patientActionColumn;
        // Initialize the table with the provided columns
    }

    public TableColumn<PatientGetListDTO, Long> getPatientIdColumn() {
        return patientIdColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientActionColumn() {
        return patientActionColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientEmailColumn() {
        return patientEmailColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientCodeColumn() {
        return patientCodeColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientHealthInsuranceNumberColumn() {
        return patientHealthInsuranceNumberColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientBirthdayColumn() {
        return patientBirthdayColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientNameColumn() {
        return patientNameColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientPhoneColumn() {
        return patientPhoneColumn;
    }

    // Khởi tạo table
    public void initializeTable() {
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        ObservableList<PatientGetListDTO> patientList = getDataFromDataSource();

        setupTableColumns();
        setupEditDeleteButtons();

        if (patientTable != null) {
            this.patientTable.setItems(patientList);
        }
    }

    // Tạo list patients (api gọi lấy danh sách ở đây)
    private ObservableList<PatientGetListDTO> getDataFromDataSource() {
        // Replace this method with your actual logic to fetch data from the data source
        List<PatientGetListDTO> patients = new ArrayList<>();
        patients.add(new PatientGetListDTO(1L, "1234567890", "2345544455", "John", "Doe", LocalDate.now(), "Address 1", "1234567890", "john.doe@example.com"));
        patients.add(new PatientGetListDTO(2L, "0987654321", "6644333222", "Jane", "Smith", LocalDate.now(), "Address 2", "0987654321", "jane.smith@example.com"));
        return FXCollections.observableArrayList(patients);
    }
    // Thêm các thông tin vào bảng sau khi đã có danh sách bệnh nhân
    public void setupTableColumns() {
        this.patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        this.patientCodeColumn.setCellValueFactory(new PropertyValueFactory<>("identificationNumber"));
        this.patientFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.patientLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.patientBirthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        this.patientHealthInsuranceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("healthInsuranceNumber"));
        this.patientEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.patientPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    }

    // Hàm tạo 2 nút edit và delete
    public void setupEditDeleteButtons() {
        Callback<TableColumn<PatientGetListDTO, String>, TableCell<PatientGetListDTO, String>> cellFactory =
                new Callback<TableColumn<PatientGetListDTO, String>, TableCell<PatientGetListDTO, String>>() {
                    @Override
                    public TableCell<PatientGetListDTO, String> call(TableColumn<PatientGetListDTO, String> param) {
                        final TableCell<PatientGetListDTO, String> cell = new TableCell<PatientGetListDTO, String>() {
                            private final Button editButton = new Button();
                            private final Button deleteButton = new Button();
                            {
                                FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
                                FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                                deleteIcon.setStyle(
                                        " -fx-cursor: hand ;"
                                                + "-glyph-size:24px;"
                                                + "-fx-fill:#ff1744;"
                                );
                                editIcon.setStyle(
                                        " -fx-cursor: hand ;"
                                                + "-glyph-size:24px;"
                                                + "-fx-fill:#00E676;"
                                );
                                HBox buttonContainer = new HBox(10); // Set the spacing between icons (10 pixels in this example)
                                buttonContainer.getChildren().addAll(editButton, deleteButton);
                                editButton.setGraphic(editIcon);
                                deleteButton.setGraphic(deleteIcon);
                                editButton.getStyleClass().add("edit-button");
                                deleteButton.getStyleClass().add("delete-button");

                                // Handle edit button action (Xử lý khi click nút chỉnh sửa)
                                editButton.setOnAction(event -> {
                                    PatientGetListDTO patient = getTableView().getItems().get(getIndex());
                                    openEditDialog(patient);

                                });

                                // Handle delete button action ( Xử lý khi click nút xoá)
                                deleteButton.setOnAction(event -> {
                                    PatientGetListDTO patient = getTableView().getItems().get(getIndex());
                                    // Add your delete button action logic here
                                    if (patient != null) {
                                        // Show a confirmation dialog before deleting the item
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setTitle("Xác nhận xoá bệnh nhân");
                                        alert.setHeaderText("Bạn có chắc chắn muốn xoá bệnh nhân");
                                        alert.setContentText("Lựa chọn");

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if (result.isPresent() && result.get() == ButtonType.OK) {
                                            // User clicked OK, perform the deletion
                                            getTableView().getItems().remove(patient);
                                        }
                                    }
                                });

                                setGraphic(buttonContainer);
                            }

                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    // Add buttons to the cell
                                    HBox buttonContainer = new HBox(10); // Set the spacing between buttons (10 pixels in this example)
                                    buttonContainer.getChildren().addAll(editButton, deleteButton);
                                    setGraphic(buttonContainer);
                                }
                            }
                        };
                        return cell;
                    }
                };

        patientActionColumn.setCellFactory(cellFactory);
    }

    // Hàm thêm bệnh nhân mới va bảng
    public void addNewPatient(PatientGetListDTO newPatient) {
        if (newPatient != null) {
            patientTable.getItems().add(newPatient);
        }
    }

    // Hàm cập nhật thông tin bệnh nhân
    public void updatePatientInTable(PatientGetListDTO oldPatient, PatientGetListDTO updatedPatient) {
        int index = patientTable.getItems().indexOf(oldPatient);
        if (index >= 0) {
            patientTable.getItems().set(index, updatedPatient);
        }
    }


    // Hàm thực hiển mở dialogStage để chỉnh sửa thông tin bệnh nhân
    private void openEditDialog(PatientGetListDTO patient) {
        String FXMLPATH = "views/patient_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(FXMLPATH));
            Parent root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Chỉnh sửa bệnh nhân");
            dialogStage.setScene(new Scene(root));
            AddPatientController addPatientController = loader.getController();
            addPatientController.setEditMode(true);
            addPatientController.setPatient(patient);
            addPatientController.setText(
                    patient.getIdentificationNumber(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    "Chứng minh nhân dân",
                    patient.getHealthInsuranceNumber(),
                    patient.getBirthDate(),
                    patient.getAddress(),
                    patient.getPhoneNumber(),
                    patient.getEmail()
            );
            dialogStage.setOnHidden(e -> {
                // Retrieve the updated patient information from the AddPatientController
                PatientGetListDTO updatedPatient = addPatientController.submit();
                if (updatedPatient != null) {
                    // Update the patient in the table
                    updatePatientInTable(patient, updatedPatient);
                }
            });
            dialogStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Show dialogStage thêm mới bệnh nhn
    @FXML
    public void showModal(ActionEvent event) {
        String FXMLPATH = "views/patient_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(FXMLPATH));
            Parent root = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tạo mới bệnh nhân");
            dialogStage.setScene(new Scene(root));
            AddPatientController addPatientController = loader.getController();
            dialogStage.setOnHidden(e -> {
                // Retrieve the newly added patient information from the AddPatientController
                PatientGetListDTO newPatient = addPatientController.submit();
                if (newPatient != null) {
                    // Add the new patient to the table
                    this.addNewPatient(newPatient);
                }
            });
            dialogStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
