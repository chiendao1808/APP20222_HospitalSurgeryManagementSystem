package com.app20222.app20222_fxapp.dto.responses.medicalRecord;

import java.util.ArrayList;
import java.util.List;
import com.app20222.app20222_fxapp.dto.file_attach.FileAttachDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicalRecordDetailsRes {

    MedicalRecordDetailsDTO detailMedicalRecord;

    List<FileAttachDTO> lstMedicalRecordFile = new ArrayList<>();

}
