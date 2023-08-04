package com.app20222.app20222_backend.constants.sql.surgeryRoom;

public class SQLSurgeryRoom {

    public static final String GET_LIST_SURGERY_ROOM =
        "SELECT \n" +
            "     id AS id, \n" +
            "     code AS code, \n" +
            "     name AS name, \n" +
            "     address AS address, \n" +
            "     description AS description, \n" +
            "     on_service_at AS onServiceAt, \n" +
            "     current_available AS currentAvailable \n" +
            "FROM {h-schema}surgery_room \n" +
            "WHERE \n" +
            "      (:id = -1 OR id = :id) AND \n" +
            "      (:code = '' OR code ILIKE ('%' || :code || '%')) AND \n" +
            "      (:name = '' OR name ILIKE ('%' || :name || '%')) AND \n" +
            "      current_available = :currentAvailable ";

}
