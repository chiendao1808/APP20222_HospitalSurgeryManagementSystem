package com.app20222.app20222_backend.dtos.surgery;

import java.util.Date;
import com.app20222.app20222_backend.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

public interface IGetListSurgery {

    Long getId();

    String getCode();

    String getName();

    String getDiseaseGroupName();

    String getType();

    String getPatientName();

    String getSurgeryRoom();

    String getStatus();

    String getResult();

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM, timezone = DateUtils.TIME_ZONE)
    Date getStartedAt();

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM, timezone = DateUtils.TIME_ZONE)
    Date getEstimatedEndAt();

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_HH_MM, timezone = DateUtils.TIME_ZONE)
    Date getEndedAt();

}
