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
}
