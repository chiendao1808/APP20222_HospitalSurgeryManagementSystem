package com.app20222.app20222_backend.dtos.users;

import java.util.Date;
import org.springframework.beans.BeanUtils;
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
public class ProfileUserDTO {

    Long id;

    String name;

    String code;

    String identificationNum;

    String identityType;

    String title;

    String address;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    Date birthDate;

    String phoneNumber;

    String email;

    String userName;

    String role;

    String department;

    public ProfileUserDTO(IGetDetailUser iGetDetailUser) {
        BeanUtils.copyProperties(iGetDetailUser, this);
    }

}
