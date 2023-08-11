package com.app20222.app20222_fxapp.app_controllers.department_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.dto.responses.deparment.DepartmentListDTO;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.department.DepartmentAPIService;
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

import java.util.*;

public class DepartmentController {
    @FXML
    private TableColumn<DepartmentListDTO, String> departmentAction;
    @FXML
    private TableColumn<DepartmentListDTO, String> departmentAddress;
    @FXML
    private TableColumn<DepartmentListDTO, String> departmentCode;
    @FXML
    private TableColumn<DepartmentListDTO, String> departmentDescription;
    @FXML
    private TableColumn<DepartmentListDTO, String> departmentEmail;
    @FXML
    private TableColumn<DepartmentListDTO, String> departmentName;
    @FXML
    private TableColumn<DepartmentListDTO, String> departmentPhoneNumber;
    @FXML
    private TextField departmentSearchCode;
    @FXML
    private TextField departmentSearchEmail;
    @FXML
    private TextField departmentSearchName;
    @FXML
    private TextField departmentSearchPhone;
    @FXML
    private TableColumn<DepartmentListDTO, Long> departmentStt;
    @FXML
    private TableView<DepartmentListDTO> departmentTable;
    @FXML
    private Button createDepartment;
    private DepartmentAPIService departmentAPIService;
    // params tìm kiếm
    Map<String, String> searchParams = new HashMap<>();

    public DepartmentController(TableView<DepartmentListDTO> departmentTable,
                                TableColumn<DepartmentListDTO, String> departmentAction,
                                TableColumn<DepartmentListDTO, String> departmentAddress,
                                TableColumn<DepartmentListDTO, String> departmentCode,
                                TableColumn<DepartmentListDTO, String> departmentDescription,
                                TableColumn<DepartmentListDTO, String> departmentEmail,
                                TableColumn<DepartmentListDTO, String> departmentName,
                                TableColumn<DepartmentListDTO, String> departmentPhoneNumber,
                                TableColumn<DepartmentListDTO, Long> departmentStt,
                                TextField departmentSearchCode,
                                TextField departmentSearchEmail,
                                TextField departmentSearchName,
                                TextField departmentSearchPhone,
                                Button createDepartment) {
        this.departmentTable = departmentTable;
        this.departmentAction = departmentAction;
        this.departmentAddress = departmentAddress;
        this.departmentCode = departmentCode;
        this.departmentDescription = departmentDescription;
        this.departmentEmail = departmentEmail;
        this.departmentName = departmentName;
        this.departmentPhoneNumber = departmentPhoneNumber;
        this.departmentStt = departmentStt;
        this.departmentSearchCode = departmentSearchCode;
        this.departmentSearchEmail = departmentSearchEmail;
        this.departmentSearchName = departmentSearchName;
        this.departmentSearchPhone = departmentSearchPhone;
        this.createDepartment = createDepartment;
    }

    public void initializeDepartment() {
        departmentAPIService = new DepartmentAPIService();
        initializeTable();
    }

    public void initializeTable() {
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        ObservableList<DepartmentListDTO> departmentList = getDataFromDataSource();
        setupTableColumns();
        setupEditDeleteButtons();

        if (departmentList != null) {
            this.departmentTable.setItems(departmentList);

        }
    }

    // Tạo list khoa/ bộ phận (api gọi lấy danh sách ở đây)
    private ObservableList<DepartmentListDTO> getDataFromDataSource() {
        List<DepartmentListDTO> lstDepartment = new ArrayList<>();
        try {
            lstDepartment = departmentAPIService.getListDepartment(searchParams);
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        return FXCollections.observableArrayList(lstDepartment);
    }

    public void setupTableColumns() {
        // Set up the PropertyValueFactory to map the data fields to the table columns
        this.departmentStt.setCellValueFactory(param -> {
            int index = param.getTableView().getItems().indexOf(param.getValue());
            return new SimpleLongProperty(index + 1).asObject();
        });
        departmentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        departmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        departmentAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        departmentEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        departmentPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

    }

    public void setupEditDeleteButtons() {
        Callback<TableColumn<DepartmentListDTO, String>, TableCell<DepartmentListDTO, String>> cellFactory =
                new Callback<>() {
                    @Override
                    public TableCell<DepartmentListDTO, String> call(TableColumn<DepartmentListDTO, String> param) {
                        final TableCell<DepartmentListDTO, String> cell = new TableCell<DepartmentListDTO, String>() {
                            private final Button updateButton = new Button();
                            {
                                FontAwesomeIconView updateIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);


                                updateIcon.setStyle(
                                        " -fx-cursor: hand ;"
                                                + "-glyph-size:24px;"
                                                + "-fx-fill:#00E676;"
                                );
                                updateButton.setGraphic(updateIcon);
                                updateButton.getStyleClass().add("edit-button");

                                // Handle edit button action
                                updateButton.setOnAction(event -> {
                                    DepartmentListDTO department = getTableView().getItems().get(getIndex());
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id", String.valueOf(department.getId()));
                                    openEditDialog(department,params);
                                });

                                setGraphic(new HBox(updateButton));
                            }

                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(new HBox(updateButton));

                                }
                            }
                        };
                        return cell;
                    }
                };

        departmentAction.setCellFactory(cellFactory);
    }

    /**
     * Handle create dialog
     */
    public void openCreateDialog() {
        String fxmlPath = "views/department_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            AddDepartmentController addDepartmentController = loader.getController();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tạo mới khoa/ bộ phận");
            dialogStage.setScene(new Scene(root));
            dialogStage.setOnHidden(e -> {
                Boolean resultSubmit = addDepartmentController.submit();
                if (Objects.equals(resultSubmit, true)) {
                    reloadTable();
                }
            });
            dialogStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Cập nhật khoa/ phòng
    public void openEditDialog(DepartmentListDTO department,  Map<String, String> params) {
        String fxmlPath = "views/department_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            AddDepartmentController addDepartmentController = loader.getController();
            addDepartmentController.setDepartmentEditDTO(department,params);
            addDepartmentController.setEditMode(true);
            addDepartmentController.disableDepartmentCodeFields();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Chỉnh sửa Khoa/ bộ phận");
            dialogStage.setScene(new Scene(root));
            dialogStage.setOnHidden(e -> {
                Boolean resultSubmit = addDepartmentController.submit();
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
     * Reload data when has a change
     */
    public void reloadTable() {
        ObservableList<DepartmentListDTO> departmentList = getDataFromDataSource();
        if (departmentList != null) {
            this.departmentTable.setItems(departmentList);
            setupEditDeleteButtons();
        }
    }

    // Tìm kiếm
    @FXML
    public void onDepartmentSubmitSearch(ActionEvent event) {
        String code = departmentSearchCode.getText();
        String email = departmentSearchEmail.getText();
        String name = departmentSearchName.getText();
        String phoneNumber = departmentSearchPhone.getText();
        // Sử dụng các giá trị được lấy từ các trường tìm kiếm
        searchParams.put("code", code);
        searchParams.put("email", email);
        searchParams.put("name", name);
        searchParams.put("phone", phoneNumber);
        // gọi lại api sau khi tìm kiếm
        reloadTable();
    }

    public void clearParams(ActionEvent event) {
        resetSearchParams();
        searchParams.put("code", "");
        searchParams.put("email", "");
        searchParams.put("name", "");
        searchParams.put("phone", "");
        reloadTable();


    }
    // clear params
    public void resetSearchParams() {
        departmentSearchCode.setText("");
        departmentSearchEmail.setText("");
        departmentSearchName.setText("");
        departmentSearchPhone.setText("");
    }

    // Show dialogStage thêm mới bệnh nhân
    @FXML
    public void showModal(ActionEvent event) {
        openCreateDialog();
    }

}


