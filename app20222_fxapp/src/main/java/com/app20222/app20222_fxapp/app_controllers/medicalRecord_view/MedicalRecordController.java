package com.app20222.app20222_fxapp.app_controllers.medicalRecord_view;
import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordDetailsRes;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordGetListDTO;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordListDTO;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
    private TableColumn<MedicalRecordListDTO, String> medicalRecordAction;

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
    // Tìm kiếm
    @FXML
    private TextField medicalRecordSearchCode;
    @FXML
    private DatePicker medicalRecordSearchEndAt;
    @FXML
    private TextField medicalRecordSearchName;
    @FXML
    private DatePicker medicalRecordSearchStartAt;
    @FXML
    private Button medicalRecordSubmitSearch;
    @FXML
    private Button medicalRecordClearSearch;

    private MedicalRecordAPIService medicalRecordAPIService;

    Map<String, String> searchParams = new HashMap<>();

    public MedicalRecordController(TableView<MedicalRecordListDTO> medicalRecordTable,
                                   TableColumn<MedicalRecordGetListDTO, Long> medicalRecordStt,
                                   TableColumn<MedicalRecordGetListDTO, Long> medicalRecordPatientID,
                                   TableColumn<MedicalRecordGetListDTO, String> medicalRecordPatientName,
                                   TableColumn<MedicalRecordGetListDTO, String> medicalRecordPatientCode,
                                   TableColumn<MedicalRecordGetListDTO, Date> medicalRecordCreateAt,
                                   TableColumn<MedicalRecordGetListDTO, Long> medicalRecordCreatedById,
                                   TableColumn<MedicalRecordGetListDTO, String> medicalRecordCreatedByName,
                                   TableColumn<MedicalRecordListDTO, String> medicalRecordAction,
                                   TextField medicalRecordSearchCode,TextField medicalRecordSearchName,
                                    DatePicker medicalRecordSearchStartAt,DatePicker medicalRecordSearchEndAt) {
        this.medicalRecordTable = medicalRecordTable;
        this.medicalRecordStt = medicalRecordStt;
        this.medicalRecordPatientID = medicalRecordPatientID;
        this.medicalRecordPatientName = medicalRecordPatientName;
        this.medicalRecordPatientCode = medicalRecordPatientCode;
        this.medicalRecordCreateAt = medicalRecordCreateAt;
        this.medicalRecordCreatedById = medicalRecordCreatedById;
        this.medicalRecordCreatedByName = medicalRecordCreatedByName;
        this.medicalRecordAction = medicalRecordAction;
        this.medicalRecordSearchCode = medicalRecordSearchCode;
        this.medicalRecordSearchName = medicalRecordSearchName;
        this.medicalRecordSearchStartAt = medicalRecordSearchStartAt;
        this.medicalRecordSearchEndAt = medicalRecordSearchEndAt;
    }
    public void reloadTable() {
        ObservableList<MedicalRecordListDTO> medicalRecordLst = getDataFromDataSource();
        if (medicalRecordTable != null) {
            this.medicalRecordTable.setItems(medicalRecordLst);
            setupEditDeleteButtons();
        }
    }

    // fomat date về string
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public void initializeMedicalRecord() {
        medicalRecordAPIService = new MedicalRecordAPIService();
        initializeTable();
        setDateView();
    }
    public void initializeTable() {
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        ObservableList<MedicalRecordListDTO> medicalRecordList = getDataFromDataSource();
        setupTableColumns();
        setupEditDeleteButtons();

        if (medicalRecordList != null) {
            this.medicalRecordTable.setItems(medicalRecordList);
            medicalRecordCreateAt.setCellFactory(column -> new TableCell<>() {
                @Override
                protected void updateItem(Date date, boolean empty) {
                    super.updateItem(date, empty);
                    if (empty || date == null) {
                        setText(null);
                    } else {
                        setText(formatDate(date));
                    }
                }
            });
        }
    }
    // Tạo list HSBA (api gọi lấy danh sách ở đây)
    private ObservableList<MedicalRecordListDTO> getDataFromDataSource() {
        List<MedicalRecordListDTO> lstMedicalRecord = new ArrayList<>();
        try {
            lstMedicalRecord = medicalRecordAPIService.getListMedicalRecord(searchParams);
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        return FXCollections.observableArrayList(lstMedicalRecord);
    }
    // setup date
    public void setDateView() {
        // Set the date format for the DatePicker to "dd/MM/yyyy"
        medicalRecordSearchStartAt.setConverter(new StringConverter<>() {
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
        medicalRecordSearchEndAt.setConverter(new StringConverter<>() {
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

    // Sét default values
    public void setDefaultDateFormat() {
        LocalDate defaultDate = LocalDate.of(1970, 1, 1);
        medicalRecordSearchStartAt.setValue(defaultDate);
        medicalRecordSearchEndAt.setValue(defaultDate);
    }
    // setUp table
    public void setupTableColumns() {
        // Set up the PropertyValueFactory to map the data fields to the table columns
        this.medicalRecordStt.setCellValueFactory(param -> {
            int index = param.getTableView().getItems().indexOf(param.getValue());
            return new SimpleLongProperty(index + 1).asObject();
        });
        medicalRecordPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        medicalRecordPatientCode.setCellValueFactory(new PropertyValueFactory<>("patientCode"));
        medicalRecordCreateAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        medicalRecordCreatedByName.setCellValueFactory(new PropertyValueFactory<>("createdByName"));

    }

    public void setupEditDeleteButtons() {
        Callback<TableColumn<MedicalRecordListDTO, String>, TableCell<MedicalRecordListDTO, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<MedicalRecordListDTO, String> call(TableColumn<MedicalRecordListDTO, String> param) {
                        final TableCell<MedicalRecordListDTO, String> cell = new TableCell<MedicalRecordListDTO, String>() {
                            private final Button viewButton = new Button();
                            private final Button updateButton = new Button();

                            {
                                FontAwesomeIconView viewIcon = new FontAwesomeIconView(FontAwesomeIcon.EYE);
                                FontAwesomeIconView updateIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                                viewIcon.setStyle(
                                        " -fx-cursor: hand ;"
                                                + "-glyph-size:24px;"
                                                + "-fx-fill:#00E676;"
                                );
                                updateIcon.setStyle(
                                        " -fx-cursor: hand ;"
                                                + "-glyph-size:24px;"
                                                + "-fx-fill:#00E676;"
                                );
                                viewButton.setGraphic(viewIcon);
                                updateButton.setGraphic(updateIcon);
                                viewButton.getStyleClass().add("edit-button");
                                updateButton.getStyleClass().add("edit-button");

                                // Handle detail button action
                                viewButton.setOnAction(event -> {
                                    MedicalRecordListDTO medicalRecord = getTableView().getItems().get(getIndex());
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id", String.valueOf(medicalRecord.getId()));
                                    try {
                                        MedicalRecordDetailsRes medicalRecordDetailsRes = medicalRecordAPIService.getDetailsMedicalRecord(params);
                                        openDetailDialog(medicalRecordDetailsRes);
                                    } catch (ApiResponseException e) {
                                        System.out.println(e.getExceptionResponse());
                                    }
                                });

                                // Handle edit button action
                                updateButton.setOnAction(event -> {
                                    MedicalRecordListDTO medicalRecord = getTableView().getItems().get(getIndex());
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id", String.valueOf(medicalRecord.getId()));
                                    try {
                                        MedicalRecordDetailsRes medicalRecordDetailsRes = medicalRecordAPIService.getDetailsMedicalRecord(params);
                                        openUpdateDialog(medicalRecordDetailsRes, params);
                                    } catch (ApiResponseException e) {
                                        System.out.println(e.getExceptionResponse());
                                    }
                                });
                                setGraphic(new HBox(viewButton, updateButton));

                            }

                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    // Show both buttons in the cell
                                    setGraphic(new HBox(viewButton, updateButton));
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

    public void openUpdateDialog(MedicalRecordDetailsRes medicalRecordDetailsRes, Map<String, String> params) {
        String fxmlPath = "views/medicalRecord_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            AddMedicalRecordController addMedicalRecordController = loader.getController();
            addMedicalRecordController.setUpdateMode(true);
            addMedicalRecordController.disablePatientFields();
            addMedicalRecordController.setMedicalRecordUpdateDTO(medicalRecordDetailsRes,params);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cập nhật hồ sơ bệnh án");
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
    // detail
    public void openDetailDialog(MedicalRecordDetailsRes medicalRecordDetailsRes) {
        String fxmlPath = "views/medicalRecord_view/detail.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            DetailsMedicalRecordController detailsMedicalRecordController = loader.getController();
            detailsMedicalRecordController.setMedicalRecordDetailsRes(medicalRecordDetailsRes);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Chi tiết hồ sơ bệnh án");
            dialogStage.setScene(new Scene(root));
            dialogStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tìm kiếm
    @FXML
    public void onDepartmentSubmitSearch(ActionEvent event) {
        String code = medicalRecordSearchCode.getText();
        String name = medicalRecordSearchName.getText();
        Optional<LocalDate> startDateOptional = Optional.ofNullable(medicalRecordSearchStartAt.getValue());
        String startDate = startDateOptional.map(date -> date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("01/01/1970");
        Optional<LocalDate> endDateOptional = Optional.ofNullable(medicalRecordSearchEndAt.getValue());
        String endDate = endDateOptional.map(date -> date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("01/01/1970");
        if(startDate != null && endDate == null){
            searchParams.put("startDate", startDate);
            searchParams.put("endDate", null );
        }
        else if(endDate != null && startDate == null){
            searchParams.put("startDate", null);
            searchParams.put("endDate", endDate );
        } else {
            searchParams.put("startDate", startDate);
            searchParams.put("endDate", endDate );
        }

        // Sử dụng các giá trị được lấy từ các trường tìm kiếm
        searchParams.put("patientCode", code);
        searchParams.put("patientName", name);
        // gọi lại api sau khi tìm kiếm
        reloadTable();
    }

    public void clearParams(ActionEvent event) {
        resetSearchParams();
        Optional<LocalDate> startDateOptional = Optional.ofNullable(medicalRecordSearchStartAt.getValue());
        String startDate = startDateOptional.map(date -> date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("01/01/1970");
        Optional<LocalDate> endDateOptional = Optional.ofNullable(medicalRecordSearchEndAt.getValue());
        String endDate = endDateOptional.map(date -> date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).orElse("01/01/1970");
        // Handle endDate and set default value if empty
        searchParams.put("startDate", startDate);
        searchParams.put("endDate", endDate);
        searchParams.put("patientCode", "");
        searchParams.put("patientName", "");
        reloadTable();


    }
    // clear params
    public void resetSearchParams() {
        medicalRecordSearchCode.setText("");
        medicalRecordSearchName.setText("");
        medicalRecordSearchStartAt.setValue(null);
        medicalRecordSearchEndAt.setValue(null);
    }


    // Show dialogStage thêm mới bệnh nhân
    @FXML
    public void showModal(ActionEvent event) {
        openCreateDialog();
    }
}
