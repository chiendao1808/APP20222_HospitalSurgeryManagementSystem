package com.app20222.app20222_backend.dtos.users;

import com.app20222.app20222_backend.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface IGetListUser {

    Long getId();

    String getName();

    String getIdentificationNum();

    String getCode();

    String getIdentityType();

    String getTitle();

    String getAddress();

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    Date getBirthDate();

    String getPhoneNumber();

    String getEmail();

    String getDepartment();

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YY_HH_MM_SS, timezone = DateUtils.TIME_ZONE)
    Date getLastModifiedAt();

}
