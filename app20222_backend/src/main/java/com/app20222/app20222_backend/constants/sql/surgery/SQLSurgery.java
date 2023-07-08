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
            "     '{' || STRING_AGG(surgery.id::::TEXT, ',') || '}' AS lstAssigneeId \n" +
            "FROM {h-schema}surgery \n" +
            "   JOIN {h-schema}users_surgeries AS uSurgery ON uSurgery.surgery_id = surgery.id \n" +
            "WHERE " +
            "      surgery.status IN (0,1) AND \n" +
            "      ( \n" +
            "           (:startedTime BETWEEN surgery.started_at AND surgery.estimated_end_at) \n" +
            "            OR (:estimatedEndTime BETWEEN surgery.started_at AND surgery.estimated_end_at)  \n" +
            "      ) \n" +
            "GROUP BY surgery.id";

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

    public static final String GET_DETAILS_SURGERY =
        "WITH surgeryFileCTE AS ( \n" +
            "SELECT \n" +
            "   surgeryFiles.surgery_id AS surgeryId, \n" +
            "   '{' || STRING_AGG(surgeryFiles.file_id::::TEXT, ',') || '}' AS lstFileAttachId \n " +
            "   FROM {h-schema}surgeries_files AS surgeryFiles \n" +
            "   GROUP BY surgeryFiles.surgery_id " +
            ") \n"+
        "SELECT \n" +
            "     surgery.id AS id, \n" +
            "     surgery.name AS name, \n" +
            "     surgery.description AS description, \n" +
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
            "     surgery.result AS result, \n" +
            "     surgery.created_at AS createdAt, \n" +
            "     CONCAT_WS('-', CONCAT_WS(' ', usrCreated.last_name, usrCreated.first_name), usrCreated.email) AS createdBy, \n" +
            "     surgeryFileCTE.lstFileAttachId AS lstFileAttachId \n " +
            "FROM {h-schema}surgery \n" +
            "   LEFT JOIN {h-schema}patient ON patient.id = surgery.patient_id \n" +
            "   LEFT JOIN {h-schema}surgery_room AS sRoom ON sRoom.id = surgery.surgery_room_id \n" +
            "   LEFT JOIN {h-schema}disease_group AS diseaseGroup ON diseaseGroup.id = surgery.disease_group_id \n" +
            "   LEFT JOIN {h-schema}users usrCreated ON usrCreated.id = surgery.created_by \n" +
            "   LEFT JOIN {h-schema}department ON department.id = usrCreated.id \n" +
            "   LEFT JOIN surgeryFileCTE ON surgeryFileCTE.surgeryId = surgery.id \n" +
            "WHERE surgery.id = :surgeryId \n";

    public static final String GET_SURGERY_ASSIGNMENTS =
        "SELECT \n" +
            "     ursSurgery.user_id AS assigneeId, \n" +
            "     CONCAT_WS(' ', users.last_name, users.first_name) AS assigneeName, \n" +
            "     users.code AS assigneeCode, \n" +
            "     CASE \n" +
            "           WHEN sRoleType.type IS NOT NULL THEN sRoleType.name \n" +
            "           ELSE '' \n" +
            "     END AS surgeryRole \n" +
            "FROM {h-schema}users_surgeries AS ursSurgery \n" +
            "   LEFT JOIN {h-schema}users ON users.id = ursSurgery.user_id \n" +
            "   LEFT JOIN {h-schema}surgery_role_type AS sRoleType ON sRoleType.type = ursSurgery.surgery_role_type \n" +
            "WHERE ursSurgery.surgery_id = :surgeryId \n";

    public static final String DELETE_ALL_SURGERY_FILE_ATTACHED_BY_SURGERY_ID =
        "DELETE FROM {h-schema}surgeries_files WHERE surgery_id = :surgeryId ";

    public static final String GET_LIST_SURGERY_FILE =
        "SELECT \n" +
            "     file.id AS fileId, \n" +
            "     file.name AS fileName, \n" +
            "     file.location AS location \n" +
            "FROM {h-schema}surgeries_files AS surFile \n" +
            "   JOIN {h-schema}file_attach AS file ON file.id = surFile.file_id \n" +
            "WHERE surFile.surgery_id = :surgeryId ";

}
