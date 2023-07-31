package com.app20222.app20222_fxapp.dto.requests.users;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import com.app20222.app20222_fxapp.enums.users.IdentityTypeEnum;
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
public class UserUpdateDTO {

    @NotNull
    IdentityTypeEnum identityType;

    @NotNull
    String identificationNumber;

    String avatarPath;

    @NotNull
    String firstName;

    @NotNull
    String lastName;

    String title;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    @NotNull
    Date birthDate;

    String address;

    @NotNull
    String phoneNumber;

    String email;

    Set<Long> lstRoleId = new HashSet<>();

    @NotNull
    Long departmentId;

}
