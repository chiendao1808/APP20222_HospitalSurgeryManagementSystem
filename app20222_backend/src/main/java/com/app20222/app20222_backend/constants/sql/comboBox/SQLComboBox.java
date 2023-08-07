package com.app20222.app20222_backend.constants.sql.comboBox;

public class SQLComboBox {

    /*
     ==== COMBO BOX USER ===
    */
    public static final String GET_COMBO_BOX_USER =
            "SELECT \n" +
                    "     id AS id, \n" +
                    "     CONCAT_WS(' ', last_name, first_name) AS name, \n" +
                    "     code AS code \n" +
                    "FROM {h-schema}users \n" +
                    "WHERE \n" +
                    "     :name = '' OR CONCAT_WS(' ', last_name, first_name) ILIKE '%' || :name || '%' ";
    /*
     ==== COMBO BOX ROLE =======
    */
    public static final String GET_COMBO_BOX_ROLE =
            "SELECT \n" +
                    "     id AS id, \n" +
                    "     displayed_name AS name, \n" +
                    "     code AS code \n" +
                    "FROM {h-schema}role \n";

    /*
     * ==== COMBO BOX PATIENT =======
     */
    public static final String GET_COMBO_BOX_PATIENT =
            "SELECT \n" +
                    "      id AS id, \n" +
                    "      CONCAT_WS(' ', last_name, first_name) AS name, \n" +
                    "      code AS code \n" +
                    "FROM {h-schema}patient \n" +
                    "WHERE \n" +
                    "     :patientName = '' OR CONCAT_WS(' ', last_name, first_name) ILIKE '%' || :patientName || '%' ";

    /*
     ==== COMBO BOX DEPARTMENT ===
    */
    public static final String GET_COMBO_BOX_DEPARTMENT =
            "SELECT \n" +
                    "     id AS id, \n" +
                    "     name AS name, \n" +
                    "     code AS code \n" +
                    "FROM {h-schema}department \n" +
                    "WHERE \n" +
                    "     :departmentName = '' OR name ILIKE '%' || :departmentName || '%' ";

    /*
     ==== COMBO BOX SURGERY ===
    */
    public static final String GET_LIST_SURGERY_ROLE =
            "SELECT DISTINCT\n" +
                    "     type AS type, \n" +
                    "     name AS name \n" +
                    "FROM {h-schema}surgery_role_type \n";

    public static final String GET_LIST_DISEASE_GROUP =
        "SELECT \n" +
            "     id AS id, \n" +
            "     name AS name, \n" +
            "     code AS code \n" +
            "FROM {h-schema}disease_group \n" +
            "WHERE \n" +
            "     :diseaseGroupName = '' OR name ILIKE '%' || :diseaseGroupName || '%' ";

    /*
     ==== COMBO BOX SURGERY ROOM ===
    */
    public static final String GET_COMBO_BOX_SURGERY_ROOM =
            "SELECT \n" +
                    "     id AS id, \n" +
                    "     name AS name, \n" +
                    "     code AS code \n" +
                    "FROM {h-schema}surgery_room \n" +
                    "WHERE \n" +
                    "     :surgeryRoomName = '' OR name ILIKE '%' || :surgeryRoomName || '%' ";

}
