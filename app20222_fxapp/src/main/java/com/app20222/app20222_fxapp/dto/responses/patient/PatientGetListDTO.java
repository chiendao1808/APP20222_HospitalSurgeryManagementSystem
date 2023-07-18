package com.app20222.app20222_fxapp.dto.responses.patient;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

@Data
public class PatientGetListDTO {

    private Long patientId;

    private String identificationNumber;

    private String healthInsuranceNumber;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

    private String email;

    public PatientGetListDTO() {
    }

    public PatientGetListDTO(Long patientId, String identificationNumber, String healthInsuranceNumber, String firstName, String lastName,
        LocalDate birthDate, String address, String phoneNumber, String email) {
        this.patientId = patientId;
        this.identificationNumber = identificationNumber;
        this.healthInsuranceNumber = healthInsuranceNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getNamePatient() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "PatientGetListDTO{" +
            "patientId=" + patientId +
            ", identificationNumber='" + identificationNumber + '\'' +
            ", healthInsuranceNumber='" + healthInsuranceNumber + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", birthDate=" + birthDate +
            ", address='" + address + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
