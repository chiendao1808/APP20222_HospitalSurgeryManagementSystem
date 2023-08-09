package com.app20222.app20222_fxapp.app_controllers.surgeryRoom_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.dto.responses.surgeryRoom.SurgeryRoomListDTO;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.surgeryRoom.SurgeryRoomAPIService;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.util.StringConverter;

import java.text.SimpleDateFormat;
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
    // Tìm kiếm
    @FXML
    private TextField SurgeryRoomSearchCode;

    @FXML
    private TextField SurgeryRoomSearchName;

    @FXML
    private ComboBox<Pair<String, Boolean>> SurgeryRoomSearchStatus;
    private SurgeryRoomAPIService surgeryRoomAPIService;
    private Map<String, String> searchParams = new HashMap<>();
    public SurgeryRoomController(TableView<SurgeryRoomListDTO> surgeryRoomTable, TableColumn<SurgeryRoomListDTO, Long> surgeryRoomStt,
                                 TableColumn<SurgeryRoomListDTO, String> surgeryRoomName,
                                 TableColumn<SurgeryRoomListDTO, String> surgeryRoomCode,
                                 TableColumn<SurgeryRoomListDTO, String> surgeryRoomDescription,
                                 TableColumn<SurgeryRoomListDTO, String> surgeryRoomAddress,
                                 TableColumn<SurgeryRoomListDTO, Boolean> surgeryRoomCurrentAvailable,
                                 TableColumn<SurgeryRoomListDTO, Date> surgeryRoomOnServiceAt,
                                 TableColumn<SurgeryRoomListDTO, String> surgeryRoomAction,
                                 TextField SurgeryRoomSearchCode, TextField SurgeryRoomSearchName,
                                 ComboBox<Pair<String, Boolean>> SurgeryRoomSearchStatus) {
        this.surgeryRoomStt = surgeryRoomStt;
        this.surgeryRoomTable = surgeryRoomTable;
        this.surgeryRoomName = surgeryRoomName;
        this.surgeryRoomCode = surgeryRoomCode;
        this.surgeryRoomDescription = surgeryRoomDescription;
        this.surgeryRoomAddress = surgeryRoomAddress;
        this.surgeryRoomCurrentAvailable = surgeryRoomCurrentAvailable;
        this.surgeryRoomOnServiceAt = surgeryRoomOnServiceAt;
        this.surgeryRoomAction = surgeryRoomAction;
        // Tìm kiếm
        this.SurgeryRoomSearchName = SurgeryRoomSearchName;
        this.SurgeryRoomSearchCode = SurgeryRoomSearchCode;
        this.SurgeryRoomSearchStatus = SurgeryRoomSearchStatus;
    }


    /**
     * Reload data when has a change
     */
    public void reloadTable() {
        ObservableList<SurgeryRoomListDTO> surgeryRoomList = getDataFromDataSource();
        if (surgeryRoomTable != null) {
            this.surgeryRoomTable.setItems(surgeryRoomList);
            setupEditDeleteButtons();
        }
    }

    public void initializeSurgeryRoom() {
        surgeryRoomAPIService = new SurgeryRoomAPIService();
        initializeTable();
        initSurgeryRoomSearch();
    }
    // Format Date
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
    public void initializeTable() {
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        ObservableList<SurgeryRoomListDTO> surgeryRoomList = getDataFromDataSource();
        setupTableColumns();
        setupEditDeleteButtons();

        if (surgeryRoomList != null) {
            this.surgeryRoomTable.setItems(surgeryRoomList);
            surgeryRoomCurrentAvailable.setCellFactory(col -> new TableCell<SurgeryRoomListDTO, Boolean>() {
                @Override
                protected void updateItem(Boolean isAvailable, boolean empty) {
                    super.updateItem(isAvailable, empty);
                    if (empty || isAvailable == null) {
                        setText("");
                    } else {
                        setText(isAvailable ? "Còn trống" : "Đã được sử dụng");
                    }
                }
            });
            // Sét kiểu hiển thị
            surgeryRoomOnServiceAt.setCellFactory(column -> new TableCell<>() {
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
    private ObservableList<SurgeryRoomListDTO> getDataFromDataSource() {
        List<SurgeryRoomListDTO> lstSurgeryRoom = new ArrayList<>();
        try {
            lstSurgeryRoom = surgeryRoomAPIService.getListSurgeryRoom(searchParams);
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
        surgeryRoomCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        surgeryRoomName.setCellValueFactory(new PropertyValueFactory<>("name"));
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
                                FontAwesomeIconView viewIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
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
                                    openUpdateDialog(surgeryRoom);
//
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
    // Cập nhật
    public void openUpdateDialog(SurgeryRoomListDTO surgeryRoomListDTO) {
        String fxmlPath = "views/surgeryRoom_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            AddSurgeryRoomController addSurgeryRoomController = loader.getController();
            addSurgeryRoomController.setEditMode(true);
            addSurgeryRoomController.setUpUpdateSurgeryRoom(surgeryRoomListDTO);
            addSurgeryRoomController.initialize(null, null);
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Cập nhật phòng phẫu thuật");
            dialogStage.setScene(new Scene(root));
            dialogStage.setOnHidden(e -> {
                Boolean resultSubmit = addSurgeryRoomController.submit();
                if (Objects.equals(resultSubmit, true)) {
                    reloadTable();
                }
            });
            dialogStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle create dialog
     */
    public void openCreateDialog() {
        String fxmlPath = "views/surgeryRoom_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            AddSurgeryRoomController addSurgeryRoomController = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Thêm mới phòng phẫu thuật");
            dialogStage.setScene(new Scene(root));
            dialogStage.setOnHidden(e -> {
                Boolean resultSubmit = addSurgeryRoomController.submit();
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

    // Tìm kiếm
    // Setup du lieu cho trang thai combobox tìm kiếm
    public void initSurgeryRoomSearch(){
        setUpCurrentAvailable();
    }
    public void setUpCurrentAvailable(){
        SurgeryRoomSearchStatus.getItems().addAll(
                new Pair<>("Còn trống", true),
                new Pair<>("Đang sử dụng", false)
        );

        SurgeryRoomSearchStatus.setConverter(new StringConverter<Pair<String, Boolean>>() {
            @Override
            public String toString(Pair<String, Boolean> object) {
                return object.getKey();
            }

            @Override
            public Pair<String, Boolean> fromString(String string) {
                return SurgeryRoomSearchStatus.getItems().stream()
                        .filter(item -> item.getKey().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });
    }
    @FXML
    public void onSurgerySubmitSearch(ActionEvent event) {
        String name = SurgeryRoomSearchName.getText();
        String code = SurgeryRoomSearchCode.getText();
        Boolean status = SurgeryRoomSearchStatus.getValue().getValue();
        if(name != null) {
            searchParams.put("name", name);
        }
        if(code != null) {
            searchParams.put("code", code);
        }
        searchParams.put("current_available", String.valueOf(status));
        reloadTable();
    }

    @FXML
    public void clearParams(ActionEvent event) {
        resetSearchParams();
        searchParams.clear(); // Clear the search parameters
        reloadTable();
    }

    public void resetSearchParams() {
        // Clear the search fields
        SurgeryRoomSearchCode.setText("");
        SurgeryRoomSearchName.setText("");
        SurgeryRoomSearchStatus.setValue(null);
    }
}
