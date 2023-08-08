package com.app20222.app20222_fxapp.app_controllers.medicalRecord_view;

import com.app20222.app20222_fxapp.dto.common.CommonIdCodeName;
import com.app20222.app20222_fxapp.dto.file_attach.FileAttachDTO;
import com.app20222.app20222_fxapp.dto.requests.medicalRecord.MedicalRecordCreateDTO;
import com.app20222.app20222_fxapp.dto.requests.medicalRecord.MedicalRecordUpdateDTO;
import com.app20222.app20222_fxapp.dto.responses.FileUploadResDTO;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordDetailsDTO;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordDetailsRes;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.comboBox.ComboBoxAPIService;
import com.app20222.app20222_fxapp.services.fileUpload.FileUploadAPIService;
import com.app20222.app20222_fxapp.services.medicalRecord.MedicalRecordAPIService;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.File;
import java.net.URL;
import java.util.*;

public class AddMedicalRecordController implements Initializable {
    @FXML
    private DialogPane createMedicalRecordPane;

    @FXML
    private Button medicalRecordFile;

    @FXML
    private ComboBox<CommonIdCodeName> medicalRecordPatientId;

    @FXML
    private TextArea medicalRecordSummary;

    @FXML
    private Label medicalRecordFileName;

//    private ProgressIndicator progressIndicator;

    private ComboBoxAPIService comboBoxAPIService ;

    private FileUploadAPIService fileUploadAPIService;
    private MedicalRecordAPIService medicalRecordAPIService;
    private Boolean reloadRequired = false;
    private Set<Long> lstFileAttachId = new HashSet<>();
    private Map<String, String> params;
    private boolean updateMode = false;
    private String messageError;

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }
    public void setUpdateMode(boolean editMode) {
        this.updateMode = editMode;
    }
    public String getMessageError() {
        return messageError;
    }



    public void setMedicalRecordUpdateDTO(MedicalRecordDetailsRes medicalRecordDetailsRes, Map<String, String> params){
        this.params = params;
        MedicalRecordDetailsDTO detailsDTO = medicalRecordDetailsRes.getDetailMedicalRecord();
        if (detailsDTO != null) {
            this.medicalRecordSummary.setText(detailsDTO.getSummary());
            Long patientId = detailsDTO.getPatientId();
            CommonIdCodeName selectedPatient = findPatientById(patientId);
            if (selectedPatient != null) {
                medicalRecordPatientId.setValue(selectedPatient);
            }
        }
        List<FileAttachDTO> fileList = medicalRecordDetailsRes.getLstMedicalRecordFile();
        if (fileList != null && !fileList.isEmpty()) {
            for (FileAttachDTO file : fileList) {
                medicalRecordFileName.setText(file.getFileName());
            }
        } else {
            medicalRecordFileName.setText("File không tồn tại");
        }
    }
    // diable lụa chọn benh nhan
    public void disablePatientFields() {
        String boldStyle = "-fx-font-weight: bold;";
        this.medicalRecordPatientId.setDisable(true);
        this.medicalRecordPatientId.setStyle(boldStyle);
    }

    // Hàm khởi tạo bạn đầu
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxAPIService = new ComboBoxAPIService();
        fileUploadAPIService = new FileUploadAPIService();
        medicalRecordAPIService = new MedicalRecordAPIService();
        setupButtonEventFilters();
        setUpMedicalRecordPatientId();

    }
    // Lấy bênh nhân theo ID
    private CommonIdCodeName findPatientById(Long patientId) {
        ObservableList<CommonIdCodeName> patients = medicalRecordPatientId.getItems();
        for (CommonIdCodeName patient : patients) {
            if (patient.getId().equals(patientId)) {
                return patient;
            }
        }

        return null;
    }
    // Khởi tạo bệnh nhân danh sách lựa chọn
    private void setUpMedicalRecordPatientId(){
        ObservableList<CommonIdCodeName> listPatientId = getDataFromDataSource();
        ObservableList<CommonIdCodeName> observableList = FXCollections.observableArrayList(listPatientId);
        medicalRecordPatientId.setConverter(new StringConverter<CommonIdCodeName>() {
            @Override
            public String toString(CommonIdCodeName item) {
                return item.getName() + " - " + item.getCode(); // Hiển thị tên bệnh nhân trong ComboBox
            }

            @Override
            public CommonIdCodeName fromString(String string) {
                // Chuyển đổi ngược từ chuỗi (nếu cần)
                return null;
            }
        });

        medicalRecordPatientId.setItems(observableList);
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
    private void setupButtonEventFilters() {
        Button okButton = (Button) createMedicalRecordPane.lookupButton(ButtonType.OK);
        okButton.setOnAction(event -> handleButtonAction(ButtonType.OK));

        Button cancelButton = (Button) createMedicalRecordPane.lookupButton(ButtonType.CANCEL);
        cancelButton.setOnAction(event -> handleButtonAction(ButtonType.CANCEL));
    }
    // Hàm hiển thị thông báo validate
    public void displayErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText("Thông tin không hợp lệ");
        alert.setContentText(getMessageError());
        alert.showAndWait();
    }

    // Kiểm tra xem đã nhập đủ các trường chưa
    public boolean isAllFieldsFilled() {
        CommonIdCodeName patientId = medicalRecordPatientId.getValue();
        String summary = medicalRecordSummary.getText();
        String fileName = medicalRecordFileName.getText();
        if (patientId == null
                || summary == null || summary.trim().isEmpty()
                || fileName == null || fileName.trim().isEmpty()) {
            this.setMessageError("Vui lòng nhập đầy đủ thông tin trước khi tiếp tục.");
            return false;
        }
        return true;
    }

    public void handleButtonAction(ButtonType buttonType) {
        if (buttonType.getButtonData() == ButtonType.OK.getButtonData() ) {
            reloadRequired = handleOkButton();
            System.out.println(reloadRequired);
            if (reloadRequired) {
                createMedicalRecordPane.getScene().getWindow().hide();
            }
        } else {
            createMedicalRecordPane.getScene().getWindow().hide();
        }

    }

    private Boolean handleOkButton() {
        Boolean result = false;

        if(isAllFieldsFilled()){
            CommonIdCodeName patientId = medicalRecordPatientId.getValue();
            String summary = medicalRecordSummary.getText();
            if(updateMode){
                MedicalRecordUpdateDTO newMedicalRecord = MedicalRecordUpdateDTO.builder()
                        .summary(summary)
                        .lstFileAttachId(lstFileAttachId)
                        .build();
                // API Call
                try {
                    result = medicalRecordAPIService.updateMedicalRecord(newMedicalRecord,params);
                } catch (ApiResponseException exception) {
                    exception.printStackTrace();
                    System.out.println(exception.getExceptionResponse());
                }
            } else {
                MedicalRecordCreateDTO newMedicalRecord = MedicalRecordCreateDTO.builder()
                        .patientId(patientId.getId())
                        .summary(summary)
                        .lstFileAttachId(lstFileAttachId)
                        .build();
                // API Call
                try {
                    result = medicalRecordAPIService.createMedicalRecord(newMedicalRecord);
                } catch (ApiResponseException exception) {
                    exception.printStackTrace();
                    System.out.println(exception.getExceptionResponse());
                }
            }

        } else {
            displayErrorAlert();
            return false;
        }
        return result;
    }

    @FXML
    public void UploadFile(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Chọn tệp");

        // Thêm các bộ lọc file (nếu cần)
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Tất cả các tệp", "*.*"),
                new FileChooser.ExtensionFilter("Hình ảnh", "*.jpg", "*.JPG", "*.png", "*.gif"),
                new FileChooser.ExtensionFilter("Tệp văn bản", "*.txt", "*.doc", "*.docx")
        );
        File seletedFile = fc.showOpenDialog(null);
        if (seletedFile != null) {
            medicalRecordFileName.setText(seletedFile.getName());
            Task<Void> uploadTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    Map<String, String> headers = new HashMap<>(); // Thêm các header cần thiết (nếu có)

                    // Kiểm tra phần mở rộng của tên tệp
                    String fileName = seletedFile.getName().toLowerCase();
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
                        // Đây là tệp hình ảnh
                        FileUploadResDTO response = fileUploadAPIService.uploadFileImage(seletedFile, headers);
                        if (response != null && response.getId() != null) {
                            lstFileAttachId.add(response.getId());
                        }
                    } else if (fileName.endsWith(".txt") || fileName.endsWith(".doc") || fileName.endsWith(".docx")) {
                        // Đây là tệp văn bản (doc hoặc docx)
                        FileUploadResDTO response = fileUploadAPIService.uploadFileDocument(seletedFile, headers);
                        if (response != null && response.getId() != null) {
                            lstFileAttachId.add(response.getId());
                        }
                    } else {
                        // Tệp không nằm trong các định dạng đã xác định
                        System.out.println("Tệp không được hỗ trợ.");
                    }

                    return null;
                }
            };


            uploadTask.setOnSucceeded(e -> {
//
            });

            Thread uploadThread = new Thread(uploadTask);
            uploadThread.setDaemon(true);
            uploadThread.start();

        } else {
            medicalRecordFileName.setText("");
        }
    }


    public Boolean submit() {
        return reloadRequired;
    }
}
