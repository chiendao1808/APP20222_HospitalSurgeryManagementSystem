package com.app20222.app20222_fxapp.dto.responses.patient;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientGetListDTO extends PatientGetListNewDTO {

    private Long patientId;
    private String identificationNumber;
    private String healthInsuranceNumber;
    private String firstName;
    private String lastName;
    private String email;

    @Override
    public String toString() {
        return "PatientGetListDTO{" +
                "patientId=" + patientId +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", healthInsuranceNumber='" + healthInsuranceNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday='" + getBirthDate() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", phoneNumber='" + getPhoneNumber() + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
