package com.app20222.app20222_fxapp.app_controllers.dashboard_view;

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
import javafx.scene.chart.PieChart;
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

public class DashBoardController {
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
    private TableColumn<SurgeryGetListDTO, String> surgeryDiseaseGroupNameColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, Date> surgeryStartedAtColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, Date> surgeryEndedAtColumn;
    @FXML
    private TableColumn<SurgeryGetListDTO, Date> surgeryEstimatedEndAtColumn;


    @FXML
    private DatePicker surgerySearchEstimatedEndAtYear;

    @FXML
    private DatePicker surgerySearchStartAtYear;

    // Tổng số ca
    @FXML
    Label totalSurgery;

    //chart
    @FXML
    private PieChart PieChartMonth;

    @FXML
    private PieChart PieChartQuarter;

    @FXML
    private PieChart PieChatYear;

    private SurgeryAPIService surgeryAPIService;

    // param tìm kiếm
    private Map<String, String> searchParams = new HashMap<>();

    public void initializeSurgery() {
        surgeryAPIService = new SurgeryAPIService();
        initializeTable();
        initSurgerySearch();
        initializePieCharts();
        // Fake tạm tổng só ca
        totalSurgery.setText("100");
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

    public DashBoardController(TableView<SurgeryGetListDTO> surgeryTable, TableColumn<SurgeryGetListDTO, Long> surgerySttColumn,
                             TableColumn<SurgeryGetListDTO, String> surgeryCodeColumn, TableColumn<SurgeryGetListDTO, String>
                                     surgeryNameColumn, TableColumn<SurgeryGetListDTO, String> surgeryPatientNameColumn, TableColumn<SurgeryGetListDTO, String>
                                     surgeryResultColumn, TableColumn<SurgeryGetListDTO, String> surgeryRoomColumn, TableColumn<SurgeryGetListDTO, String>
                                     surgeryStatusColumn, TableColumn<SurgeryGetListDTO, String> typeSurgeryColumn,
                             TableColumn<SurgeryGetListDTO, String> surgeryActionColumn, TableColumn<SurgeryGetListDTO, String> surgeryDiseaseGroupNameColumn,
                             TableColumn<SurgeryGetListDTO, Date> surgeryStartedAtColumn, TableColumn<SurgeryGetListDTO, Date> surgeryEndedAtColumn,
                             TableColumn<SurgeryGetListDTO, Date> surgeryEstimatedEndAtColumn,
                             DatePicker surgerySearchEstimatedEndAtYear,DatePicker surgerySearchStartAtYear,
                               PieChart PieChartMonth, PieChart PieChartQuarter, PieChart PieChatYear,Label totalSurgery
    ) {
        this.surgeryTable = surgeryTable;
        this.surgerySttColumn = surgerySttColumn;
        this.surgeryNameColumn = surgeryNameColumn;
        this.surgeryPatientNameColumn = surgeryPatientNameColumn;
        this.surgeryResultColumn = surgeryResultColumn;
        this.surgeryRoomColumn = surgeryRoomColumn;
        this.surgeryStatusColumn = surgeryStatusColumn;
        this.surgeryActionColumn = surgeryActionColumn;
        this.surgeryDiseaseGroupNameColumn = surgeryDiseaseGroupNameColumn;
        this.surgeryStartedAtColumn = surgeryStartedAtColumn;
        this.surgeryEndedAtColumn = surgeryEndedAtColumn;
        this.surgeryEstimatedEndAtColumn = surgeryEstimatedEndAtColumn;
        // tìm kiếm
        this.surgerySearchEstimatedEndAtYear =surgerySearchEstimatedEndAtYear;
        this.surgerySearchStartAtYear = surgerySearchStartAtYear;
        this.totalSurgery = totalSurgery;
        this.PieChartMonth = PieChartMonth;
        this.PieChartQuarter = PieChartQuarter;
        this.PieChatYear = PieChatYear;

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
        surgeryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surgeryDiseaseGroupNameColumn.setCellValueFactory(new PropertyValueFactory<>("diseaseGroupName"));
        surgeryPatientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        surgeryStartedAtColumn.setCellValueFactory(new PropertyValueFactory<>("startedAt"));
        surgeryEstimatedEndAtColumn.setCellValueFactory(new PropertyValueFactory<>("estimatedEndAt"));
        surgeryEndedAtColumn.setCellValueFactory(new PropertyValueFactory<>("endedAt"));
        surgeryResultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
        surgeryRoomColumn.setCellValueFactory(new PropertyValueFactory<>("surgeryRoom"));
        surgeryStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    public void setupEditDeleteButtons() {
        Callback<TableColumn<SurgeryGetListDTO, String>, TableCell<SurgeryGetListDTO, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<SurgeryGetListDTO, String> call(TableColumn<SurgeryGetListDTO, String> param) {
                        final TableCell<SurgeryGetListDTO, String> cell = new TableCell<SurgeryGetListDTO, String>() {
                            private final Button viewButton = new Button();
                            {
                                FontAwesomeIconView viewIcon = new FontAwesomeIconView(FontAwesomeIcon.UPLOAD);
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
                                        System.out.println(surgeryDetailDTO);
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

    // Tìm kiếm
    // khởi tạo combobox bệnh nhân
    public void initSurgerySearch(){
        setUpDate();
    }

    // setUpDate
    public void setUpDate(){
        setYear();
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



    @FXML
    public void onDashBoardSubmitSearch(ActionEvent event) {
        String selectedStartDate = surgerySearchStartAtYear.getConverter().toString(surgerySearchStartAtYear.getValue());
        String startedAt = null;
        // Get selected estimated end date, hour, and minute
        String selectedEstimatedEndDate = surgerySearchEstimatedEndAtYear.getConverter().toString(surgerySearchEstimatedEndAtYear.getValue());
        String estimatedEndAt = null;

        // Format the selected surgery start date and time
        if (selectedStartDate != null ) {
            startedAt = String.format("%s %02d:%02d", selectedStartDate,0,00);
        }
        // Format the selected estimated end date and time
        if (selectedEstimatedEndDate != null  ) {
            estimatedEndAt = String.format("%s %02d:%02d", selectedEstimatedEndDate,0,00);
        }
        if(startedAt != null && estimatedEndAt == null){
            searchParams.put("startedAt", startedAt);
        }
        else if(estimatedEndAt != null && startedAt == null){
            searchParams.put("estimatedEndAt", estimatedEndAt);
        } else {
            searchParams.put("startedAt", startedAt);
            searchParams.put("estimatedEndAt", estimatedEndAt);
        }
        System.out.println("params: " + searchParams);
        reloadTable();
    }

    public void clearParams(ActionEvent event) {
        resetSearchParams();
        searchParams.clear(); // Clear the search parameters
        reloadTable();
    }

    public void resetSearchParams() {
        surgerySearchStartAtYear.setValue(null);
        surgerySearchEstimatedEndAtYear.setValue(null);
    }

    // chart
    public void initializePieCharts() {
        // Create sample data for PieCharts
        ObservableList<PieChart.Data> pieChartDataMonth = FXCollections.observableArrayList(
                new PieChart.Data("Category A", 30),
                new PieChart.Data("Category B", 40),
                new PieChart.Data("Category C", 20),
                new PieChart.Data("Category D", 10)
        );

        ObservableList<PieChart.Data> pieChartDataQuarter = FXCollections.observableArrayList(
                new PieChart.Data("Category X", 15),
                new PieChart.Data("Category Y", 25),
                new PieChart.Data("Category Z", 60)
        );

        ObservableList<PieChart.Data> pieChartDataYear = FXCollections.observableArrayList(
                new PieChart.Data("Category P", 50),
                new PieChart.Data("Category Q", 30),
                new PieChart.Data("Category R", 20)
        );

        // Set the data to PieChart components
        PieChartMonth.setData(pieChartDataMonth);
        PieChartQuarter.setData(pieChartDataQuarter);
        PieChatYear.setData(pieChartDataYear);
      
    }


}
