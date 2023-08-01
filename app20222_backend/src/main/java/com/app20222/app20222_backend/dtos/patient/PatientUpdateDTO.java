package com.app20222.app20222_backend.dtos.patient;

import java.util.Date;
import javax.validation.constraints.NotNull;
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
public class PatientUpdateDTO {

    Long patientId;

    @NotNull
    String identificationNumber;

    @NotNull
    IdentityTypeEnum identityType;

    @NotNull
    String firstName;

    @NotNull
    String lastName;

    @NotNull
    String healthInsuranceNumber;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    @NotNull
    Date birthDate;

    String address;

    String phoneNumber;

    String email;

    String portraitPath;
}
