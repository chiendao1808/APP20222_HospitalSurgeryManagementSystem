package com.app20222.app20222_backend.dtos.surgery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
import com.app20222.app20222_backend.enums.surgery.SurgeryStatusEnum;
import com.app20222.app20222_backend.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class SurgeryCreateDTO {

    @NotNull
    String name;

    String description;

    @NotNull
    String diseaseGroup;

    @NotNull
    Integer type;

    @NotNull
    Long patientId;

    List<SurgeryRoleDTO> lstAssignment = new ArrayList<>();

    @NotNull
    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM, timezone = DateUtils.TIME_ZONE)
    Date startedAt;

    @NotNull
    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM, timezone = DateUtils.TIME_ZONE)
    Date estimatedEndAt;

    Long surgeryRoomId;

    Integer status = SurgeryStatusEnum.REMAINING.getValue();
}
