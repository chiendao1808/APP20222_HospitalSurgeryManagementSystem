package com.app20222.app20222_backend.dtos.surgery;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import com.app20222.app20222_backend.entities.file_attach.FileAttach;
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

    Set<FileAttach> lstFileAttach= new HashSet<>();

    List<IGetSurgeryAssignment> lstAssignment = new ArrayList<>();


    public SurgeryDetailDTO(IGetDetailSurgery iGetDetailSurgery, Set<FileAttach> lstFileAttach, List<IGetSurgeryAssignment> lstAssignment){
        BeanUtils.copyProperties(iGetDetailSurgery, this);
        this.lstFileAttach = lstFileAttach;
        this.lstAssignment = lstAssignment;
    }

}
