package com.app20222.app20222_fxapp.app_controllers.medicalRecord_view;

import com.app20222.app20222_fxapp.dto.common.CommonIdCodeName;
import com.app20222.app20222_fxapp.dto.responses.FileUploadResDTO;
import com.app20222.app20222_fxapp.dto.responses.exception.ExceptionResponse;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordListDTO;
import com.app20222.app20222_fxapp.enums.apis.APIDetails;
import com.app20222.app20222_fxapp.exceptions.apiException.ApiResponseException;
import com.app20222.app20222_fxapp.services.comboBox.ComboBoxAPIService;
import com.app20222.app20222_fxapp.services.fileUpload.FileUploadAPIService;
import com.app20222.app20222_fxapp.utils.apiUtils.ApiUtils;
import com.app20222.app20222_fxapp.utils.httpUtils.HttpUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.http.HttpResponse;
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

    private ComboBoxAPIService comboBoxAPIService;

    private FileUploadAPIService fileUploadAPIService;

    private Boolean reloadRequired = false;

    private String messageError;

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public String getMessageError() {
        return messageError;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxAPIService = new ComboBoxAPIService();
        fileUploadAPIService = new FileUploadAPIService();
        setupButtonEventFilters();
        setUpMedicalRecordPatientId();
    }
    private void setUpMedicalRecordPatientId(){
        ObservableList<CommonIdCodeName> listPatientId = getDataFromDataSource();
        System.out.println("listPatientId "+ listPatientId);
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
            String fileName = medicalRecordFileName.getText();
            System.out.println("patientId: " + patientId);
            System.out.println("summary: " + summary);
            System.out.println("fileName: " + fileName);
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
                new FileChooser.ExtensionFilter("Hình ảnh", "*.jpg", "*.png", "*.gif"),
                new FileChooser.ExtensionFilter("Tệp văn bản", "*.txt", "*.doc", "*.docx")
        );
        File seletedFile = fc.showOpenDialog(null);
        if (seletedFile != null) {
            medicalRecordFileName.setText(seletedFile.getName());
            // Gọi phương thức doUploadFile với tệp đã chọn
            Map<String, String> headers = new HashMap<>(); // Thêm các header cần thiết (nếu có)

            String uri = ApiUtils.buildURI(APIDetails.UPLOAD_DOCUMENT.getRequestPath() + APIDetails.UPLOAD_DOCUMENT.getDetailPath(), new HashMap<>());
            System.out.println("uri" + uri);
            FileUploadResDTO response = fileUploadAPIService.uploadFileDocument(seletedFile, headers);
            System.out.println("response"+ response);
        } else {
            medicalRecordFileName.setText("");
        }
    }



    public Boolean submit() {
        return reloadRequired;
    }
}
