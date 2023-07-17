package com.app20222.app20222_fxapp.dto.responses.patient;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

public class PatientGetListDTO {

    private Long patientId;

    private String identificationNumber;

    private String healthInsuranceNumber;

    private String firstName;

    private  String lastName;

    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

    private String email;

    public PatientGetListDTO() {}
    public PatientGetListDTO(Long patientId, String identificationNumber, String healthInsuranceNumber, String firstName, String lastName, LocalDate birthDate, String address,String phoneNumber, String email){
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void setHealthInsuranceNumber(String healthInsuranceNumber) {
        this.healthInsuranceNumber = healthInsuranceNumber;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Long getPatientId() {
        return patientId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getNamePatient() {
        return firstName + " " + lastName;
    }
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public String getHealthInsuranceNumber() {
        return healthInsuranceNumber;
    }

    public String getLastName() {
        return lastName;
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
