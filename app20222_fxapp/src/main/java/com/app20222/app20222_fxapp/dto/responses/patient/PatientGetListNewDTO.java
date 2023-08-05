package com.app20222.app20222_fxapp.dto.responses.patient;

import java.util.Date;
import com.app20222.app20222_fxapp.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientGetListNewDTO {

    Long id;

    String name;

    String code;

    String identityTypeVal;

    String identityType;

    String identificationNumber;

    String healthInsuranceNumber;

    @JsonFormat(pattern = DateUtils.FORMAT_DATE_DD_MM_YYYY_SLASH, timezone = DateUtils.TIME_ZONE)
    Date birthDate;

    String phoneNumber;

    String address;

}
