-- Tạo các bảng trong DB --
-- Bảng users --
DROP TABLE IF EXISTS "app20222_db"."users";
CREATE TABLE IF NOT EXISTS "app20222_db"."users"
(
    id                    bigserial unique   not null,
    identification_number varchar(20)        not null,
    identity_type         int2               not null,
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
    created_by            int8          not null,
    modified_at           timestamp ,
    modified_by           int8,
    department_id         int8,
    primary key (id)
);
COMMENT ON COLUMN "app20222_db"."users"."id" IS 'Id của người dùng';
COMMENT ON COLUMN "app20222_db"."users"."identification_number" IS 'Số chứng thực cá nhân của người dùng';
COMMENT ON COLUMN "app20222_db"."users"."identity_type" IS 'Loại giấy tờ chứng thực (0: CMTND, 1: CCCD, 2: Hộ chiếu)';
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
    name         varchar(100) not null,
    address      text         not null,
    phone_number varchar(11)  not null,
    description  text,
    primary key (id)
);
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

COMMENT ON COLUMN "app20222_db"."patient"."id" IS 'Id của bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."identification_number" IS 'Số chứng thực cá nhân của bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."identity_type" IS 'Loại giấy tờ chứng thực (0: CMTND, 1: CCCD, 2: Hộ chiếu)';
COMMENT ON COLUMN "app20222_db"."patient"."code" IS 'Mã bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."first_name" IS 'Tên bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."last_name" IS 'Họ và tên đệm bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."birth_date" IS 'Ngày sinh bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."address" IS 'Địa chỉ bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."phone_number" IS 'Số điện thoại liên hệ bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."email" IS 'Email bệnh nhân';
COMMENT ON COLUMN "app20222_db"."patient"."user_id" IS 'Id tài khoản của bệnh nhân (nếu có)';







