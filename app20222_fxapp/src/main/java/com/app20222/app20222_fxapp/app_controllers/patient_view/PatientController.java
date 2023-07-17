package com.app20222.app20222_fxapp.app_controllers.patient_view;

import com.app20222.app20222_fxapp.app_controllers.ShowScreen;
import com.app20222.app20222_fxapp.dto.responses.patient.PatientGetListDTO;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PatientController  {
    @FXML
    private TableView<PatientGetListDTO> patientTable;
    @FXML
    private TableColumn<PatientGetListDTO, String> patientActionColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientCodeColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientEmailColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientHealthInsuranceNumberColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientBirthdayColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientNameColumn;
    @FXML
    private TableColumn<PatientGetListDTO, String> patientFirstNameColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientLastNameColumn;
    @FXML
    private TableColumn<PatientGetListDTO, Long> patientIdColumn;

    @FXML
    private TableColumn<PatientGetListDTO, String> patientPhoneColumn;


    public PatientController() {}

    public PatientController(TableView<PatientGetListDTO> patientTable,
                             TableColumn<PatientGetListDTO, String> codeColumn,
                             TableColumn<PatientGetListDTO, String> emailColumn,
                             TableColumn<PatientGetListDTO, String> healthInsuranceNumberColumn,
                             TableColumn<PatientGetListDTO, String> birthdayColumn,
                             TableColumn<PatientGetListDTO, String> nameColumn,
                             TableColumn<PatientGetListDTO, String> firstNameColumn,
                             TableColumn<PatientGetListDTO, String> lastNameColumn,
                             TableColumn<PatientGetListDTO, Long> idColumn,
                             TableColumn<PatientGetListDTO, String> phoneColumn,
                             TableColumn<PatientGetListDTO, String> patientActionColumn) {
        this.patientTable = patientTable;
        this.patientCodeColumn = codeColumn;
        this.patientEmailColumn = emailColumn;
        this.patientHealthInsuranceNumberColumn = healthInsuranceNumberColumn;
        this.patientBirthdayColumn = birthdayColumn;
        this.patientNameColumn = nameColumn;
        this.patientFirstNameColumn = firstNameColumn;
        this.patientLastNameColumn = lastNameColumn;
        this.patientIdColumn = idColumn;
        this.patientPhoneColumn = phoneColumn;
        this.patientActionColumn = patientActionColumn;
        // Initialize the table with the provided columns
    }

    public TableColumn<PatientGetListDTO, Long> getPatientIdColumn() {
        return patientIdColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientActionColumn() {
        return patientActionColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientEmailColumn() {
        return patientEmailColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientCodeColumn() {
        return patientCodeColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientHealthInsuranceNumberColumn() {
        return patientHealthInsuranceNumberColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientBirthdayColumn() {
        return patientBirthdayColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientNameColumn() {
        return patientNameColumn;
    }

    public TableColumn<PatientGetListDTO, String> getPatientPhoneColumn() {
        return patientPhoneColumn;
    }


    public void initializeTable() {
        // Lấy danh sách bệnh nhân từ nguồn dữ liệu của bạn
        List<PatientGetListDTO> patients = new ArrayList<>(); // Thay thế getDataFromDataSource() bằng phương thức lấy dữ liệu từ nguồn dữ liệu của bạn
        patients.add(new PatientGetListDTO(1L, "1234567890", "2345544455", "John", "Doe", LocalDate.now(), "Address 1", "1234567890", "john.doe@example.com"));
        patients.add(new PatientGetListDTO(2L, "0987654321", "6644333222", "Jane", "Smith", LocalDate.now(), "Address 2", "0987654321", "jane.smith@example.com"));

        // Tạo danh sách ObservableList từ danh sách bệnh nhân
        ObservableList<PatientGetListDTO> patientList = FXCollections.observableArrayList(patients);

        // Thiết lập cột cho TableView
        this.patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        this.patientCodeColumn.setCellValueFactory(new PropertyValueFactory<>("identificationNumber"));
        this.patientFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        this.patientLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        this.patientBirthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        this.patientHealthInsuranceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("healthInsuranceNumber"));
        this.patientEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.patientPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        //add cell of button edit
        Callback<TableColumn<PatientGetListDTO, String>, TableCell<PatientGetListDTO, String>> cellFactory =
                new Callback<TableColumn<PatientGetListDTO, String>, TableCell<PatientGetListDTO, String>>() {
                    @Override
                    public TableCell<PatientGetListDTO, String> call(TableColumn<PatientGetListDTO, String> param) {
                        final TableCell<PatientGetListDTO, String> cell = new TableCell<PatientGetListDTO, String>() {
                            private final Button editButton = new Button();
                            private final Button deleteButton = new Button();
                            {
                                FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
                                FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                                deleteIcon.setStyle(
                                        " -fx-cursor: hand ;"
                                                + "-glyph-size:24px;"
                                                + "-fx-fill:#ff1744;"
                                );
                                editIcon.setStyle(
                                        " -fx-cursor: hand ;"
                                                + "-glyph-size:24px;"
                                                + "-fx-fill:#00E676;"
                                );
                                HBox buttonContainer = new HBox(10); // Set the spacing between icons (10 pixels in this example)
                                buttonContainer.getChildren().addAll(editButton, deleteButton);
                                editButton.setGraphic(editIcon);
                                deleteButton.setGraphic(deleteIcon);
                                editButton.getStyleClass().add("edit-button");
                                deleteButton.getStyleClass().add("delete-button");

                                // Handle edit button action
                                editButton.setOnAction(event -> {
                                    PatientGetListDTO patient = getTableView().getItems().get(getIndex());
                                    String identificationNumber = patient.getIdentificationNumber();
                                    String firstName = patient.getFirstName();
                                    String lastName = patient.getLastName();
                                    String healthInsuranceNumber = patient.getHealthInsuranceNumber();
                                    LocalDate birthDate = patient.getBirthDate();
                                    String address = patient.getAddress();
                                    String phoneNumber = patient.getPhoneNumber();
                                    String email = patient.getEmail();
                                    System.out.println("identificationNumber" + identificationNumber);
                                    // Add your edit button action logic here
                                    AddPatientController addPatientController = new AddPatientController();
                                    addPatientController.setText(identificationNumber, firstName, lastName, "Chứng minh nhân dân", healthInsuranceNumber, birthDate, address, phoneNumber, email);
                                    String FXMLPATH = "views/patient_view/create.fxml";
                                    try {
                                        ShowScreen showWindow =new ShowScreen();
                                        showWindow.Show(FXMLPATH,"Chỉnh sửa bệnh nhân");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                });

                                // Handle delete button action
                                deleteButton.setOnAction(event -> {
                                    PatientGetListDTO patient = getTableView().getItems().get(getIndex());
                                    // Add your delete button action logic here
                                });

                                setGraphic(buttonContainer);
                            }

                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        patientActionColumn.setCellFactory(cellFactory);

        if (patientTable != null) {
            this.patientTable.setItems(patientList);
        }

    }
    @FXML
    public void showModal(ActionEvent event)  {
        String FXMLPATH = "views/patient_view/create.fxml";
        try {
            ShowScreen showWindow =new ShowScreen();
            showWindow.Show(FXMLPATH,"Tạo mới bệnh nhân");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
