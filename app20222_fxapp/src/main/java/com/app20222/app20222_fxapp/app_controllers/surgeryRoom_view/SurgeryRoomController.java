package com.app20222.app20222_fxapp.app_controllers.surgeryRoom_view;

import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordGetListDTO;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordListDTO;
import com.app20222.app20222_fxapp.dto.responses.surgeryRoom.SurgeryRoomListDTO;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.medicalRecord.MedicalRecordAPIService;
import com.app20222.app20222_fxapp.services.surgeryRoom.SurgeryRoomAPIService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.*;

public class SurgeryRoomController {
    @FXML
    private TableColumn<SurgeryRoomListDTO, String> surgeryRoomAction;

    @FXML
    private TableColumn<SurgeryRoomListDTO, String> surgeryRoomAddress;

    @FXML
    private TableColumn<SurgeryRoomListDTO, String> surgeryRoomCode;

    @FXML
    private TableColumn<SurgeryRoomListDTO, Boolean> surgeryRoomCurrentAvailable;

    @FXML
    private TableColumn<SurgeryRoomListDTO, String> surgeryRoomDescription;

    @FXML
    private TableColumn<SurgeryRoomListDTO, String> surgeryRoomName;

    @FXML
    private TableColumn<SurgeryRoomListDTO, Date> surgeryRoomOnServiceAt;

    @FXML
    private TableColumn<SurgeryRoomListDTO, Long> surgeryRoomStt;

    @FXML
    private TableView<SurgeryRoomListDTO> surgeryRoomTable;
    @FXML
    private Button createSurgeryRoom;
    private SurgeryRoomAPIService surgeryRoomAPIService;

    public SurgeryRoomController(TableView<SurgeryRoomListDTO> surgeryRoomTable, TableColumn<SurgeryRoomListDTO, Long> surgeryRoomStt,
                                 TableColumn<SurgeryRoomListDTO, String> surgeryRoomName,
                                 TableColumn<SurgeryRoomListDTO, String> surgeryRoomCode,
                                 TableColumn<SurgeryRoomListDTO, String> surgeryRoomDescription,
                                 TableColumn<SurgeryRoomListDTO, String> surgeryRoomAddress,
                                 TableColumn<SurgeryRoomListDTO, Boolean> surgeryRoomCurrentAvailable,
                                 TableColumn<SurgeryRoomListDTO, Date> surgeryRoomOnServiceAt,
                                 TableColumn<SurgeryRoomListDTO, String> surgeryRoomAction) {
        this.surgeryRoomStt = surgeryRoomStt;
        this.surgeryRoomTable = surgeryRoomTable;
        this.surgeryRoomName = surgeryRoomName;
        this.surgeryRoomCode = surgeryRoomCode;
        this.surgeryRoomDescription = surgeryRoomDescription;
        this.surgeryRoomAddress = surgeryRoomAddress;
        this.surgeryRoomCurrentAvailable = surgeryRoomCurrentAvailable;
        this.surgeryRoomOnServiceAt = surgeryRoomOnServiceAt;
        this.surgeryRoomAction = surgeryRoomAction;
    }

    public void initializeMedicalRecord() {
        surgeryRoomAPIService = new SurgeryRoomAPIService();
        initializeTable();
    }
    public void initializeTable() {
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        ObservableList<SurgeryRoomListDTO> surgeryRoomList = getDataFromDataSource();
        System.out.println("surgeryRoomList" + surgeryRoomList);
        setupTableColumns();
        setupEditDeleteButtons();

        if (surgeryRoomList != null) {
            this.surgeryRoomTable.setItems(surgeryRoomList);

        }
    }
    // Tạo list HSBA (api gọi lấy danh sách ở đây)
    private ObservableList<SurgeryRoomListDTO> getDataFromDataSource() {
        List<SurgeryRoomListDTO> lstSurgeryRoom = new ArrayList<>();
        try {
            lstSurgeryRoom = surgeryRoomAPIService.getListSurgeryRoom(new HashMap<>());
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        return FXCollections.observableArrayList(lstSurgeryRoom);
    }

    public void setupTableColumns() {
        // Set up the PropertyValueFactory to map the data fields to the table columns
        this.surgeryRoomStt.setCellValueFactory(param -> {
            int index = param.getTableView().getItems().indexOf(param.getValue());
            return new SimpleLongProperty(index + 1).asObject();
        });
        surgeryRoomCode.setCellValueFactory(new PropertyValueFactory<>("name"));
        surgeryRoomName.setCellValueFactory(new PropertyValueFactory<>("code"));
        surgeryRoomDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        surgeryRoomAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        surgeryRoomCurrentAvailable.setCellValueFactory(new PropertyValueFactory<>("currentAvailable"));
        surgeryRoomOnServiceAt.setCellValueFactory(new PropertyValueFactory<>("onServiceAt"));

    }

    public void setupEditDeleteButtons() {
        Callback<TableColumn<SurgeryRoomListDTO, String>, TableCell<SurgeryRoomListDTO, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<SurgeryRoomListDTO, String> call(TableColumn<SurgeryRoomListDTO, String> param) {
                        final TableCell<SurgeryRoomListDTO, String> cell = new TableCell<SurgeryRoomListDTO, String>() {
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
                                    SurgeryRoomListDTO surgeryRoom = getTableView().getItems().get(getIndex());
//                                    System.out.println("Patient" + patient);
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id", String.valueOf(surgeryRoom.getId()));
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

        surgeryRoomAction.setCellFactory(cellFactory);
    }
}
