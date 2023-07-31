package com.app20222.app20222_fxapp.utils;

import java.time.LocalDate;

public class Validation {
    public static boolean isAllFieldsFilledPatient(String identificationNumber, String firstName, String lastName, String identityType,
                                            String healthInsuranceNumber, LocalDate birthDate, String address, String phoneNumber, String email) {
        return !identificationNumber.isEmpty() &&
                !firstName.isEmpty() &&
                !lastName.isEmpty() &&
                identityType != null &&
                !healthInsuranceNumber.isEmpty() &&
                birthDate != null &&
                !address.isEmpty() &&
                !phoneNumber.isEmpty() &&
                !email.isEmpty();
    }
    public static boolean isValidEmail(String email) {
        // Kiểm tra tính hợp lệ của email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Kiểm tra tính hợp lệ của số điện thoại
        String phoneRegex = "^[0-9]{10}$";
        return phoneNumber.matches(phoneRegex);
    }
}
