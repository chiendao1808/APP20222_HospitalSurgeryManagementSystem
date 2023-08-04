package com.app20222.app20222_fxapp.app_controllers.surgery_view;

import com.app20222.app20222_fxapp.app_controllers.ShowScreen;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryGetListDTO;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.surgery.SurgeryAPIService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
    private TableColumn<SurgeryGetListDTO,String> surgeryStatusColumn;
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

    private SurgeryAPIService surgeryAPIService;

    public SurgeryController() {
    }

    public void initializeSurgery() {
        surgeryAPIService = new SurgeryAPIService();
        initializeTable();
    }
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
    public void initializeTable(){
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        ObservableList<SurgeryGetListDTO> surgeryList = getDataFromDataSource();
        System.out.println("surgeryList" + surgeryList);
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
    public SurgeryController(TableView<SurgeryGetListDTO> surgeryTable, TableColumn<SurgeryGetListDTO,Long> surgerySttColumn,
                             TableColumn<SurgeryGetListDTO,String> surgeryCodeColumn, TableColumn<SurgeryGetListDTO,String>
            surgeryNameColumn, TableColumn<SurgeryGetListDTO,String> surgeryPatientNameColumn, TableColumn<SurgeryGetListDTO,String>
            surgeryResultColumn, TableColumn<SurgeryGetListDTO,String> surgeryRoomColumn, TableColumn<SurgeryGetListDTO,String>
            surgeryStatusColumn, TableColumn<SurgeryGetListDTO,String> typeSurgeryColumn,
                             TableColumn<SurgeryGetListDTO, String> surgeryActionColumn, TableColumn<SurgeryGetListDTO, String> surgeryDiseaseGroupNameColumn,
                             TableColumn<SurgeryGetListDTO, Date> surgeryStartedAtColumn,TableColumn<SurgeryGetListDTO, Date> surgeryEndedAtColumn,
                             TableColumn<SurgeryGetListDTO, Date> surgeryEstimatedEndAtColumn ) {
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
    }

    // Tạo list surgery (api gọi lấy danh sách ở đây)
    private ObservableList<SurgeryGetListDTO> getDataFromDataSource() {
        List<SurgeryGetListDTO> lstSurgery = new ArrayList<>();
        try {
            lstSurgery = surgeryAPIService.getListSurgery(new HashMap<>());
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
                                    SurgeryGetListDTO patient = getTableView().getItems().get(getIndex());
//                                    System.out.println("Patient" + patient);
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id", String.valueOf(patient.getId()));
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

        surgeryActionColumn.setCellFactory(cellFactory);
    }
    @FXML
    public void showModal(ActionEvent event) {
        String FXMLPATH = "views/surgery_view/create.fxml";
        try {
            ShowScreen showWindow = new ShowScreen();
            showWindow.Show(FXMLPATH, "Tạo ca phẫu thuật");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
