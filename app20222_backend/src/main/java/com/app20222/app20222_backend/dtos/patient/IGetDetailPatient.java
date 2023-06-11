package com.app20222.app20222_backend.dtos.patient;

import java.util.Date;
import com.app20222.app20222_backend.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

public interface IGetDetailPatient {

    Long getId();

    String getName();

    String getCode();

    String getIdentityType();

    Integer getIdentityTypeVal();

    String getIdentificationNumber();

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    Date getBirthDate();

    String getPhoneNumber();

    String getAddress();

    String getPortraitPath();

}