package com.app20222.app20222_fxapp.app_controllers.dashboard_view;

import com.app20222.app20222_fxapp.dto.responses.statistics.NumberSurgeryPreviewDTO;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryDetailDTO;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryGetListDTO;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.dashboard.DashboardAPIService;
import com.app20222.app20222_fxapp.services.surgery.SurgeryAPIService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DashboardController {

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
    private DatePicker surgerySearchEndDate;

    @FXML
    private DatePicker surgerySearchStartDate;

    // Tổng số ca
    @FXML
    private Label totalSurgery;

    //chart
    @FXML
    private PieChart pieChartMonth;

    @FXML
    private Text numSurgeryCurrentMonth;

    @FXML
    private PieChart pieChartQuarter;

    @FXML
    private Text numSurgeryCurrentQuarter;

    @FXML
    private PieChart pieChatYear;

    @FXML
    private Text numSurgeryCurrentYear;

    private SurgeryAPIService surgeryAPIService;

    private DashboardAPIService dashboardAPIService;

    // param tìm kiếm
    private Map<String, String> searchParams = new HashMap<>();

    public void initializeSurgery() {
        surgeryAPIService = new SurgeryAPIService();
        dashboardAPIService = new DashboardAPIService();
        initializeTable();
        initSurgerySearch();
        initializePieCharts();
        // Get số ca phẫu thuật đã thực hiện
        totalSurgery.setText(surgeryTable.getItems().size() + "  ca");
        // init số ca phẫu thuật theo các mốc
        initNumSurgeryChart();

    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        return sdf.format(date);
    }

    public void reloadTable() {
        ObservableList<SurgeryGetListDTO> surgeryList = getDataFromDataSource();
        if (surgeryTable != null) {
            this.surgeryTable.setItems(surgeryList);
            setupEditDeleteButtons();
        }
        totalSurgery.setText(surgeryTable.getItems().size() + "  ca");
    }

    public void initNumSurgeryChart(){
        // Init số ca phẫu thuật các biểu đồ
        try {
            NumberSurgeryPreviewDTO numberSurgeryPreview = dashboardAPIService.previewNumberSurgery();
            numSurgeryCurrentMonth.setText("Tháng này: " + numberSurgeryPreview.getCurrentMonthNum() + " ca");
            numSurgeryCurrentQuarter.setText("Quý này: " + numberSurgeryPreview.getCurrentQuarterNum() + " ca");
            numSurgeryCurrentYear.setText("Năm nay: " + numberSurgeryPreview.getCurrentYearNum() + " ca");
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception);
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

    public DashboardController(TableView<SurgeryGetListDTO> surgeryTable, TableColumn<SurgeryGetListDTO, Long> surgerySttColumn,
        TableColumn<SurgeryGetListDTO, String> surgeryCodeColumn, TableColumn<SurgeryGetListDTO, String>
        surgeryNameColumn, TableColumn<SurgeryGetListDTO, String> surgeryPatientNameColumn, TableColumn<SurgeryGetListDTO, String>
        surgeryResultColumn, TableColumn<SurgeryGetListDTO, String> surgeryRoomColumn, TableColumn<SurgeryGetListDTO, String>
        surgeryStatusColumn, TableColumn<SurgeryGetListDTO, String> typeSurgeryColumn,
        TableColumn<SurgeryGetListDTO, String> surgeryActionColumn, TableColumn<SurgeryGetListDTO, String> surgeryDiseaseGroupNameColumn,
        TableColumn<SurgeryGetListDTO, Date> surgeryStartedAtColumn, TableColumn<SurgeryGetListDTO, Date> surgeryEndedAtColumn,
        TableColumn<SurgeryGetListDTO, Date> surgeryEstimatedEndAtColumn,
        DatePicker surgerySearchEndDate, DatePicker surgerySearchStartDate,
        PieChart pieChartMonth, Text numSurgeryCurrentMonth, PieChart pieChartQuarter, Text numSurgeryCurrentQuarter, PieChart pieChatYear,
        Text numSurgeryCurrentYear, Label totalSurgery
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
        this.surgerySearchEndDate = surgerySearchEndDate;
        this.surgerySearchStartDate = surgerySearchStartDate;
        this.totalSurgery = totalSurgery;
        this.pieChartMonth = pieChartMonth;
        this.numSurgeryCurrentMonth = numSurgeryCurrentMonth;
        this.pieChartQuarter = pieChartQuarter;
        this.numSurgeryCurrentQuarter = numSurgeryCurrentQuarter;
        this.pieChatYear = pieChatYear;
        this.numSurgeryCurrentYear = numSurgeryCurrentYear;

    }

    // Tạo list surgery (api gọi lấy danh sách ở đây)
    private ObservableList<SurgeryGetListDTO> getDataFromDataSource() {
        List<SurgeryGetListDTO> lstSurgery = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("startTime", surgerySearchStartDate.getConverter().toString(surgerySearchStartDate.getValue()));
        params.put("endTime", surgerySearchEndDate.getConverter().toString(surgerySearchEndDate.getValue()));
        try {
            lstSurgery = dashboardAPIService.previewListSurgery(params);
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
    public void initSurgerySearch() {
        setUpDate();
    }

    // setUpDate
    public void setUpDate() {
        setYear();
    }

    public void setYear() {
        // Set the date format for the DatePicker to "dd/MM/yyyy"
        surgerySearchStartDate.setConverter(new StringConverter<>() {
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
        surgerySearchEndDate.setConverter(new StringConverter<>() {
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
        String selectedStartDate = surgerySearchStartDate.getConverter().toString(surgerySearchStartDate.getValue());
        String startedAt = null;
        // Get selected estimated end date, hour, and minute
        String selectedEstimatedEndDate = surgerySearchEndDate.getConverter().toString(surgerySearchEndDate.getValue());
        String estimatedEndAt = null;

        // Format the selected surgery start date and time
        if (selectedStartDate != null) {
            startedAt = String.format("%s %02d:%02d", selectedStartDate, 0, 00);
        }
        // Format the selected estimated end date and time
        if (selectedEstimatedEndDate != null) {
            estimatedEndAt = String.format("%s %02d:%02d", selectedEstimatedEndDate, 0, 00);
        }
        if (startedAt != null && estimatedEndAt == null) {
            searchParams.put("startTime", startedAt);
        } else if (estimatedEndAt != null && startedAt == null) {
            searchParams.put("endTime", estimatedEndAt);
        } else {
            searchParams.put("startTime", startedAt);
            searchParams.put("endTime", estimatedEndAt);
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
        surgerySearchStartDate.setValue(null);
        surgerySearchEndDate.setValue(null);
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
        pieChartMonth.setData(pieChartDataMonth);
        pieChartQuarter.setData(pieChartDataQuarter);
        pieChatYear.setData(pieChartDataYear);

    }


}
