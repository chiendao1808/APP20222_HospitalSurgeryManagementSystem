package com.app20222.app20222_fxapp.app_controllers.patient_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListNewDTO;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.patient.PatientAPIService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.*;

public class PatientController {

    @FXML
    private TableView<PatientGetListNewDTO> patientTable;
    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientActionColumn;

    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientCodeColumn;

    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientAddressColumn;

    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientBirthdayColumn;

    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientNameColumn;
    @FXML
    private TableColumn<PatientGetListNewDTO, Long> patientIdColumn;

    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientPhoneColumn;

    private PatientAPIService patientAPIService;


    public PatientController() {
    }

    public PatientController(TableView<PatientGetListNewDTO> patientTable,
                             TableColumn<PatientGetListNewDTO, Long> idColumn,
                             TableColumn<PatientGetListNewDTO, String> nameColumn,
                             TableColumn<PatientGetListNewDTO, String> codeColumn,
                             TableColumn<PatientGetListNewDTO, String> birthdayColumn,
                             TableColumn<PatientGetListNewDTO, String> phoneColumn,
        TableColumn<PatientGetListNewDTO, String> addressColumn,
        TableColumn<PatientGetListNewDTO, String> actionColumn) {
        this.patientTable = patientTable;
        this.patientIdColumn = idColumn;
        this.patientNameColumn = nameColumn;
        this.patientCodeColumn = codeColumn;
        this.patientBirthdayColumn = birthdayColumn;
        this.patientPhoneColumn = phoneColumn;
        this.patientAddressColumn = addressColumn;
        this.patientActionColumn = actionColumn;
        this.patientAPIService = new PatientAPIService();
        // Initialize the table with the provided columns
    }

    public TableColumn<PatientGetListNewDTO, Long> getPatientIdColumn() {
        return patientIdColumn;
    }

    public TableColumn<PatientGetListNewDTO, String> getPatientNameColumn() {
        return patientNameColumn;
    }

    public TableColumn<PatientGetListNewDTO, String> getPatientCodeColumn() {
        return patientCodeColumn;
    }

    public TableColumn<PatientGetListNewDTO, String> getPatientBirthdayColumn() {
        return patientBirthdayColumn;
    }

    public TableColumn<PatientGetListNewDTO, String> getPatientPhoneColumn() {
        return patientPhoneColumn;
    }

    public TableColumn<PatientGetListNewDTO, String> getPatientAddressColumn() {
        return patientAddressColumn;
    }

    public TableColumn<PatientGetListNewDTO, String> getPatientActionColumn() {
        return patientActionColumn;
    }

    // Khởi tạo table
    public void initializeTable() {
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        ObservableList<PatientGetListNewDTO> patientList = getDataFromDataSource();
        setupTableColumns();
        setupEditDeleteButtons();

        if (patientTable != null) {
            this.patientTable.setItems(patientList);
        }
    }

    /**
     * Reload data when has a change
     */
    public void reloadTable() {
        ObservableList<PatientGetListNewDTO> patientList = getDataFromDataSource();
        if (patientTable != null) {
            this.patientTable.setItems(patientList);
        }
    }

    // Tạo list patients (api gọi lấy danh sách ở đây)
    private ObservableList<PatientGetListNewDTO> getDataFromDataSource() {
        // Replace this method with your actual logic to fetch data from the data source
        List<PatientGetListNewDTO> lstPatient = new ArrayList<>();
        try {
            lstPatient = patientAPIService.getListPatient(new HashMap<>());
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        return FXCollections.observableArrayList(lstPatient);
    }
    // Thêm các thông tin vào bảng sau khi đã có danh sách bệnh nhân
    public void setupTableColumns() {
        this.patientIdColumn.setCellValueFactory(param -> {
            int index = param.getTableView().getItems().indexOf(param.getValue());
            return new SimpleLongProperty(index + 1).asObject();
        });
        this.patientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.patientCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        this.patientBirthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        this.patientPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        this.patientAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    // Hàm tạo 2 nút edit và delete
    public void setupEditDeleteButtons() {
        Callback<TableColumn<PatientGetListNewDTO, String>, TableCell<PatientGetListNewDTO, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<PatientGetListNewDTO, String> call(TableColumn<PatientGetListNewDTO, String> param) {
                        final TableCell<PatientGetListNewDTO, String> cell = new TableCell<PatientGetListNewDTO, String>() {
                            private final Button viewButton = new Button();

                            {
                                FontAwesomeIconView viewIcon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                                viewIcon.setStyle(
                                        " -fx-cursor: hand ;"
                                                + "-glyph-size:24px;"
                                                + "-fx-fill:#00E676;"
                                );
                                viewButton.setGraphic(viewIcon);
                                viewButton.getStyleClass().add("edit-button");

                                // Handle edit button action
                                viewButton.setOnAction(event -> {
                                    PatientGetListNewDTO patient = getTableView().getItems().get(getIndex());
                                    System.out.println("Patient" + patient);
                                    openEditDialog(patient);

                                });

                                setGraphic(viewButton);
                            }

                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(viewButton);
                                }
                            }
                        };
                        return cell;
                    }
                };

        patientActionColumn.setCellFactory(cellFactory);
    }

    // Hàm thêm bệnh nhân mới va bảng
    public void addNewPatient(PatientGetListNewDTO newPatient) {
        if (newPatient != null) {
            patientTable.getItems().add(newPatient);
        }
    }

    // Hàm cập nhật thông tin bệnh nhân
    public void updatePatientInTable(PatientGetListNewDTO oldPatient, PatientGetListNewDTO updatedPatient) {
        int index = patientTable.getItems().indexOf(oldPatient);
        if (index >= 0) {
            patientTable.getItems().set(index, updatedPatient);
        }
    }


    // Hàm thực hiển mở dialogStage để chỉnh sửa thông tin bệnh nhân
    @FXML
    private void openEditDialog(PatientGetListNewDTO patient) {
        String fxmlPath = "views/patient_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            AddPatientController addPatientController = loader.getController();
            addPatientController.setEditMode(false);
            addPatientController.disableAllFields();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Xem chi tiết bệnh nhân");
            dialogStage.setScene(new Scene(root));

//            addPatientController.setPatient(patient);
//            addPatientController.setText(
//                    patient.getIdentificationNumber(),
//                    patient.getFirstName(),
//                    patient.getLastName(),
//                    "Chứng minh nhân dân",
//                    patient.getHealthInsuranceNumber(),
//                    patient.getBirthDate(),
//                    patient.getAddress(),
//                    patient.getPhoneNumber(),
//                    patient.getEmail()
//            );
            dialogStage.setOnHidden(e -> {
                // Retrieve the updated patient information from the AddPatientController
                Boolean resultSubmit = addPatientController.submit();
                if (Objects.equals(resultSubmit, true)) {
                    // Update the patient in the table
//                    updatePatientInTable(patient, updatedPatient);
                    reloadTable();
                }
            });
            dialogStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handle create dialog
     */
    public void openCreateDialog() {
        String fxmlPath = "views/patient_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            AddPatientController addPatientController = loader.getController();
            addPatientController.setCreateMode(true);
            addPatientController.initialize(null, null);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tạo mới bệnh nhân");
            dialogStage.setScene(new Scene(root));
            dialogStage.setOnHidden(e -> {
                Boolean resultSubmit = addPatientController.submit();
                if (Objects.equals(resultSubmit, true)) {
                    reloadTable();
                }
            });
            dialogStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Show dialogStage thêm mới bệnh nhân
    @FXML
    public void showModal(ActionEvent event) {
        openCreateDialog();
    }

}
