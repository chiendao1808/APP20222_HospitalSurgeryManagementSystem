package com.app20222.app20222_backend.dtos.users;

import java.util.Date;
import com.app20222.app20222_backend.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

public interface IGetDetailUser {

    Long getId();

    String getName();

    String getCode();

    String getIdentificationNum();

    String getIdentityType();

    String getTitle();

    String getAddress();


    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    Date getBirthDate();

    String getPhoneNumber();

    String getEmail();

    String getUserName();

    String getRole();

    String getDepartment();

}
