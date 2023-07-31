package com.app20222.app20222_backend.constants.sql.department;

public class SQLDepartment {

    public static final String GET_LIST_DEPARTMENT =
        "SELECT \n" +
            "      id AS id, \n" +
            "      code AS code, \n" +
            "      name AS name, \n" +
            "      phone_number AS phoneNumber, \n" +
            "      email AS email, \n" +
            "      description AS description, \n" +
            "      logo_path AS logoPath, \n" +
            "      address AS address \n" +
            "FROM {h-schema}department \n" +
            "WHERE \n" +
            "      (:id = -1 OR :id = id) AND \n" +
            "      (:code = '' OR :code = code) AND \n" +
            "      (:name = '' OR :name = name) AND \n" +
            "      (:phone = '' OR :phone = phone_number) AND \n" +
            "      (:email = '' OR :email = email) ";

}
