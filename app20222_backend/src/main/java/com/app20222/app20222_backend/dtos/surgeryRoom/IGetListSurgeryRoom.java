package com.app20222.app20222_backend.dtos.surgery_room;

import java.util.Date;
import com.app20222.app20222_backend.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

public interface IGetListSurgeryRoom {

    Long getId();

    String getName();

    String getCode();

    String getAddress();

    String getDescription();

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    Date getOnServiceAt();

    Boolean getCurrentAvailable();

}
