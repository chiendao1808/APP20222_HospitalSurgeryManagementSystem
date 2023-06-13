-- Tạo view user_role --
DROP VIEW IF EXISTS "app20222_db"."view_user_role";
CREATE VIEW "app20222_db"."view_user_role" AS (
    SELECT
        usrRole.user_id,
        '{' || STRING_AGG(CAST(usrRole.role_id AS TEXT), ',') || '}' AS lst_role_id,
        '{' || STRING_AGG(role.name, ',') || '}' AS lst_role_name,
        '{' || STRING_AGG(role.code, ',') || '}' AS lst_role_code
        FROM "app20222_db"."users_roles" usrRole
        LEFT JOIN "app20222_db"."role" ON usrRole.role_id = role.id
        GROUP BY usrRole.user_id
);