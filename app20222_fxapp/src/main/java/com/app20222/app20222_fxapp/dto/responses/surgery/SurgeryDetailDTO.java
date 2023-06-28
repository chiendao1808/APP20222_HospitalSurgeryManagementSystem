package com.app20222.app20222_fxapp.dto.responses.surgery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.app20222.app20222_fxapp.dto.file_attach.FileAttachDTO;
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
public class SurgeryDetailDTO {

    Long id;

    String code;

    String name;

    String diseaseGroupName;

    String description;

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

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM, timezone = DateUtils.TIME_ZONE)
    Date createdAt;

    String createdBy;

    List<FileAttachDTO> lstFileAttach = new ArrayList<>();

    List<SurgeryAssignmentDTO> lstAssignment = new ArrayList<>();


}
