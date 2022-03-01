IF OBJECT_ID('Role', 'U') IS NULL
CREATE TABLE Role
(
    id           varchar(36) primary key,
    rolename     varchar(255),
    created_time SMALLDATETIME,
    created_by   varchar(36),
    updated_time SMALLDATETIME,
    updated_by   varchar(36)
);

IF OBJECT_ID('Users', 'U') IS NULL
CREATE TABLE Users
(
    id           varchar(36) primary key,
    username     varchar(20)   not null unique,
    pass         varchar(255)  not null,
    name         nvarchar(255) not null,
    is_deleted   bit,
    token_value  varchar(255),
    created_time SMALLDATETIME not null,
    created_by   varchar(36),
    updated_time SMALLDATETIME not null,
    updated_by   varchar(36),
    id_role      varchar(36),
    constraint fk_user_role foreign key (id_role) references Role (id)
        ON DELETE CASCADE
);

IF OBJECT_ID('Permission', 'U') IS NULL
CREATE TABLE Permission
(
    id              varchar(36) primary key,
    table_name      varchar(50),
    permission_name varchar(20),
    created_time    SMALLDATETIME not null,
    updated_time    SMALLDATETIME not null,
);

IF OBJECT_ID('Permission_Role', 'U') IS NULL
CREATE TABLE Permission_Role
(
    id_role       varchar(36),
    id_permission varchar(36),
    primary key (id_role, id_permission),
    constraint fk_role_n_n foreign key (id_role) references Role (id)
        ON DELETE CASCADE,
    constraint fk_permission_n_n foreign key (id_permission) references Permission (id)
        ON DELETE CASCADE

);

IF OBJECT_ID('TryingPractice', 'U') IS NULL
CREATE TABLE TryingPractice
(
    id           varchar(36) primary key,
    name         nvarchar(255) not null,
    phone_number varchar(20)   not null,
    email        varchar(255)  not null,
    time_sent    SMALLDATETIME not null,
    is_contacted bit,
    time_reply   SMALLDATETIME,
    reply_by     varchar(36)
);

IF OBJECT_ID('Candidate', 'U') IS NULL
CREATE TABLE Candidate
(
    id            varchar(36) primary key,
    name          nvarchar(255) not null,
    date_of_birth DATE,
    email         varchar(255)  not null,
    phone_number  varchar(20)   not null,
    description   nvarchar(1000),
    time_sent     SMALLDATETIME not null,
    is_approve    bit,
    time_reply    SMALLDATETIME,
    reply_by      varchar(36)
);

IF OBJECT_ID('Coach', 'U') IS NULL
CREATE TABLE Coach
(
    id            varchar(36) primary key,
    name          nvarchar(255) not null,
    date_of_birth DATE,
    email         varchar(255)  not null,
    phone_number  varchar(20)   not null,
    description   nvarchar(1000),
    created_time  SMALLDATETIME not null,
    created_by    varchar(36),
    updated_time  SMALLDATETIME not null,
    updated_by    varchar(36),
);

IF OBJECT_ID('MembershipCard', 'U') IS NULL
CREATE TABLE MembershipCard
(
    id          varchar(36) primary key,
    cardname    nvarchar(255) not null,
    level_card  int,
    description nvarchar(1000),
    minprice    bigint        not null,
    is_deleted  bit
);

IF OBJECT_ID('GeneralClass', 'U') IS NULL
CREATE TABLE GeneralClass
(
    id            varchar(36) primary key,
    name          nvarchar(255)  not null,
    type          nvarchar(255)  not null,
    description   nvarchar(1000) not null,
    capacity      int,
    practice_time varchar(255),
    created_time  SMALLDATETIME  not null,
    created_by    varchar(36),
    updated_time  SMALLDATETIME  not null,
    updated_by    varchar(36),
    id_coach      varchar(36),
    constraint fk_coach_general foreign key (id_coach) references Coach (id)
        ON DELETE CASCADE
);

IF OBJECT_ID('Customer', 'U') IS NULL
CREATE TABLE Customer
(
    id            varchar(36) primary key,
    name          nvarchar(255) not null,
    phone_number  varchar(20)   not null,
    email         varchar(255)  not null,
    time_enroll   SMALLDATETIME,
    time_expire   smalldatetime,
    is_expire     bit,
    exercise_form nvarchar(255),
    created_time  SMALLDATETIME not null,
    created_by    varchar(36),
    updated_time  SMALLDATETIME not null,
    updated_by    varchar(36),
    id_card       varchar(36),
    constraint fk_card_customer foreign key (id_card) references MembershipCard (id)
        ON DELETE CASCADE
);

IF OBJECT_ID('PrivateClass', 'U') IS NULL
CREATE TABLE PrivateClass
(
    id                 varchar(36) primary key,
    name               nvarchar(255)  not null,
    description        nvarchar(1000) not null,
    number_sessions    int,
    remaining_sessions int,
    created_time       SMALLDATETIME  not null,
    created_by         varchar(36),
    id_customer        varchar(36),
    id_coach           varchar(36)
        constraint fk_customer_class foreign key (id_customer) references Customer (id)
            ON DELETE CASCADE,
    constraint fk_coach_class foreign key (id_coach) references Coach (id)
        ON DELETE CASCADE
);

IF OBJECT_ID('TrackingProgress', 'U') IS NULL
CREATE TABLE TrackingProgress
(
    id               varchar(36) primary key,
    time_checkin     smalldatetime not null,
    id_private_class varchar(36),
    constraint fk_class_tracking foreign key (id_private_class) references PrivateClass (id)
        ON DELETE CASCADE
);

IF OBJECT_ID('AccessManagement', 'U') IS NULL
CREATE TABLE AccessManagement
(
    id            varchar(36),
    time_checkin  smalldatetime,
    time_checkout smalldatetime,
    is_checkout   bit,
    id_class      varchar(36),
    id_customer   varchar(36),
    updated_time smalldatetime
    CONSTRAINT fk_class_access foreign key (id_class) references GeneralClass (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_customer_access foreign key (id_customer) references Customer (id)
        ON DELETE CASCADE,
)