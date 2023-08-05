package com.app20222.app20222_fxapp.app_controllers.medicalRecord_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.app_controllers.patient_view.AddPatientController;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordGetListDTO;

import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordListDTO;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListNewDTO;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.medicalRecord.MedicalRecordAPIService;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.*;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MedicalRecordController {

    // Hồ sơ bệnh án
    @FXML
    private TableView<MedicalRecordListDTO> medicalRecordTable;
    @FXML
    private TableColumn<MedicalRecordGetListDTO, Long> medicalRecordStt;
    @FXML
    private TableColumn<MedicalRecordGetListDTO, String> medicalRecordAction;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, Date> medicalRecordCreateAt;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, Long> medicalRecordCreatedById;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, String> medicalRecordCreatedByName;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, String> medicalRecordPatientCode;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, Long> medicalRecordPatientID;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, String> medicalRecordPatientName;

    @FXML
    private Button createMedicalRecord;
    private MedicalRecordAPIService medicalRecordAPIService;

    public MedicalRecordController(TableView<MedicalRecordListDTO> medicalRecordTable,
                                   TableColumn<MedicalRecordGetListDTO, Long> medicalRecordStt,
                                   TableColumn<MedicalRecordGetListDTO, Long> medicalRecordPatientID,
                                   TableColumn<MedicalRecordGetListDTO, String> medicalRecordPatientName,
                                   TableColumn<MedicalRecordGetListDTO, String> medicalRecordPatientCode,
                                   TableColumn<MedicalRecordGetListDTO, Date> medicalRecordCreateAt,
                                   TableColumn<MedicalRecordGetListDTO, Long> medicalRecordCreatedById,
                                   TableColumn<MedicalRecordGetListDTO, String> medicalRecordCreatedByName,
                                   TableColumn<MedicalRecordGetListDTO, String> medicalRecordAction) {
        this.medicalRecordTable = medicalRecordTable;
        this.medicalRecordStt = medicalRecordStt;
        this.medicalRecordPatientID = medicalRecordPatientID;
        this.medicalRecordPatientName = medicalRecordPatientName;
        this.medicalRecordPatientCode = medicalRecordPatientCode;
        this.medicalRecordCreateAt = medicalRecordCreateAt;
        this.medicalRecordCreatedById = medicalRecordCreatedById;
        this.medicalRecordCreatedByName = medicalRecordCreatedByName;
        this.medicalRecordAction = medicalRecordAction;
    }
    public void reloadTable() {
        ObservableList<MedicalRecordListDTO> medicalRecordLst = getDataFromDataSource();
        if (medicalRecordTable != null) {
            this.medicalRecordTable.setItems(medicalRecordLst);
            setupEditDeleteButtons();
        }
    }

    public void initializeMedicalRecord() {
        medicalRecordAPIService = new MedicalRecordAPIService();
        initializeTable();
    }
    public void initializeTable() {
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        ObservableList<MedicalRecordListDTO> medicalRecordList = getDataFromDataSource();
        System.out.println("medicalRecordList" + medicalRecordList);
        setupTableColumns();
        setupEditDeleteButtons();

        if (medicalRecordList != null) {
            this.medicalRecordTable.setItems(medicalRecordList);

        }
    }
    // Tạo list HSBA (api gọi lấy danh sách ở đây)
    private ObservableList<MedicalRecordListDTO> getDataFromDataSource() {
        List<MedicalRecordListDTO> lstMedicalRecord = new ArrayList<>();
        try {
            lstMedicalRecord = medicalRecordAPIService.getListMedicalRecord(new HashMap<>());
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        return FXCollections.observableArrayList(lstMedicalRecord);
    }

    public void setupTableColumns() {
        // Set up the PropertyValueFactory to map the data fields to the table columns
        this.medicalRecordStt.setCellValueFactory(param -> {
            int index = param.getTableView().getItems().indexOf(param.getValue());
            return new SimpleLongProperty(index + 1).asObject();
        });
        medicalRecordPatientID.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        medicalRecordPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        medicalRecordPatientCode.setCellValueFactory(new PropertyValueFactory<>("patientCode"));
        medicalRecordCreateAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        medicalRecordCreatedById.setCellValueFactory(new PropertyValueFactory<>("createdById"));
        medicalRecordCreatedByName.setCellValueFactory(new PropertyValueFactory<>("createdByName"));

    }

    public void setupEditDeleteButtons() {
        Callback<TableColumn<MedicalRecordGetListDTO, String>, TableCell<MedicalRecordGetListDTO, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<MedicalRecordGetListDTO, String> call(TableColumn<MedicalRecordGetListDTO, String> param) {
                        final TableCell<MedicalRecordGetListDTO, String> cell = new TableCell<MedicalRecordGetListDTO, String>() {
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
                                    MedicalRecordGetListDTO medicalRecord = getTableView().getItems().get(getIndex());
//                                    System.out.println("Patient" + patient);
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id", String.valueOf(medicalRecord.getId()));
//                                    try {
//                                        PatientDetailsDTO detailsPatientDTO = patientAPIService.getDetailsPatient(params);
//                                        System.out.println("detailsPatientDTO" + detailsPatientDTO);
//                                        openEditDialog(detailsPatientDTO,params);
//                                    } catch (ApiResponseException e) {
//                                        System.out.println(e.getExceptionResponse());
//                                    }
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

        medicalRecordAction.setCellFactory(cellFactory);
    }
    /**
     * Handle create dialog
     */
    public void openCreateDialog() {
        String fxmlPath = "views/medicalRecord_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            AddMedicalRecordController addMedicalRecordController = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tạo mới hồ sơ bệnh án");
            dialogStage.setScene(new Scene(root));
            dialogStage.setOnHidden(e -> {
                Boolean resultSubmit = addMedicalRecordController.submit();
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
