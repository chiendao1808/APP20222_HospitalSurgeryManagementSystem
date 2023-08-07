package com.app20222.app20222_fxapp.app_controllers.surgery_view;

import com.app20222.app20222_fxapp.dto.common.CommonIdCodeName;
import com.app20222.app20222_fxapp.dto.requests.medicalRecord.MedicalRecordUpdateDTO;
import com.app20222.app20222_fxapp.dto.requests.surgery.SurgeryCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.surgery.SurgeryRoleDTO;
import com.app20222.app20222_fxapp.dto.responses.surgery.SurgeryRoleTypeDTO;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.comboBox.ComboBoxAPIService;
import com.app20222.app20222_fxapp.services.surgery.SurgeryAPIService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddSurgeryController implements Initializable {
    @FXML
    private ListView<SurgeryRoleDTO> RemoveLstAssignment;

    @FXML
    private Button cancelCreateSurgery;

    @FXML
    private TextField createSurgeryDiseaseGroupId;

    @FXML
    private TextArea createSurgeryDescription;

    @FXML
    private DatePicker createSurgeryEstimatedEndAt;

    @FXML
    private TextField createSurgeryName;

    @FXML
    private ComboBox<CommonIdCodeName> createSurgeryPatientId;

    @FXML
    private ComboBox<CommonIdCodeName> createSurgeryRoomId;

    @FXML
    private ComboBox<Integer> createSurgeryStartHour;

    @FXML
    private ComboBox<Integer> createSurgeryStartMinute;
    @FXML
    private ComboBox<Integer> createSurgeryEstimatedEndHour;

    @FXML
    private ComboBox<Integer> createSurgeryEstimatedEndMinute;
    @FXML
    private DatePicker createSurgeryStartedAt;

    @FXML
    private DialogPane createSurgeryPane;

    @FXML
    private Button removeAssignment;

    @FXML
    private Button selectAssignment;

    @FXML
    private ListView<CommonIdCodeName> selectLstAssignment;

    @FXML
    private Button submitCreateSurgery;
    // api
    private ComboBoxAPIService comboBoxAPIService;
    private SurgeryAPIService surgeryAPIService;
    private List<SurgeryRoleDTO> surgeryAssignmentList;

    List<CommonIdCodeName> lstSurgeryAssignment = new ArrayList<>();
    private SurgeryController surgeryController;

    // Khởi tạo SurgeryController (dùng cho gọi lại api danh sách)
    public void setSurgeryController(SurgeryController surgeryController) {
        this.surgeryController = surgeryController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            surgeryAssignmentList = new ArrayList<>();
            comboBoxAPIService = new ComboBoxAPIService();
            surgeryAPIService = new SurgeryAPIService();
            setUpPatientId();
            setUpSurgeryRoom();
            setUpLstAssignment();
            setUpDate();
    }
    // khởi tạo combobox bệnh nhân
    public void setUpPatientId(){
        ObservableList<CommonIdCodeName> listPatientId = getDataFromDataSource();
        ObservableList<CommonIdCodeName> observableList = FXCollections.observableArrayList(listPatientId);
        createSurgeryPatientId.setConverter(new StringConverter<CommonIdCodeName>() {
            @Override
            public String toString(CommonIdCodeName item) {
                if (item != null) {
                    return item.getName() + " - " + item.getCode();
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


        createSurgeryPatientId.setItems(observableList);
    }

    // Khởi tạo combobox phong phau thuat
    public void setUpSurgeryRoom(){
        ObservableList<CommonIdCodeName> listSurgeryRoom = getDataFromDataSurgeryRoom();
        ObservableList<CommonIdCodeName> observableList = FXCollections.observableArrayList(listSurgeryRoom);
        createSurgeryRoomId.setConverter(new StringConverter<CommonIdCodeName>() {
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

        createSurgeryRoomId.setItems(observableList);
    }

    // Khởi tạo danh sách người thực hiện
    public void setUpLstAssignment(){
        ObservableList<CommonIdCodeName> listSurgeryAssignment = getDataFromDataSurgeryAssignment();
        ObservableList<CommonIdCodeName> observableList = FXCollections.observableArrayList(listSurgeryAssignment);
        selectLstAssignment.setCellFactory(lv -> new ListCell<CommonIdCodeName>() {
            @Override
            protected void updateItem(CommonIdCodeName item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }

            }
        });
        selectLstAssignment.setItems(observableList);

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
        createSurgeryStartHour.setItems(observableHours);
        createSurgeryEstimatedEndHour.setItems(observableHours);

    }

    public void setUpMinuteComboBox() {
        List<Integer> minutes = new ArrayList<>();
        for (int minute = 0; minute < 60; minute++) {
            minutes.add(minute);
        }
        ObservableList<Integer> observableMinutes = FXCollections.observableArrayList(minutes);
        createSurgeryStartMinute.setItems(observableMinutes);
        createSurgeryEstimatedEndMinute.setItems(observableMinutes);
    }
    public void setYear() {
        // Set the date format for the DatePicker to "dd/MM/yyyy"
        createSurgeryStartedAt.setConverter(new StringConverter<>() {
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
        createSurgeryEstimatedEndAt.setConverter(new StringConverter<>() {
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
    // lấy list danh sách bệnh nhân combobox
    private ObservableList<CommonIdCodeName> getDataFromDataSource() {
        List<CommonIdCodeName> lstPatientId = new ArrayList<>();
        try {
            lstPatientId = comboBoxAPIService.getComboBoxPatients(new HashMap<>());
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        return FXCollections.observableArrayList(lstPatientId);
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

    // Lấy danh sách loại ca phẫu thuật
    private ObservableList<SurgeryRoleTypeDTO> getDataFromDataSurgeryType() {
        List<SurgeryRoleTypeDTO> lstSurgeryType = new ArrayList<>();
        try {
            lstSurgeryType = comboBoxAPIService.getComboBoxSurgeryRoleTypes(new HashMap<>());
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        return FXCollections.observableArrayList(lstSurgeryType);
    }

    // Lấy danh sách người thực hiện
    private ObservableList<CommonIdCodeName> getDataFromDataSurgeryAssignment() {

        try {
            lstSurgeryAssignment = comboBoxAPIService.getComboBoxUsers(new HashMap<>());
        } catch (ApiResponseException exception) {
            exception.printStackTrace();
            System.out.println(exception.getExceptionResponse());
        }
        return FXCollections.observableArrayList(lstSurgeryAssignment);
    }

    // Xử lý khi ấn lựa chọn người thực hiện
    @FXML
    private void onSelectAssignmentButtonClick() {
        CommonIdCodeName selectedAssignment = selectLstAssignment.getSelectionModel().getSelectedItem();
        if (selectedAssignment != null) {
            openRoleSelectionModal(selectedAssignment, selectLstAssignment, RemoveLstAssignment);
        }
    }
    // Xử lý khi ấn remove người thực hiện
    @FXML
    private void onRemoveAssignmentButtonClick() {
        SurgeryRoleDTO selectedAssignment = RemoveLstAssignment.getSelectionModel().getSelectedItem();
        if (selectedAssignment != null) {
            CommonIdCodeName assignmentToRemove = findAssignmentInList(selectedAssignment.getAssigneeId());
            if (assignmentToRemove != null) {
                surgeryAssignmentList.remove(assignmentToRemove);
            }
            RemoveLstAssignment.getItems().remove(selectedAssignment);
            selectLstAssignment.getItems().add(assignmentToRemove);
        }
    }


    // Hàm mở modal lựa chọn quyền trong ca phaa thuat
    private void openRoleSelectionModal(CommonIdCodeName selectedAssignment, ListView<CommonIdCodeName> selectLstAssignment, ListView<SurgeryRoleDTO> RemoveLstAssignment) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setTitle("Lựa chọn quyền trong ca phẫu thuật");
        dialogStage.setResizable(false);
        Label label = new Label("Lựa chọn vai trò trong ca phẫu thuật:");
        ComboBox<SurgeryRoleTypeDTO> surgeryTypeComboBox = new ComboBox<>();
        surgeryTypeComboBox.setPrefWidth(200);
        surgeryTypeComboBox.setPromptText("Lựa chọn");
        // eet dat cho combobox
        ObservableList<SurgeryRoleTypeDTO> listSurgeryType = getDataFromDataSurgeryType();
        ObservableList<SurgeryRoleTypeDTO> observableList = FXCollections.observableArrayList(listSurgeryType);
        surgeryTypeComboBox.setConverter(new StringConverter<SurgeryRoleTypeDTO>() {
            @Override
            public String toString(SurgeryRoleTypeDTO item) {
                if (item != null) {
                    return item.getName();
                } else {
                    return "";
                }
            }
            @Override
            public SurgeryRoleTypeDTO fromString(String string) {
                // Chuyển đổi ngược từ chuỗi (nếu cần)
                return null;
            }
        });

        surgeryTypeComboBox.setItems(observableList);
        Button confirmButton = new Button("Xác nhận");
        confirmButton.setOnAction(event -> {
            SurgeryRoleTypeDTO selectedRole = surgeryTypeComboBox.getSelectionModel().getSelectedItem();
            if (selectedRole != null) {
                SurgeryRoleDTO surgeryAssignmentDTO = SurgeryRoleDTO.builder()
                        .assigneeId(selectedAssignment.getId())
                        .assigneeName(selectedAssignment.getName())
                        .surgeryRoleType(Integer.valueOf(String.valueOf(selectedRole.getType())))
                        .build();
                surgeryAssignmentList.add(surgeryAssignmentDTO);
                RemoveLstAssignment.getItems().add(surgeryAssignmentDTO);
                selectLstAssignment.getItems().remove(selectedAssignment);

                RemoveLstAssignment.setCellFactory(lv -> new ListCell<SurgeryRoleDTO>() {
                    @Override
                    protected void updateItem(SurgeryRoleDTO item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getAssigneeName() + " - " + findAssignmentRoleInList(item.getSurgeryRoleType(),listSurgeryType));
                        }

                    }
                });
                dialogStage.close();
            } else {
                // Show a warning or error message, as no role is selected.
                showErrorMessage("Please select a role for the assignment.");
            }
        });
        // Layout setup
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(label, surgeryTypeComboBox, confirmButton);

        Scene scene = new Scene(vbox);
        dialogStage.setScene(scene);

        // Show the stage and wait for it to be closed before returning to the main window
        dialogStage.showAndWait();
    }



    // Tìm kiếm phan tu khi remove di
    public CommonIdCodeName findAssignmentInList(Long getAssigneeId ) {
        for (CommonIdCodeName assignment : lstSurgeryAssignment) {
            if (assignment.getId() == getAssigneeId) {
                return assignment;
            }
        }
        return null;
    }

    public String findAssignmentRoleInList(Integer assigneeId, ObservableList<SurgeryRoleTypeDTO> lstAssignmentRole) {
        for (SurgeryRoleTypeDTO assignmentRole : lstAssignmentRole) {
            if (assignmentRole.getType() == assigneeId) {
                return assignmentRole.getName();
            }
        }
        return null;
    }


    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleSubmit(){
        if (validateForm()) {
            Boolean result = submitForm();
            if (result) {
                createSurgeryPane.getScene().getWindow().hide();
                if (surgeryController != null) {
                    surgeryController.reloadTable();
                }
            }
        }
    }
    @FXML
    public void handleCancel(){
        createSurgeryPane.getScene().getWindow().hide();
    }
    public boolean submitForm(){
        Boolean result = false;
        // Get selected patient and surgery room
        CommonIdCodeName selectedPatient = createSurgeryPatientId.getSelectionModel().getSelectedItem();
        CommonIdCodeName selectedRoom = createSurgeryRoomId.getSelectionModel().getSelectedItem();
        // Get surgery name, description, and disease group ID
        String surgeryName = createSurgeryName.getText();
        String description = createSurgeryDescription.getText();
        String diseaseGroup = createSurgeryDiseaseGroupId.getText();

        // Get selected surgery start date, hour, and minute
        String selectedStartDate = createSurgeryStartedAt.getConverter().toString(createSurgeryStartedAt.getValue());
        Integer selectedStartHour = createSurgeryStartHour.getSelectionModel().getSelectedItem();
        Integer selectedStartMinute = createSurgeryStartMinute.getSelectionModel().getSelectedItem();
        String startedAt = null;

        // Get selected estimated end date, hour, and minute
        String selectedEstimatedEndDate = createSurgeryEstimatedEndAt.getConverter().toString(createSurgeryEstimatedEndAt.getValue());
        Integer selectedEstimatedEndHour = createSurgeryEstimatedEndHour.getSelectionModel().getSelectedItem();
        Integer selectedEstimatedEndMinute = createSurgeryEstimatedEndMinute.getSelectionModel().getSelectedItem();
        String estimatedEndAt = null;
        // Format the selected surgery start date and time
        if (selectedStartDate != null && selectedStartHour != null && selectedStartMinute != null) {
            startedAt = String.format("%s %02d:%02d", selectedStartDate, selectedStartHour, selectedStartMinute);
        }
        // Format the selected estimated end date and time
        if (selectedEstimatedEndDate != null && selectedEstimatedEndHour != null && selectedEstimatedEndMinute != null) {
            estimatedEndAt = String.format("%s %02d:%02d", selectedEstimatedEndDate, selectedEstimatedEndHour, selectedEstimatedEndMinute);
        }
        // Chuyển về dạng Date
        Date startDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        if (startedAt != null) {
            try {
                startDate = dateFormat.parse(startedAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Date estimatedEndDate = null;
        if (estimatedEndAt != null) {
            try {
                estimatedEndDate = dateFormat.parse(estimatedEndAt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        SurgeryCreateDTO newSurgery = SurgeryCreateDTO.builder()
                    .name(surgeryName)
                    .description(description)
                    .diseaseGroup(diseaseGroup)
                    .type(0)
                    .patientId(selectedPatient.getId())
                    .lstAssignment(surgeryAssignmentList)
                    .startedAt(startDate)
                    .estimatedEndAt(estimatedEndDate)
                    .surgeryRoomId(selectedRoom.getId())
                    .status(0)
                    .build();
            // API Call
            try {
                result = surgeryAPIService.createSurgery(newSurgery);
            } catch (ApiResponseException exception) {
                exception.printStackTrace();
                System.out.println(exception.getExceptionResponse());
            }

        return result;
    }

    public boolean validateForm() {
        if (createSurgeryPatientId.getSelectionModel().isEmpty()) {
            showErrorMessage("Vui lòng chọn bệnh nhân.");
            return false;
        }

        if (createSurgeryRoomId.getSelectionModel().isEmpty()) {
            showErrorMessage("Vui lòng chọn phòng phẫu thuật.");
            return false;
        }

        if (createSurgeryName.getText().trim().isEmpty()) {
            showErrorMessage("Vui lòng nhập tên ca phẫu thuật.");
            return false;
        }

        if (createSurgeryStartedAt.getValue() == null || createSurgeryStartHour.getSelectionModel().isEmpty() || createSurgeryStartMinute.getSelectionModel().isEmpty()) {
            showErrorMessage("Vui lòng nhập ngày và giờ bắt đầu ca phẫu thuật.");
            return false;
        }

        if (createSurgeryEstimatedEndAt.getValue() == null || createSurgeryEstimatedEndHour.getSelectionModel().isEmpty() || createSurgeryEstimatedEndMinute.getSelectionModel().isEmpty()) {
            showErrorMessage("Vui lòng nhập ngày và giờ kết thúc dự kiến của ca phẫu thuật.");
            return false;
        }

        if (createSurgeryDescription.getText().trim().isEmpty()) {
            showErrorMessage("Vui lòng nhập mô tả ca phẫu thuật.");
            return false;
        }

        if (createSurgeryDiseaseGroupId.getText().trim().isEmpty()) {
            showErrorMessage("Vui lòng nhập mã nhóm bệnh.");
            return false;
        }

        if (surgeryAssignmentList.isEmpty()) {
            showErrorMessage("Vui lòng chọn ít nhất một người thực hiện.");
            return false;
        }

        return true;
    }

}
