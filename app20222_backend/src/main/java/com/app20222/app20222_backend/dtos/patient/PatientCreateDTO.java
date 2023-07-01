package com.app20222.app20222_backend.dtos.patient;

import java.util.Date;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.app20222.app20222_backend.enums.users.IdentityTypeEnum;
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
public class PatientCreateDTO {

    @NotNull
    String identificationNumber;

    @NotNull
    IdentityTypeEnum identityType;

    @NotNull
    String firstName;

    @NotNull
    String lastName;

    @NotNull
    @Size(min = 5, max = 15)
    String healthInsuranceNumber;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    @NotNull
    Date birthDate;

    String address;

    @NotNull
    String phoneNumber;

    @NotNull
    String email;

}
