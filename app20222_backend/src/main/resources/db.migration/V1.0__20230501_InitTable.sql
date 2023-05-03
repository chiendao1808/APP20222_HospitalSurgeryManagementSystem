-- Tạo các bảng trong DB --
-- Bảng users --
DROP TABLE IF EXISTS "app20222_db"."users";
CREATE TABLE IF NOT EXISTS "app20222_db"."users"
(
    id                    bigserial unique   not null,
    identification_number varchar(20)        not null,
    identity_type         int2               not null,
    avatar_id             int8,
    code                  varchar(20) unique not null,
    first_name            varchar(50),
    last_name             varchar(50),
    birth_date            date,
    address               text,
    phone_number          varchar(11),
    email                 varchar(50) unique,
    username              varchar(50) unique not null,
    password              varchar(30)        not null,
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
COMMENT ON COLUMN "app20222_db"."users"."avatar_id" IS 'Id file avatar của người dùng';
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
VALUES ('00293849828', 1, 'ADMIN_SUPER1', 'Admin', 'Super', 'admin', 'admin', now(), 1);


-- Bảng department --
DROP TABLE IF EXISTS "app20222_db"."department";
CREATE TABLE IF NOT EXISTS "app20222_db"."department"
(
    id           bigserial    not null unique,
    code         varchar(10)  not null unique,
    logo_id      int8,
    name         varchar(100) not null,
    address      text         not null,
    phone_number varchar(11)  not null,
    description  text,
    primary key (id)
);
COMMENT ON TABLE "app20222_db"."department" IS 'Bảng chứa thông tin khoa/bộ phận của NV bệnh viện/ Bác sĩ';
COMMENT ON COLUMN "app20222_db"."department"."id" IS 'Id của khoa/bộ phận';
COMMENT ON COLUMN "app20222_db"."department"."code" IS 'Mã của khoa/bộ phận';
COMMENT ON COLUMN "app20222_db"."department"."name" IS 'Tên của khoa/bộ phận';
COMMENT ON COLUMN "app20222_db"."department"."address" IS 'Địa chỉ của khoa/bộ phận';
COMMENT ON COLUMN "app20222_db"."department"."phone_number" IS 'Số điện thoại liên hệ của khoa/bộ phận';
COMMENT ON COLUMN "app20222_db"."department"."description" IS 'Thông tin mô tả khoa/bộ phận';

-- Bảng patient --
DROP TABLE IF EXISTS "app20222_db"."patient";
CREATE TABLE IF NOT EXISTS "app20222_db"."patient"
(
    id                    bigserial   not null unique,
    identification_number varchar(20) not null,
    identity_type         int2        not null,
    portrait_img_id       int8,
    code                  varchar(20) not null unique,
    first_name            varchar(50),
    last_name             varchar(50),
    birth_date            date,
    address               text,
    phone_number          varchar(11),
    email                 varchar(50),
    user_id               int8,
    primary key (id)
);
COMMENT ON TABLE "app20222_db"."patient" IS 'Bảng chứa thông tin định danh và các thông tin chung của bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."id" IS 'Id của bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."identification_number" IS 'Số chứng thực cá nhân của bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."identity_type" IS 'Loại giấy tờ chứng thực (0: CMTND, 1: CCCD, 2: Hộ chiếu)';
COMMENT ON COLUMN "app20222_db"."patient"."portrait_img_id" IS 'Ảnh chân dung bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."code" IS 'Mã bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."first_name" IS 'Tên bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."last_name" IS 'Họ và tên đệm bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."birth_date" IS 'Ngày sinh bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."address" IS 'Địa chỉ bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."phone_number" IS 'Số điện thoại liên hệ bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."email" IS 'Email bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."user_id" IS 'Id tài khoản của bệnh nhân (nếu có)';

-- Bảng surgery --
DROP TABLE IF EXISTS "app20222_db"."surgery";
CREATE TABLE IF NOT EXISTS "app20222_db"."surgery"
(
    id               bigserial not null unique,
    name             varchar(100),
    description      text,
    disease_group_id int8,
    type             varchar(50),
    created_at       timestamp not null,
    created_by       int8      not null,
    modified_at      timestamp,
    modified_by      int8,
    started_at       timestamp not null,
    estimated_end_at timestamp not null,
    end_at           timestamp,
    assignee_id      int8,
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
COMMENT ON COLUMN "app20222_db"."surgery"."assignee_id" IS 'Id bác sĩ thực hiện phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."result" IS 'Kết quả thực hiện phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."patient_id" IS 'Id bệnh nhân được phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."surgery_room_id" IS 'Id phòng thực hiện phẫu thuật';
COMMENT ON COLUMN "app20222_db"."surgery"."status" IS 'Trạng thái ca phẫu thuật (0 : Chờ thực hiện, 1 : Đang được thực hiện , 2 : Đã thực hiện)';

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
COMMENT ON COLUMN "app20222_db"."file_attach"."type" IS 'Loại file đính kèm ( 0: File avatar/ảnh chân dung, 1: File ảnh chụp/scan tài liệu, 2: File tài liệu văn bản)';
COMMENT ON COLUMN "app20222_db"."file_attach"."file_ext" IS 'Định dạng file';
COMMENT ON COLUMN "app20222_db"."file_attach"."size" IS 'Kích thước file (byte)';
COMMENT ON COLUMN "app20222_db"."file_attach"."id" IS 'Loại kho lưu trữ file (0: Lưu tại server, 1: Lưu tại cloud server bên thứ 3)';
COMMENT ON COLUMN "app20222_db"."file_attach"."location" IS 'Đường dẫn đến địa chỉ lưu trữ file';
COMMENT ON COLUMN "app20222_db"."file_attach"."created_by" IS 'Người tải lên file';
COMMENT ON COLUMN "app20222_db"."file_attach"."created_at" IS 'Thời gian tải lên file';



















