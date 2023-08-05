package com.app20222.app20222_fxapp.app_controllers.patient_view;

import com.app20222.app20222_fxapp.MainApplication;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientDetailsDTO;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListNewDTO;
import com.app20222.app20222_fxapp.enums.users.IdentityTypeEnum;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
    private TableColumn<PatientGetListNewDTO, Date> patientBirthdayColumn;

    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientNameColumn;
    @FXML
    private TableColumn<PatientGetListNewDTO, Long> patientIdColumn;
    @FXML
    private TableColumn<PatientGetListNewDTO, String> patientPhoneColumn;
    // Tìm kiếm
    @FXML
    private TextField patientSearchCode;

    @FXML
    private TextField patientSearchEmail;

    @FXML
    private TextField patientSearchIdNumber;

    @FXML
    private ComboBox<String> patientSearchIdentityType;

    @FXML
    private TextField patientSearchName;

    @FXML
    private TextField patientSearchPhone;

    @FXML
    private Button patientSubmitSearch;
    private PatientAPIService patientAPIService;

    // dùng cho loại gấy chưn thực
    private final Map<String, String> identityTypeMap = new HashMap<>();
    // params tìm kiếm
    Map<String, String> searchParams = new HashMap<>();

    public PatientController() {
    }

    public PatientController(TableView<PatientGetListNewDTO> patientTable,
                             TableColumn<PatientGetListNewDTO, Long> idColumn,
                             TableColumn<PatientGetListNewDTO, String> nameColumn,
                             TableColumn<PatientGetListNewDTO, String> codeColumn,
                             TableColumn<PatientGetListNewDTO, Date> birthdayColumn,
                             TableColumn<PatientGetListNewDTO, String> phoneColumn,
                             TableColumn<PatientGetListNewDTO, String> addressColumn,
                             TableColumn<PatientGetListNewDTO, String> actionColumn,
                             TextField patientSearchCode, TextField patientSearchEmail, TextField patientSearchIdNumber,
                             ComboBox<String> patientSearchIdentityType, TextField patientSearchName, TextField patientSearchPhone,
                             Button patientSubmitSearch) {
        this.patientTable = patientTable;
        this.patientIdColumn = idColumn;
        this.patientNameColumn = nameColumn;
        this.patientCodeColumn = codeColumn;
        this.patientBirthdayColumn = birthdayColumn;
        this.patientPhoneColumn = phoneColumn;
        this.patientAddressColumn = addressColumn;
        this.patientActionColumn = actionColumn;
        this.patientSearchCode = patientSearchCode;
        this.patientSearchEmail = patientSearchEmail;
        this.patientSearchIdNumber = patientSearchIdNumber;
        this.patientSearchIdentityType = patientSearchIdentityType;
        this.patientSearchName = patientSearchName;
        this.patientSearchPhone = patientSearchPhone;
        this.patientSubmitSearch = patientSubmitSearch;
        // Initialize the table with the provided columns
    }


    // fomat date về string
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }
    // Khởi tạo table
    public void initializePatient(){
        patientAPIService = new PatientAPIService();
        initializeTable();
        setupIdentityTypes();
    }
    public void initializeTable() {
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        ObservableList<PatientGetListNewDTO> patientList = getDataFromDataSource();
        setupTableColumns();
        setupEditDeleteButtons();

        if (patientTable != null) {
            this.patientTable.setItems(patientList);
            patientBirthdayColumn.setCellFactory(column -> new TableCell<>() {
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

    /**
     * Reload data when has a change
     */
    public void reloadTable() {
        ObservableList<PatientGetListNewDTO> patientList = getDataFromDataSource();
        if (patientTable != null) {
            this.patientTable.setItems(patientList);
            setupEditDeleteButtons();
        }
    }

    // Tạo list patients (api gọi lấy danh sách ở đây)
    private ObservableList<PatientGetListNewDTO> getDataFromDataSource() {
        // Replace this method with your actual logic to fetch data from the data source
        List<PatientGetListNewDTO> lstPatient = new ArrayList<>();
        try {
            lstPatient = patientAPIService.getListPatient(searchParams);
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

    public void setupIdentityTypes() {
        identityTypeMap.put(IdentityTypeEnum.CITIZEN_ID_CARD.name(), IdentityTypeEnum.CITIZEN_ID_CARD.getType());
        identityTypeMap.put(IdentityTypeEnum.ID_CARD.name(), IdentityTypeEnum.ID_CARD.getType());
        identityTypeMap.put(IdentityTypeEnum.PASSPORT.name(), IdentityTypeEnum.PASSPORT.getType());
        // Create an ObservableList to hold the labels for the identity types
        ObservableList<String> identityTypeLabels = FXCollections.observableArrayList(identityTypeMap.values());

        // Set the items in the ComboBox to display the identity type labels
        patientSearchIdentityType.setItems(identityTypeLabels);

        // Set a StringConverter to map the selected identityType to its corresponding value
        patientSearchIdentityType.setConverter(new StringConverter<>() {
            @Override
            public String toString(String label) {
                return label; // Display the label in the ComboBox
            }

            @Override
            public String fromString(String string) {
                // Convert the label back to its corresponding value
                IdentityTypeEnum type = IdentityTypeEnum.typeOf(string);
                return type == null ? "" : type.name();
            }
        });
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
                                    Map<String, String> params = new HashMap<>();
                                    params.put("id", String.valueOf(patient.getId()));
                                    try {
                                        PatientDetailsDTO detailsPatientDTO = patientAPIService.getDetailsPatient(params);
                                        System.out.println("detailsPatientDTO" + detailsPatientDTO);
                                        openEditDialog(detailsPatientDTO,params);
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

        patientActionColumn.setCellFactory(cellFactory);
    }


    // Hàm thực hiển mở dialogStage để chỉnh sửa thông tin bệnh nhân
    @FXML
    private void openEditDialog(PatientDetailsDTO patient, Map<String, String> params) {
        String fxmlPath = "views/patient_view/create.fxml";
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlPath));
            Parent root = loader.load();
            AddPatientController addPatientController = loader.getController();
            addPatientController.disableAllFields();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Xem chi tiết bệnh nhân");
            dialogStage.setScene(new Scene(root));
            addPatientController.setPatientId(params);
            addPatientController.setText(
                    patient.getIdentificationNumber(),
                    patient.getFirstName(),
                    patient.getLastName(),
                    patient.getIdentityType(),
                    patient.getHealthInsuranceNumber(),
                    patient.getBirthDate(),
                    patient.getAddress(),
                    patient.getPhoneNumber(),
                    patient.getEmail()
            );
            dialogStage.setOnHidden(e -> {
                Boolean resultSubmit = addPatientController.submit();
                if (Objects.equals(resultSubmit, true)) {
                    reloadTable();
                }
            });
            dialogStage.show();
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

    // Tìm kiếm
    @FXML
    public void onPatientSubmitSearch(ActionEvent event) {
        String code = patientSearchCode.getText();
        String email = patientSearchEmail.getText();
        String idNumber = patientSearchIdNumber.getText();
        String selectedIdentityTypeLabel = patientSearchIdentityType.getValue();
        String identityType = String.valueOf(IdentityTypeEnum.typeOf(selectedIdentityTypeLabel));
        String name = patientSearchName.getText();
        String phone = patientSearchPhone.getText();

        // Sử dụng các giá trị được lấy từ các trường tìm kiếm
        // Gọi phương thức fetchSearchResults() hoặc thực hiện các hành động cần thiết.
         searchParams.put("code", code);
         searchParams.put("email", email);
         searchParams.put("idNumber", idNumber);
         searchParams.put("identityType", identityType);
         searchParams.put("name", name);
         searchParams.put("phoneNumber", phone);
         System.out.println(searchParams);
         // gọi lại api sau khi tìm kiếm
         reloadTable();

    }


}
