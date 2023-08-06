package com.app20222.app20222_fxapp.app_controllers.medicalRecord_view;

import com.app20222.app20222_fxapp.constants.apis.ApiConstants;
import com.app20222.app20222_fxapp.constants.fileAttach.FileAttachConstants;
import com.app20222.app20222_fxapp.dto.file_attach.FileAttachDTO;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordDetailsDTO;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordDetailsRes;
import com.app20222.app20222_fxapp.enums.fileAttach.FileTypeEnum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import org.apache.commons.io.FileUtils;

public class DetailsMedicalRecordController {
    @FXML
    private DialogPane detailMedicalRecordPane;

    @FXML
    private Text medicalRecordCodeDetail;

    @FXML
    private Text medicalRecordCreateAtDetail;


    @FXML
    private Text medicalRecordCreateNameDetail;

    @FXML
    private Button medicalRecordFileDetail;

    @FXML
    private Text medicalRecordNameDetail;

    @FXML
    private Text medicalRecordSummaryDetail;

    @FXML
    private Text medicalRecordFileNameDetail;

    private static final String filePathSuffix = ApiConstants.HTTP_SCHEME + "://" + ApiConstants.API_LOCAL_IP + ":" + ApiConstants.SERVER_PORT + ApiConstants.DEFAULT_API_PATH;


    private MedicalRecordDetailsRes medicalRecordDetailsRes;

    public void setMedicalRecordDetailsRes(MedicalRecordDetailsRes medicalRecordDetailsRes) {
        this.medicalRecordDetailsRes = medicalRecordDetailsRes;
        MedicalRecordDetailsDTO detailsDTO = medicalRecordDetailsRes.getDetailMedicalRecord();
        if (detailsDTO != null) {
            medicalRecordCodeDetail.setText(detailsDTO.getPatientCode());
            medicalRecordNameDetail.setText(detailsDTO.getPatientName());
            medicalRecordCreateAtDetail.setText(String.valueOf(detailsDTO.getCreatedAt()));
            medicalRecordCreateNameDetail.setText(detailsDTO.getCreatedByName());
            medicalRecordSummaryDetail.setText(detailsDTO.getSummary());
        }

        List<FileAttachDTO> fileList = medicalRecordDetailsRes.getLstMedicalRecordFile();
        if (fileList != null && !fileList.isEmpty()) {
            for (FileAttachDTO file : fileList) {
                medicalRecordFileNameDetail.setText(file.getFileName());
            }
        } else {
            medicalRecordFileNameDetail.setText("File không tồn tại");
        }

        // ẩn 2 button
        detailMedicalRecordPane.getButtonTypes().clear();
    }


    // Xử lý download file
    public void downloadFile(String fileUrl, String filePath) {
        try {
            FileUtils.copyURLToFile(new URL(fileUrl), new File(filePath), 10000, 10000);
            System.out.println("File đã được tải xuống thành công!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDownload(ActionEvent event) {
        List<FileAttachDTO> fileList = medicalRecordDetailsRes.getLstMedicalRecordFile();
        if (fileList != null && !fileList.isEmpty()) {
            for (FileAttachDTO file : fileList) {
                String fileUrl = Objects.equals(file.getType(), FileTypeEnum.DOCUMENT.getType()) ? filePathSuffix + file.getLocation() : file.getLocation();
                String selectedFileName = file.getFileName();
                String extension = com.app20222.app20222_fxapp.utils.fileUtils.FileUtils.getFileExtension(selectedFileName);
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(FileAttachConstants.DEFAULT_DOWNLOAD_FOLDER));
                fileChooser.setTitle(file.getFileName());
                fileChooser.setInitialFileName(file.getFileName());
                fileChooser.getExtensionFilters().addAll(FileAttachConstants.lstExtensionFilters);
                FileChooser.ExtensionFilter extensionFilter = FileAttachConstants.mapExtensionFilters.get(extension);
                if(Objects.nonNull(extensionFilter)){
                    fileChooser.setSelectedExtensionFilter(extensionFilter);
                }
                // Show save file window dialog
                File selectedFile = fileChooser.showSaveDialog(detailMedicalRecordPane.getScene().getWindow());

                if (selectedFile != null) {
                    String fileName = selectedFile.getAbsolutePath();
                    downloadFile(fileUrl, fileName);
                }
            }
        }

    }


}
