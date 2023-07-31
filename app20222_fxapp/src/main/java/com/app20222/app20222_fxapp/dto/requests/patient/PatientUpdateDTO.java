package com.app20222.app20222_fxapp.dto.requests.patient;

import java.util.Date;
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
public class PatientUpdateDTO {

    @NotNull
    Long patientId;

    @NotNull
    String identificationNumber;

    @NotNull
    String healthInsuranceNumber;

    @NotNull
    IdentityTypeEnum identityType;

    @NotNull
    String firstName;

    @NotNull
    String lastName;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    @NotNull
    Date birthDate;

    String address;

    String phoneNumber;

    String email;

    String portraitPath;

}
