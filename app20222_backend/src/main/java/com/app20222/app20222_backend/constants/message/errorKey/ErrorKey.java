package com.app20222.app20222_backend.constants.message.errorKey;

public class ErrorKey {


    public static class Surgery {
        // error code
        public static final String NOT_FOUND_ERROR_CODE = "surgery.error.not.found";

        public static final String EXISTED_ERROR_CODE = "surgery.error.existed";

        public static final String OVERLAPPED_ERROR_CODE = "surgery.error.overlapped";

        public static final String PERMISSION_DENIED_ERROR_CODE = "surgery.error.permission.denied";


        // error key;
        public static final String ID = "id";

    }

    public static class Patient {

        //error code
        public static final String NOT_FOUND_ERROR_CODE = "patient.error.not.found";
        public static final String EXISTED_ERROR_CODE = "patient.error.existed";

        public static final String DUPLICATED_ERROR_CODE = "patient.error.duplicated";

        public static final String PERMISSION_DENIED_ERROR_CODE = "patient.error.permission.denied";

        // error key

        public static final String ID = "id";

        public static final String IDENTIFICATION_NUMBER = "identification_number";

        public static final String HEALTH_INSURANCE_NUMBER = "health_insurance_number";

        public static final String CODE = "code";

    }

    public static class User {
        //error code
        public static final String NOT_FOUND_ERROR_CODE = "user.error.not.found";
        public static final String EXISTED_ERROR_CODE = "user.error.existed";

        public static final String DUPLICATED_ERROR_CODE = "user.error.duplicated";

        public static final String PERMISSION_DENIED_ERROR_CODE = "user.error.permission.denied";

        public static final String BAD_CREDENTIALS_ERROR_CODE = "user.error.bad.credentials";

        // error key

        public static final String ID = "id";

        public static final String IDENTIFICATION_NUMBER = "identification_number";

        public static final String CODE = "code";

        public static final String USERNAME = "username";

        public static final String PASSWORD = "password";
    }

    public static class Department {
        //error code
        public static final String NOT_FOUND_ERROR_CODE = "department.error.not.found";
        public static final String EXISTED_ERROR_CODE = "department.error.existed";

        public static final String DUPLICATED_ERROR_CODE = "department.error.duplicated";

        public static final String PERMISSION_DENIED_ERROR_CODE = "department.error.permission.denied";

        // error key

        public static final String ID = "id";

        public static final String CODE = "code";
    }

    public static class SurgeryRoom {
        //error code
        public static final String NOT_FOUND_ERROR_CODE = "surgery_room.error.not.found";
        public static final String EXISTED_ERROR_CODE = "surgery_room.error.existed";

        public static final String DUPLICATED_ERROR_CODE = "surgery_room.error.duplicated";

        public static final String PERMISSION_DENIED_ERROR_CODE = "surgery_room.error.permission.denied";

        // error key
        public static final String ID = "id";

        public static final String CODE = "code";
    }

    public static class FileAttach {
        //error code
        public static final String UPLOAD_ERROR_CODE = "file_attach.error.upload";
        public static final String NOT_FOUND_ERROR_CODE = "file_attach.error.not.found";

        public static final String EXISTED_ERROR_CODE = "file_attach.error.existed";

        public static final String DUPLICATED_ERROR_CODE = "file_attach.error.duplicated";

        public static final String PERMISSION_DENIED_ERROR_CODE = "file_attach.error.permission.denied";

        // error key
        public static final String ID = "id";
    }

    public static class MedicalRecord {
        //error code
        public static final String NOT_FOUND_ERROR_CODE = "medical.record.error.not.found";

        public static final String EXISTED_ERROR_CODE = "medical.record.error.existed";

        public static final String DUPLICATED_ERROR_CODE = "medical.record.error.duplicated";

        public static final String PERMISSION_DENIED_ERROR_CODE = "medical.record.error.permission.denied";

        // error key
        public static final String ID = "id";
    }


}
