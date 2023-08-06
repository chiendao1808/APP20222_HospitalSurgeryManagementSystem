package com.app20222.app20222_fxapp.app_controllers.medicalRecord_view;

import com.app20222.app20222_fxapp.dto.file_attach.FileAttachDTO;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordDetailsDTO;
import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordDetailsRes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

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


    public void downloadFile(String fileUrl, String fileName) {
        try {
            URL url = new URL(fileUrl);
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(fileName);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();
            System.out.println("File đã được tải xuống thành công!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleDownload(ActionEvent event) {
        List<FileAttachDTO> fileList = medicalRecordDetailsRes.getLstMedicalRecordFile();
        if (fileList != null && !fileList.isEmpty()) {
            FileAttachDTO fileToDownload = fileList.get(0); // Assuming you want to download the first file in the list.
            String fileUrl = fileToDownload.getLocation();
            String suggestedFileName = fileToDownload.getFileName();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName(suggestedFileName);

            File selectedFile = fileChooser.showSaveDialog(detailMedicalRecordPane.getScene().getWindow());

            if (selectedFile != null) {
                String fileName = selectedFile.getAbsolutePath();
                downloadFile(fileUrl, fileName);
            }
        }

    }


}
