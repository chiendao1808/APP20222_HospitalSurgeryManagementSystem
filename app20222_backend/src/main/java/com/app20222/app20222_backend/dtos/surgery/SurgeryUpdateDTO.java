package com.app20222.app20222_backend.dtos.surgery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotNull;
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
public class SurgeryUpdateDTO {

    private String name;

    private String description;

    @NotNull
    String diseaseGroup;

    @NotNull
    private Integer type;

    @NotNull
    Integer status;

    String result;

    List<SurgeryRoleDTO> lstAssignment = new ArrayList<>();

    @NotNull
    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM, timezone = DateUtils.TIME_ZONE)
    Date startedAt;

    @NotNull
    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM, timezone = DateUtils.TIME_ZONE)
    Date estimatedEndAt;

    @NotNull
    Long surgeryRoomId;

}
