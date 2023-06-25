package com.app20222.app20222_backend.dtos.medical_record;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
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
public class MedicalRecordCreateDTO {

    @NotNull
    Long patientId;

    String summary;

    Set<Long> lstFileAttachId = new HashSet<>();

}
