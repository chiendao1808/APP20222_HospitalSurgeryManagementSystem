package com.app20222.app20222_fxapp.dto.responses.users;

import com.app20222.app20222_fxapp.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserListDTO {

    Long id;

    String name;

    String identificationNum;

    String identityType;

    String address;

    String code;

    String title;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    Date birthDate;

    String phoneNumber;

    String email;

    String department;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YY_HH_MM_SS, timezone = DateUtils.TIME_ZONE)
    Date lastModifiedAt;
}
