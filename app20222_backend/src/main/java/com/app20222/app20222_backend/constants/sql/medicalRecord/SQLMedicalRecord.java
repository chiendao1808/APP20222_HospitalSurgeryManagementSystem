package com.app20222.app20222_backend.constants.sql.medicalRecord;

public class SQLMedicalRecord {

    public static final String GET_LIST_MEDICAL_RECORD =
        "SELECT \n" +
            "      medRecord.id AS id, \n" +
            "      medRecord.patient_id AS patientId, \n" +
            "      CONCAT_WS(' ', patient.last_name, patient.first_name) AS patientName, \n" +
            "      patient.code AS patientCode, \n" +
            "      medRecord.created_at AS createdAt, \n" +
            "      users.id AS createdById, \n" +
            "      CONCAT_WS(' ', users.last_name, users.first_name) AS createdByName " +
            "FROM {h-schema}medical_record medRecord \n" +
            "       LEFT JOIN {h-schema}patient ON patient.id = medRecord.patient_id \n" +
            "       LEFT JOIN {h-schema}users ON users.id = medRecord.created_by \n" +
            "WHERE \n" +
            "       (:patientId = -1 OR medRecord.patient_id = :patientId) AND \n" +
            "       (:patientName = '' OR CONCAT_WS(' ', patient.last_name, patient.first_name) ILIKE '%' || :patientName || '%') AND \n" +
            "       (:patientCode = '' OR patient.code ILIKE '%' || :patientCode || '%') AND \n" +
            "       (:phoneNumber = '' OR patient.phone_number ILIKE '%' || :phoneNumber || '%') AND \n" +
            "       (:idType = -1  OR patient.identity_type = :idType) AND \n"+
            "       (:idNumber = '' OR patient.identification_number = :idNumber) AND \n" +
            "       (:email = '' OR patient.email ILIKE '%' || :email || '%') AND \n" +
            "       (:startDate = DATE('1970-01-01') OR :startDate >= DATE(medRecord.created_at)) AND \n" +
            "       (:endDate = DATE('1970-01-01') OR :endDate <= DATE(medRecord.created_at)) ";


    public static final String GET_DETAIL_MEDICAL_RECORD =
        "SELECT \n" +
            "      medRecord.id AS id, \n" +
            "      medRecord.summary AS summary, \n" +
            "      medRecord.patient_id AS patientId, \n" +
            "      CONCAT_WS(' ', patient.last_name, patient.first_name) AS patientName, \n" +
            "      patient.code AS patientCode, \n" +
            "      medRecord.created_at AS createdAt, \n" +
            "      users.id AS createdById, \n" +
            "      CONCAT_WS(' ', users.last_name, users.first_name) AS createdByName \n" +
            "FROM {h-schema}medical_record medRecord \n" +
            "       LEFT JOIN {h-schema}patient ON patient.id = medRecord.patient_id \n" +
            "       LEFT JOIN {h-schema}users ON users.id = medRecord.created_by \n" +
            "WHERE \n" +
            "       medRecord.id = :medicalRecordId \n";

    public static final String GET_LIST_MEDICAL_RECORD_FILE =
        "SELECT \n" +
            "     file.id AS fileId, \n" +
            "     file.name AS fileName, \n" +
            "     file.location AS location," +
            "     file.type AS type \n" +
            "FROM {h-schema}medical_records_files AS medRecordFile \n" +
            "   JOIN {h-schema}file_attach AS file ON file.id = medRecordFile.file_id \n" +
            "WHERE medRecordFile.medical_record_id = :medicalRecordId ";


}
