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

    public static final String GET_LIST_SURGERY =
        "SELECT  \n" +
            "     surgery.id AS id, \n" +
            "     surgery.name AS name, \n" +
            "     diseaseGroup.name AS diseaseGroupName, \n" +
            "     '' AS type, \n" +
            "     CONCAT_WS(' ', patient.last_name, patient.first_name), \n" +
            "     sRoom.name AS surgeryRoomName, \n" +
            "     CASE \n" +
            "           WHEN surgery.status = 0 THEN 'Chờ thực hiện' \n" +
            "           WHEN surgery.status = 1 THEN 'Đang được thực hiện' \n" +
            "           WHEN surgery.status = 2 THEN 'Đã được thực hiện' \n" +
            "           WHEN surgery.status = 3 THEN 'Đã hủy' \n" +
            "           ELSE '' \n" +
            "     END AS status, \n" +
            "     surgery.started_at AS startedAt, \n" +
            "     surgery.estimated_end_at AS estimatedEndAt, \n" +
            "     surgery.end_at AS endAt, \n" +
            "     surgery.result AS result \n" +
            "FROM {h-schema}surgery \n" +
            "   LEFT JOIN {h-schema}patient ON patient.id = surgery.patient_id \n" +
            "   LEFT JOIN {h-schema}surgery_room AS sRoom ON sRoom.id = surgery.surgery_room_id \n" +
            "   LEFT JOIN {h-schema}disease_group AS diseaseGroup ON diseaseGroup.id = surgery.disease_group_id \n" +
            "WHERE \n" +
            "     (surgery.id IN (:lstViewableSurgeryId)) AND \n" +
            "     (:surgeryName = '' OR surgery.name ILIKE '%' || :surgeryName || '%') AND \n" +
            "     (:patientId = -1 OR :patientId = surgery.patient_id) AND \n" +
            "     (:diseaseGroupId = -1 OR :diseaseGroupId = surgery.disease_group_id) AND \n" +
            "     (:surgeryRoomId = -1 OR :surgeryRoomId = surgery.surgery_room_id) AND \n" +
            "     (:status = -1 OR :status = surgery.status) AND \n " +
            "     (:startedAt = CAST('1970-01-01 00:00' AS TIMESTAMP) OR :startedAt >= CAST(surgery.started_at AS TIMESTAMP)) AND \n" +
            "     (:estimatedEndAt = CAST('1970-01-01 00:00' AS TIMESTAMP) OR :estimatedEndAt <= CAST(surgery.estimated_end_at AS TIMESTAMP)) ";

}
