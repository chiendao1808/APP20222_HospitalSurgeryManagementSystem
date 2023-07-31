package com.app20222.app20222_fxapp.dto.responses.medicalRecord;

import com.app20222.app20222_fxapp.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicalRecordGetListDTO {

    @NotNull
    Long id;
    @NotNull
    Long patientId;
    @NotNull
    String patientName;
    @NotNull
    String patientCode;
    @NotNull
    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    Date createdAt;
    @NotNull
    Long createdById;
    @NotNull
    String createdByName;
}
