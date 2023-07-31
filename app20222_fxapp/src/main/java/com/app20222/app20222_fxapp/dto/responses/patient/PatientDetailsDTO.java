package com.app20222.app20222_fxapp.dto.responses.patient;

import java.util.Date;
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
public class PatientDetailsDTO {

    Long id;

    String firstName;

    String lastName;

    String name;

    String code;

    String identityType;

    Integer identityTypeVal;

    String identificationNumber;

    String healthInsuranceNumber;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    Date birthDate;

    String phoneNumber;

    String email;

    String address;

    String portraitPath;

}
