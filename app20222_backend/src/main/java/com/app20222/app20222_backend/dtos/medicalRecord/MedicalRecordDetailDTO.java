package com.app20222.app20222_backend.dtos.medicalRecord;

import java.util.ArrayList;
import java.util.List;
import com.app20222.app20222_backend.dtos.fileAttach.IGetFileAttach;
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
public class MedicalRecordDetailDTO {

    IGetDetailMedicalRecord detailMedicalRecord;

    List<IGetFileAttach> lstMedicalRecordFile = new ArrayList<>();
}
