package com.app20222.app20222_backend.constants.sql.statistics;

public class SQLStatistics {

    public static final String GET_PREVIEW_CURRENT_SURGERY_NUM =
        "SELECT\n" +
            "       COUNT(id) FILTER (WHERE (end_at BETWEEN DATE_TRUNC('month', NOW()) AND NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh')) \n" +
            "           AS currentMonthNum,\n" +
            "       COUNT(id) FILTER (WHERE (end_at BETWEEN DATE_TRUNC('quarter', NOW()) AND NOW() AT TIME ZONE 'Asia/Ho_Chi_Minh')) \n" +
            "           AS currentQuarterNum,\n" +
            "       COUNT(id) FILTER (WHERE (end_at BETWEEN DATE_TRUNC('year', now()) AND now() AT TIME ZONE 'Asia/Ho_Chi_Minh')) \n" +
            "           AS currentYearNum\n" +
            "FROM {h-schema}surgery \n" +
            "WHERE \n" +
            "      id IN (:lstViewableSurgeryId) AND \n" +
            "      end_at IS NOT NULL AND \n" +
            "      status = 2 ";

    public static final String GET_PREVIEW_SURGERY_NUM_MONTHLY_IN_A_YEAR =
        "SELECT\n" +
            "       EXTRACT(MONTH FROM end_at) AS month,\n" +
            "       COUNT(id) as numSurgery \n" +
            "    FROM {h-schema}surgery \n" +
            "    WHERE\n" +
            "          id IN (:lstViewableSurgeryId) AND \n" +
            "          end_at IS NOT NULL AND \n" +
            "          status = 2 AND \n" +
            "          end_at BETWEEN :firstDateOfYear AND :lastDateOfYear \n" +
            "    GROUP BY month\n" +
            "    ORDER BY month ASC ";

    public static final String GET_PREVIEW_LIST_SURGERY =
        "SELECT  \n" +
            "     surgery.id AS id, \n" +
            "     surgery.name AS name, \n" +
            "     surgery.code AS code, \n" +
            "     diseaseGroup.name AS diseaseGroupName, \n" +
            "     '' AS type, \n" +
            "     CONCAT_WS(' ', patient.last_name, patient.first_name) AS patientName, \n" +
            "     patient.code AS patientCode, \n" +
            "     sRoom.name || '-' || sRoom.address AS surgeryRoomName, \n" +
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
            "     surgery.id IN (:lstViewableSurgeryId) AND \n" +
            "     surgery.end_at IS NOT NULL AND \n" +
            "     surgery.status = 2 AND \n" +
            "     (:startTime = DATE('1970-01-01') OR DATE(surgery.end_at) >= :startTime) AND \n" +
            "     (:endTime = DATE('1970-01-01') OR DATE(surgery.end_at) <= :endTime) \n";

    public static final String EXPORT_PREVIEW_SURGERY_QUERY =
        "SELECT  \n" +
            "     surgery.name AS name, \n" +
            "     surgery.code AS code, \n" +
            "     diseaseGroup.name AS diseaseGroupName, \n" +
            "     '' AS type, \n" +
            "     CONCAT_WS(' ', patient.last_name, patient.first_name) AS patientName, \n" +
            "     patient.code AS patientCode, \n" +
            "     sRoom.name || '-' || sRoom.address AS surgeryRoomName, \n" +
            "     CASE \n" +
            "           WHEN surgery.status = 0 THEN 'Chờ thực hiện' \n" +
            "           WHEN surgery.status = 1 THEN 'Đang được thực hiện' \n" +
            "           WHEN surgery.status = 2 THEN 'Đã được thực hiện' \n" +
            "           WHEN surgery.status = 3 THEN 'Đã hủy' \n" +
            "           ELSE '' \n" +
            "     END AS status, \n" +
            "     ' ' || TO_CHAR(surgery.started_at, 'dd/MM/yyyy HH:mm') AS startedAt, \n" +
            "     ' ' || TO_CHAR(surgery.estimated_end_at, 'dd/MM/yyyy HH:mm') AS estimatedEndAt, \n" +
            "     ' ' || TO_CHAR(surgery.end_at, 'dd/MM/yyyy HH:mm') AS endAt, \n" +
            "     surgery.result AS result \n" +
            "FROM {h-schema}surgery \n" +
            "   LEFT JOIN {h-schema}patient ON patient.id = surgery.patient_id \n" +
            "   LEFT JOIN {h-schema}surgery_room AS sRoom ON sRoom.id = surgery.surgery_room_id \n" +
            "   LEFT JOIN {h-schema}disease_group AS diseaseGroup ON diseaseGroup.id = surgery.disease_group_id \n" +
            "WHERE \n" +
            "     surgery.id IN (:lstViewableSurgeryId) AND \n" +
            "     surgery.end_at IS NOT NULL AND \n" +
            "     surgery.status = 2 AND \n" +
            "     DATE(surgery.end_at) BETWEEN DATE(:startTime) AND DATE(:endTime)\n";


}
