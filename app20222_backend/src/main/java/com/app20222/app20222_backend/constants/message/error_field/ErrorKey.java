package com.app20222.app20222_backend.constants.message.error_field;

public class ErrorKey {


    public static class Surgery {
        // error code
        public static final String NOT_FOUND_ERROR_CODE = "surgery.error.not_found";

        public static final String EXISTED_ERROR_CODE = "surgery.error.existed";

        public static final String OVERLAPPED_ERROR_CODE = "surgery.error.overlapped";

        public static final String PERMISSION_DENIED_ERROR_CODE = "surgery.error.permission_denied";


        // error key;
        public static final String ID = "id";

    }

    public static class Patient {

        //error code
        public static final String NOT_FOUND_ERROR_CODE = "patient.error.not_found";
        public static final String EXISTED_ERROR_CODE = "patient.error.existed";

        public static final String DUPLICATED_ERROR_CODE = "patient.error.duplicated";

        public static final String PERMISSION_DENIED_ERROR_CODE = "patient.error.permission_denied";

        // error key

        public static final String ID = "id";

        public static final String IDENTIFICATION_NUMBER = "identification_number";

        public static final String CODE = "code";

    }


}
