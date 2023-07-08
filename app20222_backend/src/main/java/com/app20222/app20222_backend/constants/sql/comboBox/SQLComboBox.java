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
                    "FROM {h-schema}users \n";
    /*
     ==== COMBO BOX ROLE =======
    */
    public static final String GET_COMBO_BOX_ROLE =
            "SELECT \n" +
                    "     id AS id, \n" +
                    "     name AS name, \n" +
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
                    "FROM {h-schema}patient \n";

    /*
     ==== COMBO BOX DEPARTMENT ===
    */
    public static final String GET_COMBO_BOX_DEPARTMENT =
            "SELECT \n" +
                    "     id AS id, \n" +
                    "     name AS name, \n" +
                    "     code AS code \n" +
                    "FROM {h-schema}department \n";

    /*
     ==== COMBO BOX SURGERY ===
    */
    public static final String GET_LIST_SURGERY_ROLE =
            "SELECT DISTINCT\n" +
                    "     type AS type, \n" +
                    "     name AS name \n" +
                    "FROM {h-schema}surgery_role_type \n";

    /*
     ==== COMBO BOX SURGERY ROOM ===
    */
    public static final String GET_COMBO_BOX_SURGERY_ROOM =
            "SELECT \n" +
                    "     id AS id, \n" +
                    "     name AS name, \n" +
                    "     code AS code \n" +
                    "FROM {h-schema}surgery_room \n";

}
