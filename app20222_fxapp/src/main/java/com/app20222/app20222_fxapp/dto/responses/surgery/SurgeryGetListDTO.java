package com.app20222.app20222_fxapp.dto.responses.surgery;

import java.util.Date;
import com.app20222.app20222_fxapp.utils.DateUtils;
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
public class SurgeryGetListDTO {

    Long id;

    String code;

    String name;

    String diseaseGroupName;

    String type;

    String patientName;

    String surgeryRoom;

    String status;

    String result;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM, timezone = DateUtils.TIME_ZONE)
    Date startedAt;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM, timezone = DateUtils.TIME_ZONE)
    Date estimatedEndAt;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM, timezone = DateUtils.TIME_ZONE)
    Date endedAt;

}
