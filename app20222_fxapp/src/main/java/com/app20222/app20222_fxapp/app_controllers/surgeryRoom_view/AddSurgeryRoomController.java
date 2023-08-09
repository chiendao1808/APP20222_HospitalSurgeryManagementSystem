package com.app20222.app20222_fxapp.app_controllers.surgeryRoom_view;

import com.app20222.app20222_fxapp.dto.requests.patient.PatientCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.patient.PatientUpdateDTO;
import com.app20222.app20222_fxapp.dto.requests.surgeryRoom.SurgeryRoomCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.surgeryRoom.SurgeryRoomUpdateDTO;
import com.app20222.app20222_fxapp.dto.responses.surgeryRoom.SurgeryRoomListDTO;
import com.app20222.app20222_fxapp.enums.users.IdentityTypeEnum;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.surgeryRoom.SurgeryRoomAPIService;
import com.app20222.app20222_fxapp.utils.DateUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddSurgeryRoomController implements Initializable {
    @FXML
    private DialogPane createSurgeryRoomPane;

    @FXML
    private RadioButton createSurgeryRoomActive;

    @FXML
    private TextField createSurgeryRoomAddress;

    @FXML
    private TextField createSurgeryRoomCode;

    @FXML
    private TextArea createSurgeryRoomDescription;

    @FXML
    private TextField createSurgeryRoomName;

    @FXML
    private RadioButton createSurgeryRoomNotActive;

    @FXML
    private DatePicker createSurgeryRoomOnServiceAt;

    private ToggleGroup activeToggleGroup;

    private Boolean reloadRequired = false;

    private boolean editMode = false;
    private SurgeryRoomAPIService surgeryRoomAPIService;
    private Boolean isSurgeryRoomActive = true;
    private Map<String, String> id = new HashMap<>();
    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        surgeryRoomAPIService = new SurgeryRoomAPIService();
        activeToggleGroup = new ToggleGroup();
        setUpSurgeryRoomStatus();
        setupButtonEventFilters();
        setUpSurgeryOnServiceAt();
        if(editMode) {
            updateSurgeryRoomUI();
        }
    }

    // Set up cho màn cập nhật UI
    public void updateSurgeryRoomUI(){
        createSurgeryRoomCode.setDisable(true);
        createSurgeryRoomOnServiceAt.setDisable(true);
    }
    // Sét thông tin cho các trường khi vào màn cập nhật phòng phẫu thuật
    public void setUpUpdateSurgeryRoom(SurgeryRoomListDTO surgeryRoomListDTO){
        id.put("id", String.valueOf(surgeryRoomListDTO.getId()));
        this.createSurgeryRoomName.setText(surgeryRoomListDTO.getName());
        this.createSurgeryRoomCode.setText(surgeryRoomListDTO.getCode());
        this.createSurgeryRoomDescription.setText(surgeryRoomListDTO.getDescription());
        this.createSurgeryRoomAddress.setText(surgeryRoomListDTO.getAddress());
        this.createSurgeryRoomOnServiceAt.setValue(DateUtils.asLocalDate(surgeryRoomListDTO.getOnServiceAt()));
        if (surgeryRoomListDTO.getCurrentAvailable()) {
            this.createSurgeryRoomActive.setSelected(true);
            this.createSurgeryRoomNotActive.setSelected(false);
        } else {
            this.createSurgeryRoomActive.setSelected(false);
            this.createSurgeryRoomNotActive.setSelected(true);
        }
    }

    // Chỉ cho chọn 1 trong 2 kiểu radio
    public void setUpSurgeryRoomStatus(){
        createSurgeryRoomActive.setToggleGroup(activeToggleGroup);
        createSurgeryRoomNotActive.setToggleGroup(activeToggleGroup);
        createSurgeryRoomActive.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                isSurgeryRoomActive = true;
            }
        });

        createSurgeryRoomNotActive.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                isSurgeryRoomActive = false;
            }
        });
    }
    // Set up hiển thị dạng date
    public void setUpSurgeryOnServiceAt(){
        createSurgeryRoomOnServiceAt.setConverter(new StringConverter<>() {
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
    // set up 2 nut submit và cancel
    private void setupButtonEventFilters() {
        Button okButton = (Button) createSurgeryRoomPane.lookupButton(ButtonType.OK);
        okButton.setOnAction(event -> handleButtonAction(ButtonType.OK));

        Button cancelButton = (Button) createSurgeryRoomPane.lookupButton(ButtonType.CANCEL);
        cancelButton.setOnAction(event -> handleButtonAction(ButtonType.CANCEL));
    }
    // Vaidate
    private boolean isAllFieldsFilled() {
        // Kiểm tra tất cả các trường đã được điền đầy đủ hay không
        boolean allFieldsFilled = true;

        if (createSurgeryRoomName.getText().isEmpty()) {
            allFieldsFilled = false;
        }
        if (createSurgeryRoomCode.getText().isEmpty()) {
            allFieldsFilled = false;
        }
        if (createSurgeryRoomDescription.getText().isEmpty()) {
            allFieldsFilled = false;
        }
        if (createSurgeryRoomAddress.getText().isEmpty()) {
            allFieldsFilled = false;
        }
        if (createSurgeryRoomOnServiceAt.getValue() == null) {
            allFieldsFilled = false;
        }
        if (activeToggleGroup.getSelectedToggle() == null) {
            allFieldsFilled = false; // Nếu không có radio nào được chọn, không hợp lệ
        }
        return allFieldsFilled;
    }

    public void handleButtonAction(ButtonType buttonType) {
        if (buttonType.getButtonData() == ButtonType.OK.getButtonData() ) {
            reloadRequired = handleOkButton();
            System.out.println(reloadRequired);
            if (reloadRequired) {
                createSurgeryRoomPane.getScene().getWindow().hide();
            }
        } else {
            createSurgeryRoomPane.getScene().getWindow().hide();
        }


    }
    public void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Thông tin không hợp lệ");
        alert.setContentText(message);
        alert.showAndWait();
    }
    public Boolean handleOkButton() {
        Boolean result = false;
        // Kiểm tra xem tất cả các trường đã được nhập
            if (isAllFieldsFilled()) {
                // Lấy giá trị từ các thành phần
                String surgeryRoomName = createSurgeryRoomName.getText();
                String surgeryRoomCode = createSurgeryRoomCode.getText();
                String surgeryRoomDescription = createSurgeryRoomDescription.getText();
                String surgeryRoomAddress = createSurgeryRoomAddress.getText();
                LocalDate onServiceDate = createSurgeryRoomOnServiceAt.getValue();

                // Gather the information from the input fields
                if (editMode) {
                        SurgeryRoomUpdateDTO newSurgeryRoom = SurgeryRoomUpdateDTO.builder()
                                .name(surgeryRoomName)
                                .description(surgeryRoomDescription)
                                .address(surgeryRoomAddress)
                                .currentAvailable(isSurgeryRoomActive)
                                .build();
                        // API Call
                        try {
                            result = surgeryRoomAPIService.updateSurgeryRoom(newSurgeryRoom,id);
                        } catch (ApiResponseException exception) {
                            exception.printStackTrace();
                            System.out.println(exception.getExceptionResponse());
                        }
                    } else {
                    SurgeryRoomCreateDTO newSurgeryRoom = SurgeryRoomCreateDTO.builder()
                            .name(surgeryRoomName)
                            .code(surgeryRoomCode)
                            .description(surgeryRoomDescription)
                            .address(surgeryRoomAddress)
                            .onServiceAt(DateUtils.asDate(onServiceDate))
                            .currentAvailable(isSurgeryRoomActive)
                            .build();
                    // API Call
                    try {
                        result = surgeryRoomAPIService.createSurgeryRoom(newSurgeryRoom);
                    } catch (ApiResponseException exception) {
                        exception.printStackTrace();
                        System.out.println(exception.getExceptionResponse());
                    }
                    }
                } else {
                // Hiển thị thông báo lỗi nếu chưa nhập đủ các trường
                displayErrorAlert("Vui lòng nhập đầy đủ thông tin trước khi tiếp tục.");
                return false;
            }
        return result;
    }
    public Boolean submit() {
        return reloadRequired;

    }
}
