package com.app20222.app20222_backend.constants.sql.users;

public class SQLUser {

    public static final String GET_LIST_USER =
        "SELECT \n" +
                "    users.id AS id,\n" +
                "    CONCAT_WS(' ', users.last_name, users.first_name) AS name, \n" +
                "    users.identification_number AS identificationNum,\n" +
                "    CASE \n" +
                "           WHEN users.identity_type = 0 THEN 'Chứng minh thư nhân dân' \n" +
                "           WHEN users.identity_type = 1 THEN 'Căn cước công dân' \n" +
                "           WHEN users.identity_type = 2 THEN 'Hộ chiếu' \n" +
                "           ELSE '' \n" +
                "     END AS identityType, \n" +
                "     users.title AS title," +
                "     users.birth_date AS birthDate, \n" +
                "     users.phone_number AS phoneNumber, \n" +
                "     users.address AS address, \n" +
                "     users.email AS email, \n" +
                "     department.name AS department \n" +
                "FROM {h-schema}users \n" +
                "     LEFT JOIN {h-schema}department ON users.department_id = department.id \n" +
                "WHERE \n" +
                "      (:code = '' OR users.code ilike ('%' || :code || '%')) AND  \n" +
                "      (:name = '' OR CONCAT_WS(' ', users.last_name, users.first_name) ILIKE ('%' || :name || '%')) AND \n" +
                "      (:email = '' OR users.code ilike ('%' || :email || '%')) AND \n" +
                "      (:phone = '' OR users.code ilike ('%' || :phone || '%')) AND \n" +
                "      (users.department_id IN (:lstDepartmentId)) \n" +
                "ORDER BY COALESCE(users.modified_at, users.created_at) DESC ";

        public static final String GET_DETAIL_USER =
            "SELECT \n" +
                "    users.id AS id,\n" +
                "    CONCAT_WS(' ', users.last_name, users.first_name) AS name, \n" +
                "    users.identification_number AS identificationNum,\n" +
                "    CASE \n" +
                "           WHEN users.identity_type = 0 THEN 'Chứng minh thư nhân dân' \n" +
                "           WHEN users.identity_type = 1 THEN 'Căn cước công dân' \n" +
                "           WHEN users.identity_type = 2 THEN 'Hộ chiếu' \n" +
                "           ELSE '' \n" +
                "     END AS identityType, \n" +
                "     users.title AS title," +
                "     users.birth_date AS birthDate, \n" +
                "     users.phone_number AS phoneNumber, \n" +
                "     users.address AS address, \n" +
                "     users.email AS email, \n" +
                "     department.name AS department \n" +
                "FROM {h-schema}users \n" +
                "     LEFT JOIN {h-schema}department ON users.department_id = department.id \n" +
                "WHERE \n" +
                "     users.id = :userId ";

        public static final String GET_LIST_USER_FEATURES_BY_ROLES =
            "SELECT code \n" +
                "FROM {h-schema}features\n" +
                "WHERE \n" +
                "    (:roles)::::text[] && lst_usable_role ";
}
