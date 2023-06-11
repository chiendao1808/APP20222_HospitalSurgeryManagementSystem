package com.app20222.app20222_backend.constants.sql.patient;

public class SQLPatient {


    public static final String GET_LIST_PATIENT =
        "SELECT \n" +
            "      patient.code AS code, \n" +
            "      patient.identity_type AS identityTypeVal, \n" +
            "      CASE \n" +
            "           WHEN patient.identity_type = 0 THEN 'Chứng minh thư nhân dân' \n" +
            "           WHEN patient.identity_type = 1 THEN 'Căn cước công dân' \n" +
            "           WHEN patient.identity_type = 2 THEN 'Hộ chiếu' \n" +
            "           ELSE '' \n" +
            "      END AS identityType, \n" +
            "      patient.identification_number AS identificationNumber, \n" +
            "      patient.portrait_path AS portraitPath, \n" +
            "      CONCAT_WS(' ', patient.last_name, patient.first_name) AS name, \n" +
            "      patient.birth_date AS birthDate, \n" +
            "      patient.address AS address, \n" +
            "      patient.phone_number AS phoneNumber, \n" +
            "      patient.email AS email \n" +
            "FROM {h-schema}patient \n" +
            "WHERE \n" +
            "      (-1 = :patientId OR patient.id = :patientId) AND \n" +
            "      ('' = :code OR patient.code ILIKE '%' || :code || '%') AND \n" +
            "      ('' = :identityType OR patient.identity_type = :identityType) AND \n" +
            "      ('' = :identificationNum OR patient.identification_number ILIKE '%' || :identificationNum || '%') AND \n" +
            "      ('' = :name OR CONCAT_WS(' ', patient.last_name, patient.first_name) ILIKE '%' || :name || '%') AND \n" +
            "      ('' = :phoneNumber OR patient.phone_number ILIKE '%' || :phoneNumber || '%') AND \n" +
            "      ('' = :email OR patient.code ILIKE '%' || :email || '%')";

    public static final String GET_DETAIL_PATIENT =
        "SELECT \n" +
            "      patient.code AS code, \n" +
            "      patient.identity_type AS identityTypeVal, \n" +
            "      CASE \n" +
            "           WHEN patient.identity_type = 0 THEN 'Chứng minh thư nhân dân' \n" +
            "           WHEN patient.identity_type = 1 THEN 'Căn cước công dân' \n" +
            "           WHEN patient.identity_type = 2 THEN 'Hộ chiếu' \n" +
            "           ELSE '' \n" +
            "      END AS identityType, \n" +
            "      patient.identification_number AS identificationNumber, \n" +
            "      patient.portrait_path AS portraitPath, \n" +
            "      CONCAT_WS(' ', patient.last_name, patient.first_name) AS name, \n" +
            "      patient.birth_date AS birthDate, \n" +
            "      patient.address AS address, \n" +
            "      patient.phone_number AS phoneNumber, \n" +
            "      patient.email AS email \n" +
            "FROM {h-schema}patient \n" +
            "WHERE patient.id = :id \n";

}
