package com.app20222.app20222_fxapp.app_controllers.medicalRecord_view;

import com.app20222.app20222_fxapp.dto.responses.medicalRecord.MedicalRecordGetListDTO;
import com.app20222.app20222_fxapp.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MedicalRecordController {

    // Hồ sơ bệnh án
    @FXML
    private TableView<MedicalRecordGetListDTO> medicalRecordTable;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, String> medicalRecordAction;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, Date> medicalRecordCreateAt;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, Long> medicalRecordCreatedById;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, String> medicalRecordCreatedByName;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, String> medicalRecordPatientCode;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, Long> medicalRecordPatientID;

    @FXML
    private TableColumn<MedicalRecordGetListDTO, String> medicalRecordPatientName;

    @FXML
    private Button createMedicalRecord;
}
