-- Tạo các bảng trong DB --
-- Bảng users --
DROP TABLE IF EXISTS "app20222_db"."users";
CREATE TABLE IF NOT EXISTS "app20222_db"."users"
(
    id                    bigserial unique   not null,
    identification_number varchar(20)        not null,
    identity_type         int2               not null,
    avatar_path           text,
    code                  varchar(20) unique not null,
    first_name            varchar(50),
    last_name             varchar(50),
    title                 varchar(20),
    birth_date            date,
    address               text,
    phone_number          varchar(11),
    email                 varchar(50) unique,
    username              varchar(50) unique not null,
    password              varchar(100)        not null,
    created_at            timestamp          not null,
    created_by            int8               not null,
    modified_at           timestamp,
    modified_by           int8,
    department_id         int8,
    primary key (id)
);
COMMENT ON TABLE "app20222_db"."users" IS 'Bảng chứa thông tin của người dùng hệ thống (NV Bệnh viện/Bác sĩ/Bệnh nhân có tài khoản)';
COMMENT ON COLUMN "app20222_db"."users"."id" IS 'Id của người dùng';
COMMENT ON COLUMN "app20222_db"."users"."identification_number" IS 'Số chứng thực cá nhân của người dùng';
COMMENT ON COLUMN "app20222_db"."users"."identity_type" IS 'Loại giấy tờ chứng thực (0: CMTND, 1: CCCD, 2: Hộ chiếu)';
COMMENT ON COLUMN "app20222_db"."users"."avatar_path" IS 'Đường dẫn file avatar của người dùng';
COMMENT ON COLUMN "app20222_db"."users"."code" IS 'Mã người dùng (Mã nhân viên/bác sĩ/ bệnh nhân có tài khoản)';
COMMENT ON COLUMN "app20222_db"."users"."first_name" IS 'Tên của người dùng';
COMMENT ON COLUMN "app20222_db"."users"."last_name" IS 'Họ và tên đệm của người dùng';
COMMENT ON COLUMN "app20222_db"."users"."birth_date" IS 'Ngày sinh của người dùng';
COMMENT ON COLUMN "app20222_db"."users"."address" IS 'Địa chỉ người dùng';
COMMENT ON COLUMN "app20222_db"."users"."phone_number" IS 'Số điện thoại liên hệ của người dùng';
COMMENT ON COLUMN "app20222_db"."users"."email" IS 'Email của người dùng';
COMMENT ON COLUMN "app20222_db"."users"."username" IS 'Username đăng nhập';
COMMENT ON COLUMN "app20222_db"."users"."password" IS 'Password đăng nhập';
COMMENT ON COLUMN "app20222_db"."users"."created_at" IS 'Thời gian tài khoản người dùng được tạo';
COMMENT ON COLUMN "app20222_db"."users"."created_by" IS 'Người thực hiện tạo tài khoản';
COMMENT ON COLUMN "app20222_db"."users"."modified_at" IS 'Thời gian cập nhật tài khoản lần cuối';
COMMENT ON COLUMN "app20222_db"."users"."modified_by" IS 'Người thực hiện cập nhật tài khoản';
COMMENT ON COLUMN "app20222_db"."users"."department_id" IS 'Id của khoa/bộ phận trực thuộc (null: nếu không phải NV/BS)';

-- Init dữ liệu --
INSERT INTO "app20222_db".users (identification_number, identity_type, code, first_name, last_name, username, password,
                                 created_at, created_by)
VALUES ('00293849828', 1, 'ADMIN_SUPER1', 'Admin', 'Super', 'admin', '$2a$12$4FTmJ2x/BfKIN9as9ivNKuo8CJZd4jtk0UDEijm7OYqrusJN251du', now(), 1);
-- username = admin, password = admin --

-- ======================================================================== --
-- Bảng auth_info --
DROP TABLE IF EXISTS "app20222_db"."auth_info";
CREATE TABLE IF NOT EXISTS "app20222_db"."auth_info" (
    id bigserial not null unique,
    user_id int8 not null unique ,
    token text not null,
    status int2 not null ,
    ip_address varchar(32),
    created_at timestamp not null ,
    modified_at timestamp,
    primary key (id)
);
COMMENT ON COLUMN "app20222_db"."auth_info"."id" IS 'Id bản ghi lưu token';
COMMENT ON COLUMN "app20222_db"."auth_info"."user_id" IS 'Id user';
COMMENT ON COLUMN "app20222_db"."auth_info"."status" IS 'Trạng thái token';
COMMENT ON COLUMN "app20222_db"."auth_info"."token" IS 'Token đăng nhập hiện tại';
COMMENT ON COLUMN "app20222_db"."auth_info"."created_at" IS 'Thời gian tạo';
COMMENT ON COLUMN "app20222_db"."auth_info"."ip_address" IS 'Địa chỉ ip đăng nhập';
COMMENT ON COLUMN "app20222_db"."auth_info"."modified_at" IS 'Thời gian cập nhật mới nhất';


-- ================================================================--
-- Bảng department --
DROP TABLE IF EXISTS "app20222_db"."department";
CREATE TABLE IF NOT EXISTS "app20222_db"."department"
(
    id           bigserial    not null unique,
    code         varchar(50)  not null unique,
    logo_path    text,
    name         varchar(100) not null,
    address      text         ,
    phone_number varchar(11)  ,
    description  text,
    primary key (id)
);
COMMENT ON TABLE "app20222_db"."department" IS 'Bảng chứa thông tin khoa/bộ phận của NV bệnh viện/ Bác sĩ';
COMMENT ON COLUMN "app20222_db"."department"."id" IS 'Id của khoa/bộ phận';
COMMENT ON COLUMN "app20222_db"."department"."code" IS 'Mã của khoa/bộ phận';
COMMENT ON COLUMN "app20222_db"."department"."logo_path" IS 'Đường dẫn file logo khoa/bộ phận';
COMMENT ON COLUMN "app20222_db"."department"."name" IS 'Tên của khoa/bộ phận';
COMMENT ON COLUMN "app20222_db"."department"."address" IS 'Địa chỉ của khoa/bộ phận';
COMMENT ON COLUMN "app20222_db"."department"."phone_number" IS 'Số điện thoại liên hệ của khoa/bộ phận';
COMMENT ON COLUMN "app20222_db"."department"."description" IS 'Thông tin mô tả khoa/bộ phận';
-- Init department data --
INSERT INTO "app20222_db"."department" (code, name, address, phone_number) VALUES ('DIRECTOR_COMMITTEE', 'Ban giám đốc', 'Phòng 301 - Tòa hành chính', '0438686868');

-- ======================================================================== --
-- Bảng patient --
DROP TABLE IF EXISTS "app20222_db"."patient";
CREATE TABLE IF NOT EXISTS "app20222_db"."patient"
(
    id                    bigserial   not null unique,
    identification_number varchar(20) not null,
    identity_type         int2        not null,
    portrait_path         text,
    code                  varchar(20) not null unique,
    first_name            varchar(50),
    last_name             varchar(50),
    birth_date            date,
    address               text,
    phone_number          varchar(11),
    email                 varchar(50),
    primary key (id)
);
COMMENT ON TABLE "app20222_db"."patient" IS 'Bảng chứa thông tin định danh và các thông tin chung của bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."id" IS 'Id của bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."identification_number" IS 'Số chứng thực cá nhân của bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."identity_type" IS 'Loại giấy tờ chứng thực (0: CMTND, 1: CCCD, 2: Hộ chiếu)';
COMMENT ON COLUMN "app20222_db"."patient"."portrait_path" IS 'Đường dẫn lưu file ảnh chân dung';
COMMENT ON COLUMN "app20222_db"."patient"."code" IS 'Mã bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."first_name" IS 'Tên bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."last_name" IS 'Họ và tên đệm bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."birth_date" IS 'Ngày sinh bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."address" IS 'Địa chỉ bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."phone_number" IS 'Số điện thoại liên hệ bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."email" IS 'Email bệnh nhân';

-- ======================================================================== --
-- Bảng medical_record --
DROP TABLE IF EXISTS "app20222_db"."medical_record";
CREATE TABLE IF NOT EXISTS "app20222_db"."medical_record"(
    id bigserial not null unique,
    patient_id int8 not null,
    summary text,
    created_by int8 not null,
    created_at timestamp not null,
    primary key (id)
);
COMMENT ON COLUMN "app20222_db"."medical_record"."id" IS 'Id bản ghi hồ sơ bệnh án';
COMMENT ON COLUMN "app20222_db"."medical_record"."patient_id" IS 'Id bệnh nhân';
COMMENT ON COLUMN "app20222_db"."medical_record"."summary" IS 'Kết luận tổng quát';
COMMENT ON COLUMN "app20222_db"."medical_record"."created_by" IS 'Id người lập/tạo bản ghi hồ sơ bệnh án';
COMMENT ON COLUMN "app20222_db"."medical_record"."created_at" IS 'Thời gian tạo/lập bản ghi hồ sơ bệnh án';

-- ======================================================================== --
-- Bảng surgery --
DROP TABLE IF EXISTS "app20222_db"."surgery";
CREATE TABLE IF NOT EXISTS "app20222_db"."surgery"
(
    id               bigserial not null unique,
    name             varchar(100),
    description      text,
    disease_group_id int8,
    type             int4,
    created_at       timestamp not null,
    created_by       int8      not null,
    modified_at      timestamp,
    modified_by      int8,
    started_at       timestamp not null,
    estimated_end_at timestamp not null,
    end_at           timestamp,
    result           text,
    patient_id       int8      not null,
    surgery_room_id  int8,
    status           int2      not null,
    lst_file_id      int8[],
    primary key (id)
);
COMMENT ON TABLE "app20222_db"."surgery" IS 'Bảng lưu thông tin các ca phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."id" IS 'Id của ca phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."name" IS 'Tên ca phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."description" IS 'Mô tả ca phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."disease_group_id" IS 'Nhóm bệnh';
COMMENT ON COLUMN "app20222_db"."surgery"."type" IS 'Loại ca phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."created_at" IS 'Thời gian tạo ca phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."created_by" IS 'Id người tạo ca phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."modified_at" IS 'Thời gian cập nhập ca phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."modified_by" IS 'Id người cập nhật ca phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."started_at" IS 'Thời gian bắt đầu phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."estimated_end_at" IS 'Thời gian kết thúc phẫu thuật dự kiến';
COMMENT ON COLUMN "app20222_db"."surgery"."end_at" IS 'Thời gian kết thúc phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."result" IS 'Kết quả thực hiện phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."patient_id" IS 'Id bệnh nhân được phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."surgery_room_id" IS 'Id phòng thực hiện phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."status" IS 'Trạng thái ca phẫu thuật (0 : Chờ thực hiện, 1 : Đang được thực hiện , 2 : Đã thực hiện, 3 : Đã hủy)';
COMMENT ON COLUMN "app20222_db"."surgery"."lst_file_id" IS 'Danh sách id các file tài liệu phẫu thuật';

-- ======================================================================== --
-- Bảng disease_group --
DROP TABLE IF EXISTS "app20222_db"."disease_group";
CREATE TABLE IF NOT EXISTS "app20222_db"."disease_group"
(
    id   int8 not null unique,
    name varchar(100),
    code varchar(10),
    primary key (id)
);

COMMENT ON TABLE "app20222_db"."disease_group" IS 'Bảng lưu thông tin nhóm bệnh';
COMMENT ON COLUMN "app20222_db"."disease_group"."id" IS 'Id nhóm bệnh';
COMMENT ON COLUMN "app20222_db"."disease_group"."name" IS 'Tên nhóm bệnh';
COMMENT ON COLUMN "app20222_db"."disease_group"."code" IS 'Mã nhóm bệnh';

-- ======================================================================== --
-- Bảng surgery_room --
DROP TABLE IF EXISTS "app20222_db"."surgery_room";
CREATE TABLE IF NOT EXISTS "app20222_db"."surgery_room"
(
    id                bigserial   not null unique,
    name              varchar(50) not null,
    address           text,
    description       text,
    current_available boolean,
    primary key (id)
);
COMMENT ON TABLE "app20222_db"."surgery_room" IS 'Bảng lưu thông tin phòng phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery_room"."id" IS 'Id phòng phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery_room"."name" IS 'Tên phòng phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery_room"."address" IS 'Địa chỉ phòng phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery_room"."description" IS 'Mô tả thông tin phòng phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery_room"."current_available" IS 'Trạng thái phòng phẫu thuật hiện tại (Sẵn sàng/Đang được sử dụng)';

-- ======================================================================== --
-- Bảng file_attach --
DROP TABLE IF EXISTS "app20222_db"."file_attach";
CREATE TABLE IF NOT EXISTS "app20222_db"."file_attach"
(
    id          bigserial   not null unique,
    name        text        not null,
    type        int2        not null,
    file_ext    varchar(10) not null,
    size        int4,
    stored_type int2        not null,
    location    text        not null,
    created_by  int8        not null,
    created_at  timestamp   not null,
    primary key (id)
);
COMMENT ON TABLE "app20222_db"."file_attach" IS 'Bảng lưu thông tin file tải lên';
COMMENT ON COLUMN "app20222_db"."file_attach"."id" IS 'Id file đính kèm';
COMMENT ON COLUMN "app20222_db"."file_attach"."name" IS 'Tên file đính kèm';
COMMENT ON COLUMN "app20222_db"."file_attach"."type" IS 'Loại file đính kèm (0: File avatar/ảnh chân dung, 1: File ảnh chụp/scan tài liệu, 2: File tài liệu văn bản)';
COMMENT ON COLUMN "app20222_db"."file_attach"."file_ext" IS 'Định dạng file';
COMMENT ON COLUMN "app20222_db"."file_attach"."size" IS 'Kích thước file (byte)';
COMMENT ON COLUMN "app20222_db"."file_attach"."id" IS 'Loại kho lưu trữ file (0: Lưu tại server, 1: Lưu tại cloud server bên thứ 3)';
COMMENT ON COLUMN "app20222_db"."file_attach"."location" IS 'Đường dẫn đến địa chỉ lưu trữ file';
COMMENT ON COLUMN "app20222_db"."file_attach"."created_by" IS 'Người tải lên file';
COMMENT ON COLUMN "app20222_db"."file_attach"."created_at" IS 'Thời gian tải lên file';

-- ======================================================================== --
-- Bảng role --
DROP TABLE IF EXISTS "app20222_db"."role";
CREATE TABLE IF NOT EXISTS "app20222_db"."role"(
    id bigserial not null unique,
    code varchar(30) not null unique,
    name text not null,
    displayed_name text not null,
    is_default boolean not null,
    primary key (id)
);
COMMENT ON COLUMN "app20222_db"."role"."id" IS 'Id vai trò';
COMMENT ON COLUMN "app20222_db"."role"."code" IS 'Mã code vai trò';
COMMENT ON COLUMN "app20222_db"."role"."name" IS 'Tên của vai trò';
-- init default role data --
DELETE FROM "app20222_db"."role" WHERE is_default = true;
INSERT INTO "app20222_db"."role" (code, name, displayed_name, is_default) VALUES
                                     ('ROLE_SUPER_ADMIN', 'SUPER_ADMIN', 'Admin tổng', true),

                                     ('ROLE_HOSPITAL_ADMIN', 'HOSPITAL_ADMIN', 'Admin bệnh viện', true),

                                     ('ROLE_HOSPITAL_MANAGER', 'HOSPITAL_MANAGER', 'Quản lý tổng bệnh viện', true),

                                     ('ROLE_DEPARTMENT_ADMIN', 'DEPARTMENT_ADMIN', 'Admin khoa/bộ phận', true),

                                     ('ROLE_DEPARTMENT_MANAGER', 'DEPARTMENT_MANAGER', 'Quản lý khoa/bộ phận', true),

                                     ('ROLE_DOCTOR', 'DOCTOR', 'Bác sĩ', true),

                                     ('ROLE_NURSE', 'NURSE', 'Y tá', true);
-- ========================================== --

-- Bảng users_roles --
DROP TABLE IF EXISTS "app20222_db"."users_roles";
CREATE TABLE IF NOT EXISTS "app20222_db"."users_roles"(
    user_id int8 not null,
    role_id int8 not null,
    primary key (user_id, role_id)
);
COMMENT ON COLUMN "app20222_db"."users_roles"."user_id" IS 'Id của người dùng';
COMMENT ON COLUMN "app20222_db"."users_roles"."role_id" IS 'Id của vai trò';
INSERT INTO "app20222_db"."users_roles" (user_id, role_id) VALUES (1,1); -- Init super admin role --

-- Bảng users_surgeries ---
DROP TABLE IF EXISTS "app20222_db"."users_surgeries";
CREATE TABLE IF NOT EXISTS "app20222_db"."users_surgeries"(
      id bigserial not null unique,
      user_id int8 not null,
      surgery_id int8 not null,
      surgery_role_type int4 not null,
      primary key (user_id, surgery_id)
);
COMMENT ON COLUMN "app20222_db"."users_surgeries"."id" IS 'Id row';
COMMENT ON COLUMN "app20222_db"."users_surgeries"."user_id" IS 'Id của người dùng';
COMMENT ON COLUMN "app20222_db"."users_surgeries"."surgery_id" IS 'Id của ca phẫu thuật';
COMMENT ON COLUMN "app20222_db"."users_surgeries"."surgery_role_type" IS 'Loại vai trò trong ca phẫu thuật (0: Bác sĩ phẫu thuật chính, 1: Bác sĩ gây mê, 2: Bác sĩ hỗ trợ, 3: Y tá hỗ trợ, 4: Nhân viên ghi tài liệu)';

-- Bảng medical_records_files --
DROP TABLE IF EXISTS "app20222_db"."medical_records_files";
CREATE TABLE IF NOT EXISTS "app20222_db"."medical_records_files"(
      medical_record_id int8 not null,
      file_id int8 not null,
      primary key (medical_record_id, file_id)
);
COMMENT ON COLUMN "app20222_db"."medical_records_files"."medical_record_id" IS 'Id bản ghi hồ sơ bệnh án';
COMMENT ON COLUMN "app20222_db"."medical_records_files"."file_id" IS 'Id file';

-- Bảng surgeries_files --
-- Bảng medical_records_files --
DROP TABLE IF EXISTS "app20222_db"."surgeries_files";
CREATE TABLE IF NOT EXISTS "app20222_db"."surgeries_files"(
        surgery_id int8 not null,
        file_id int8 not null,
        primary key (surgery_id, file_id)
);
COMMENT ON COLUMN "app20222_db"."surgeries_files"."surgery_id" IS 'Id bản ghi ca phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgeries_files"."file_id" IS 'Id file';

-- Bảng mail --
DROP TABLE IF EXISTS "app20222_db"."mail";
CREATE TABLE IF NOT EXISTS "app20222_db"."mail"(
    id bigserial not null unique,
    code varchar(30) not null unique,
    lst_to_address text,
    from_address text not null,
    subject text not null,
    content text not null,
    status int4 not null,
    is_has_attachments bool not null,
    sent_time timestamp not null,
    primary key (id)
);
COMMENT ON COLUMN "app20222_db"."mail"."id" IS 'Id bản ghi lịch sử gửi mail';
COMMENT ON COLUMN "app20222_db"."mail"."code" IS 'Mã code của email lưu trong hệ thống (phục vụ cho việc gửi lại nếu cần)';
COMMENT ON COLUMN "app20222_db"."mail"."lst_to_address" IS 'Danh sách địa chỉ email được gửi';
COMMENT ON COLUMN "app20222_db"."mail"."subject" IS 'Tiêu đề gửi mail';
COMMENT ON COLUMN "app20222_db"."mail"."from_address" IS 'Địa chỉ gửi';
COMMENT ON COLUMN "app20222_db"."mail"."content" IS 'Nội dung email';
COMMENT ON COLUMN "app20222_db"."mail"."is_has_attachments" IS 'Có/Không file đính kèm';
COMMENT ON COLUMN "app20222_db"."mail"."status" IS 'Trạng thái email (0: Chờ gửi, 1: Đã gửi, 2: Gửi thành công, 3 : Gửi thất bại)';
COMMENT ON COLUMN "app20222_db"."mail"."sent_time" IS 'Thời gian gửi';





















