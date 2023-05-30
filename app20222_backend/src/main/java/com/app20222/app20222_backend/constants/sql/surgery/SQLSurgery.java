package com.app20222.app20222_backend.constants.sql.surgery;

public class SQLSurgery {

    public static final String GET_LIST_VIEWABLE_SURGERY_ID_BY_DEPARTMENT =
        "SELECT " +
            "   DISTINCT surgery.id  \n" +
            "FROM {h-schema}surgery \n" +
            "   JOIN {h-schema}users_surgeries uSurgery ON uSurgery.surgery_id = surgery.id \n" +
            "   JOIN {h-schema}users ON users.id = uSurgery.user_id AND users.department_id = :departmentId \n" +
            "WHERE surgery.created_by = :createdBy ";

    public static final String GET_LIST_VIEWABLE_SURGERY_ID_BY_USER =
        "SELECT " +
            "   DISTINCT surgery.id  \n" +
            "FROM {h-schema}surgery \n" +
            "   JOIN {h-schema}users_surgeries uSurgery ON uSurgery.surgery_id = surgery.id AND uSurgery.user_id = :userId \n";

    public static final String GET_OVERLAP_SURGERY =
        "SELECT DISTINCT " +
            "     surgery.id AS surgeryId, \n" +
            "     surgery.surgery_room_id AS surgeryRoomId, \n" +
            "     surgery.patient_id AS patientId, \n" +
            "     '{' || STRING.AGG(uSurgery.surgery_id::::TEXT, ',') || '}' AS lstAssigneeId \n" +
            "FROM {h-schema}surgery \n" +
            "   JOIN {h-schema}users_surgeries uSurgery \n" +
            "WHERE " +
            "      surgery.status IN (0,1) AND \n" +
            "      ( \n" +
            "           (:startedTime BETWEEN surgery.started_at AND surgery.estimated_end_at) \n" +
            "            OR (:estimatedEndTime BETWEEN surgery.started_at AND surgery.estimated_end_at)  \n" +
            "      )";
}
