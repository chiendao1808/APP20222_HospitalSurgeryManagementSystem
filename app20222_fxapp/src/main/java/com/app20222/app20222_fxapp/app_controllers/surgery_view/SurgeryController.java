package com.app20222.app20222_fxapp.app_controllers.surgery_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.dto.common.CommonIdCodeName;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryDetailDTO;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryGetListDTO;
import com.app20222.app20222_fxapp.enums.surgery.SurgeryStatusEnum;
import com.app20222.app20222_fxapp.enums.users.IdentityTypeEnum;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.comboBox.ComboBoxAPIService;
import com.app20222.app20222_fxapp.services.surgery.SurgeryAPIService;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SurgeryController {


    @FXML
    private Button createSurgery;
    @FXML
    private TableView<SurgeryGetListDTO> surgeryTable;
    @FXML
    private ScrollPane surgeryTableView;
    // column

    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryActionColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, Long> surgerySttColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryCodeColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryNameColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryPatientNameColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryResultColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryRoomColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryStatusColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> typeSurgeryColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, String> surgeryDiseaseGroupNameColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, Date> surgeryStartedAtColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, Date> surgeryEndedAtColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, Date> surgeryEstimatedEndAtColumn;

    // Tìm kiếm
    @FXML
    private Button surgeryClearSearch;
    @FXML
    private TextField surgerySearchDiseaseGroup;

    @FXML
    private ComboBox<Integer> surgerySearchEstimatedEndAtHour;

    @FXML
    private ComboBox<Integer> surgerySearchEstimatedEndAtMinute;

    @FXML
    private DatePicker surgerySearchEstimatedEndAtYear;

    @FXML
    private TextField surgerySearchName;

    @FXML
    private TextField surgerySearchPatientId;

    @FXML
    private ComboBox<Integer> surgerySearchStartedAtHour;

    @FXML
    private ComboBox<Integer> surgerySearchStartedAtMinute;

    @FXML
    private ComboBox<String> surgerySearchStatus;

    @FXML
    private ComboBox<CommonIdCodeName> surgerySearchSurgeryRoomId;

    @FXML
    private TextField surgerySearchPatientIdSearch;
    @FXML
    private DatePicker surgerySearchStartAtYear;
    @FXML
    private Button surgerySubmitSearch;

    private SurgeryAPIService surgeryAPIService;
    private ComboBoxAPIService comboBoxAPIService;

    // param tìm kiếm
    private Map<String, String> searchParams = new HashMap<>();
    private final Map<String, String> status = new HashMap<>();

    public void initializeSurgery() {
        surgeryAPIService = new SurgeryAPIService();
        comboBoxAPIService = new ComboBoxAPIService();
        initializeTable();
        initSurgerySearch();
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        return sdf.format(date);
    }

    public void reloadTable() {
        ObservableList<SurgeryGetListDTO> surgeryList = getDataFromDataSource();
        if (surgeryTable != null) {
            this.surgeryTable.setItems(surgeryList);
            setupEditDeleteButtons();
        }
    }

    public void initializeTable() {
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        ObservableList<SurgeryGetListDTO> surgeryList = getDataFromDataSource();
        setupTableColumns();
        setupEditDeleteButtons();

        if (surgeryList != null) {
            this.surgeryTable.setItems(surgeryList);
            surgeryStartedAtColumn.setCellFactory(column -> new TableCell<>() {
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

            surgeryEndedAtColumn.setCellFactory(column -> new TableCell<>() {
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

            surgeryEstimatedEndAtColumn.setCellFactory(column -> new TableCell<>() {
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

    public SurgeryController(TableView<SurgeryGetListDTO> surgeryTable, TableColumn<SurgeryGetListDTO, Long> surgerySttColumn,
                             TableColumn<SurgeryGetListDTO, String> surgeryCodeColumn, TableColumn<SurgeryGetListDTO, String>
                                     surgeryNameColumn, TableColumn<SurgeryGetListDTO, String> surgeryPatientNameColumn, TableColumn<SurgeryGetListDTO, String>
                                     surgeryResultColumn, TableColumn<SurgeryGetListDTO, String> surgeryRoomColumn, TableColumn<SurgeryGetListDTO, String>
                                     surgeryStatusColumn, TableColumn<SurgeryGetListDTO, String> typeSurgeryColumn,
                             TableColumn<SurgeryGetListDTO, String> surgeryActionColumn, TableColumn<SurgeryGetListDTO, String> surgeryDiseaseGroupNameColumn,
                             TableColumn<SurgeryGetListDTO, Date> surgeryStartedAtColumn, TableColumn<SurgeryGetListDTO, Date> surgeryEndedAtColumn,
                             TableColumn<SurgeryGetListDTO, Date> surgeryEstimatedEndAtColumn, TextField surgerySearchDiseaseGroup,
                             ComboBox<Integer> surgerySearchEstimatedEndAtHour, ComboBox<Integer> surgerySearchEstimatedEndAtMinute,
                             DatePicker surgerySearchEstimatedEndAtYear, TextField surgerySearchName,
                             TextField surgerySearchPatientId, ComboBox<Integer> surgerySearchStartedAtHour,
                             ComboBox<Integer> surgerySearchStartedAtMinute, ComboBox<String> surgerySearchStatus,
                             ComboBox<CommonIdCodeName> surgerySearchSurgeryRoomId, DatePicker surgerySearchStartAtYear,
                             TextField surgerySearchPatientIdSearch
    ) {
        this.surgeryTable = surgeryTable;
        this.surgerySttColumn = surgerySttColumn;
        this.surgeryCodeColumn = surgeryCodeColumn;
        this.surgeryNameColumn = surgeryNameColumn;
        this.surgeryPatientNameColumn = surgeryPatientNameColumn;
        this.surgeryResultColumn = surgeryResultColumn;
        this.surgeryRoomColumn = surgeryRoomColumn;
        this.surgeryStatusColumn = surgeryStatusColumn;
        this.typeSurgeryColumn = typeSurgeryColumn;
        this.surgeryActionColumn = surgeryActionColumn;
        this.surgeryDiseaseGroupNameColumn = surgeryDiseaseGroupNameColumn;
        this.surgeryStartedAtColumn = surgeryStartedAtColumn;
        this.surgeryEndedAtColumn = surgeryEndedAtColumn;
        this.surgeryEstimatedEndAtColumn = surgeryEstimatedEndAtColumn;
        // tìm kiếm
        this.surgerySearchDiseaseGroup = surgerySearchDiseaseGroup;
        this.surgerySearchEstimatedEndAtHour = surgerySearchEstimatedEndAtHour;
        this.surgerySearchEstimatedEndAtMinute = surgerySearchEstimatedEndAtMinute;
        this.surgerySearchEstimatedEndAtYear =surgerySearchEstimatedEndAtYear;
        this.surgerySearchName = surgerySearchName;
        this.surgerySearchPatientId = surgerySearchPatientId;
        this.surgerySearchStartedAtHour = surgerySearchStartedAtHour;
        this.surgerySearchStartedAtMinute = surgerySearchStartedAtMinute;
        this.surgerySearchStatus = surgerySearchStatus;
        this.surgerySearchSurgeryRoomId = surgerySearchSurgeryRoomId;
        this.surgerySearchStartAtYear = surgerySearchStartAtYear;
        this.surgerySearchPatientIdSearch = surgerySearchPatientIdSearch;

}

    // Tạo list surgery (api gọi lấy danh sách ở đây)
    private ObservableList<SurgeryGetListDTO> getDataFromDataSource() {
        List<SurgeryGetListDTO> lstSurgery = new ArrayList<>();
        try {
            lstSurgery = surgeryAPIService.getListSurgery(searchParams);
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        return FXCollections.observableArrayList(lstSurgery);
    }
    // Thêm các thông tin vào bảng sau khi đã có danh sách ca phau thuat
    public void setupTableColumns() {
        // Set up the PropertyValueFactory to map the data fields to the table columns
        this.surgerySttColumn.setCellValueFactory(param -> {
            int index = param.getTableView().getItems().indexOf(param.getValue());
            return new SimpleLongProperty(index + 1).asObject();
        });
        surgeryCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        surgeryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surgeryDiseaseGroupNameColumn.setCellValueFactory(new PropertyValueFactory<>("diseaseGroupName"));
        surgeryPatientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        surgeryStartedAtColumn.setCellValueFactory(new PropertyValueFactory<>("startedAt"));
        surgeryEstimatedEndAtColumn.setCellValueFactory(new PropertyValueFactory<>("estimatedEndAt"));
        surgeryEndedAtColumn.setCellValueFactory(new PropertyValueFactory<>("endedAt"));
        surgeryResultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        surgeryRoomColumn.setCellValueFactory(new PropertyValueFactory<>("surgeryRoom"));
        surgeryStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        typeSurgeryColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

    }

    public void setupEditDeleteButtons() {
        Callback<TableColumn<SurgeryGetListDTO, String>, TableCell<SurgeryGetListDTO, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<SurgeryGetListDTO, String> call(TableColumn<SurgeryGetListDTO, String> param) {
                        final TableCell<SurgeryGetListDTO, String> cell = new TableCell<SurgeryGetListDTO, String>() {
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
                                    SurgeryGetListDTO surgery = getTableView().getItems().get(getIndex());
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id", String.valueOf(surgery.getId()));
                                    try {
                                        SurgeryDetailDTO surgeryDetailDTO = surgeryAPIService.getDetailsSurgery(params);
                                        openDetailDialog(surgeryDetailDTO);
                                    } catch (ApiResponseException e) {
                                        System.out.println(e.getExceptionResponse());
                                    }
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

        surgeryActionColumn.setCellFactory(cellFactory);
    }

    // detail
    public void openDetailDialog(SurgeryDetailDTO surgeryDetailDTO) {
        String fxmlPath = "views/surgery_view/detail.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            DetailSurgeryController detailSurgeryController = loader.getController();
            detailSurgeryController.setSurgeryDetail(surgeryDetailDTO);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Chi tiết ca phẫu thuật");
            dialogStage.setScene(new Scene(root));
            dialogStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openCreateDialog() {
        String fxmlPath = "views/surgery_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            AddSurgeryController addSurgeryController = loader.getController();
            addSurgeryController.setSurgeryController(this);
            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initStyle(StageStyle.UTILITY);
            dialogStage.setTitle("Tạo mới ca phẫu thuật");
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);
            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void showModal(ActionEvent event) {
        openCreateDialog();
    }

    // Tìm kiếm
    // khởi tạo combobox bệnh nhân
    public void initSurgerySearch(){
        setUpSurgeryRoom();
        setupStatus();
        setUpDate();
    }


    // Khởi tạo combobox phong phau thuat
    public void setUpSurgeryRoom(){
        ObservableList<CommonIdCodeName> listSurgeryRoom = getDataFromDataSurgeryRoom();
        ObservableList<CommonIdCodeName> observableList = FXCollections.observableArrayList(listSurgeryRoom);
        surgerySearchSurgeryRoomId.setConverter(new StringConverter<CommonIdCodeName>() {
            @Override
            public String toString(CommonIdCodeName item) {
                if (item != null) {
                    return item.getName() ;
                } else {
                    return "";
                }
            }
            @Override
            public CommonIdCodeName fromString(String string) {
                // Chuyển đổi ngược từ chuỗi (nếu cần)
                return null;
            }
        });

        surgerySearchSurgeryRoomId.setItems(observableList);
    }

    // Khỏi tạo status
    public void setupStatus(){
        status.put(SurgeryStatusEnum.ALL.name(), SurgeryStatusEnum.ALL.getStatus());
        status.put(SurgeryStatusEnum.REMAINING.name(), SurgeryStatusEnum.REMAINING.getStatus());
        status.put(SurgeryStatusEnum.OPERATING.name(), SurgeryStatusEnum.OPERATING.getStatus());
        status.put(SurgeryStatusEnum.COMPLETED.name(), SurgeryStatusEnum.COMPLETED.getStatus());
        status.put(SurgeryStatusEnum.CANCELED.name(), SurgeryStatusEnum.CANCELED.getStatus());

        // Create an ObservableList to hold the labels for the identity types
        ObservableList<String> statusLabel = FXCollections.observableArrayList(status.values());

        // Set the items in the ComboBox to display the identity type labels
        surgerySearchStatus.setItems(statusLabel);

        // Set a StringConverter to map the selected identityType to its corresponding value
        surgerySearchStatus.setConverter(new StringConverter<>() {
            @Override
            public String toString(String label) {
                return label; // Display the label in the ComboBox
            }

            @Override
            public String fromString(String string) {
                // Convert the label back to its corresponding value (name of the enum)
                for (Map.Entry<String, String> entry : status.entrySet()) {
                    if (entry.getValue().equals(string)) {
                        return entry.getKey();
                    }
                }
                return "";
            }
        });
    }
    // setUpDate
    public void setUpDate(){
        setUpHourComboBox();
        setUpMinuteComboBox();
        setYear();
    }
    public void setUpHourComboBox() {
        List<Integer> hours = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            hours.add(hour);
        }
        ObservableList<Integer> observableHours = FXCollections.observableArrayList(hours);
        surgerySearchStartedAtHour.setItems(observableHours);
        surgerySearchEstimatedEndAtHour.setItems(observableHours);

    }

    public void setUpMinuteComboBox() {
        List<Integer> minutes = new ArrayList<>();
        for (int minute = 0; minute < 60; minute++) {
            minutes.add(minute);
        }
        ObservableList<Integer> observableMinutes = FXCollections.observableArrayList(minutes);
        surgerySearchStartedAtMinute.setItems(observableMinutes);
        surgerySearchEstimatedEndAtMinute.setItems(observableMinutes);
    }
    public void setYear() {
        // Set the date format for the DatePicker to "dd/MM/yyyy"
        surgerySearchStartAtYear.setConverter(new StringConverter<>() {
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
        surgerySearchEstimatedEndAtYear.setConverter(new StringConverter<>() {
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

    // Lấy danh sách phòng
    private ObservableList<CommonIdCodeName> getDataFromDataSurgeryRoom() {
        List<CommonIdCodeName> lstSurgeryRoom = new ArrayList<>();
        try {
            lstSurgeryRoom = comboBoxAPIService.getComboBoxSurgeryRooms(new HashMap<>());
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        return FXCollections.observableArrayList(lstSurgeryRoom);
    }


    @FXML
    public void onSurgerySubmitSearch(ActionEvent event) {
        String diseaseGroupName = surgerySearchDiseaseGroup.getText();
        String surgeryStatusLabel = surgerySearchStatus.getValue();
        String surgeryStatus = surgerySearchStatus.getConverter().fromString(surgeryStatusLabel);
        String surgeryName = surgerySearchName.getText();
        String selectedPatient = surgerySearchPatientId.getText();
        CommonIdCodeName selectedSurgeryRoom = surgerySearchSurgeryRoomId.getValue();
        // Date
        // Get selected surgery start date, hour, and minute
        String selectedStartDate = surgerySearchStartAtYear.getConverter().toString(surgerySearchStartAtYear.getValue());
        Integer selectedStartHour = surgerySearchStartedAtHour.getSelectionModel().getSelectedItem();
        Integer selectedStartMinute = surgerySearchStartedAtMinute.getSelectionModel().getSelectedItem();
        String startedAt = null;

        // Get selected estimated end date, hour, and minute
        String selectedEstimatedEndDate = surgerySearchEstimatedEndAtYear.getConverter().toString(surgerySearchEstimatedEndAtYear.getValue());
        Integer selectedEstimatedEndHour = surgerySearchEstimatedEndAtHour.getSelectionModel().getSelectedItem();
        Integer selectedEstimatedEndMinute = surgerySearchEstimatedEndAtMinute.getSelectionModel().getSelectedItem();
        String estimatedEndAt = null;
        if(diseaseGroupName != null ){
            searchParams.put("diseaseGroupName", diseaseGroupName);

        }
        if(surgeryStatus != null){
            searchParams.put("status", surgeryStatus);
        }

        searchParams.put("surgeryName", surgeryName);
        if (selectedPatient != null) {
            searchParams.put("patientName", selectedPatient);
        }
        if (selectedSurgeryRoom != null) {
            searchParams.put("surgeryRoomId", String.valueOf(selectedSurgeryRoom.getId()));
        }

        // Format the selected surgery start date and time
        if (selectedStartDate != null && selectedStartHour != null && selectedStartMinute != null) {
            startedAt = String.format("%s %02d:%02d", selectedStartDate, selectedStartHour, selectedStartMinute);
        }
        // Format the selected estimated end date and time
        if (selectedEstimatedEndDate != null && selectedEstimatedEndHour != null && selectedEstimatedEndMinute != null) {
            estimatedEndAt = String.format("%s %02d:%02d", selectedEstimatedEndDate, selectedEstimatedEndHour, selectedEstimatedEndMinute);
        }
        if(startedAt != null){
            searchParams.put("startedAt", startedAt);
            searchParams.put("estimatedEndAt", null);
        }
        else if(estimatedEndAt != null){
            searchParams.put("startedAt", null);
            searchParams.put("estimatedEndAt", estimatedEndAt);
        } else {
            searchParams.put("startedAt", startedAt);
            searchParams.put("estimatedEndAt", estimatedEndAt);
        }
        reloadTable();
    }

    public void clearParams(ActionEvent event) {
        resetSearchParams();
        searchParams.clear(); // Clear the search parameters
        reloadTable();
    }

    public void resetSearchParams() {
        // Clear the search fields
        surgerySearchDiseaseGroup.setText("");
        surgerySearchEstimatedEndAtHour.setValue(null);
        surgerySearchEstimatedEndAtMinute.setValue(null);
        surgerySearchEstimatedEndAtYear.setValue(null);
        surgerySearchName.setText("");
        surgerySearchPatientId.setText("");
        surgerySearchStartedAtHour.setValue(null);
        surgerySearchStartedAtMinute.setValue(null);
        surgerySearchStatus.setValue(null);
        surgerySearchSurgeryRoomId.setValue(null);
        surgerySearchStartAtYear.setValue(null);

    }
}
