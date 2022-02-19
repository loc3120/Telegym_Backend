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

IF OBJECT_ID('TryingPractice', 'U') IS NULL
CREATE TABLE TryingPractice
(
    id           varchar(36) primary key,
    name         nvarchar(255) not null,
    phone_number varchar(255)  not null,
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
    phone_number  varchar(255)  not null,
    description   nvarchar(1000),
    time_sent     SMALLDATETIME not null,
    is_approve    bit,
    time_reply    SMALLDATETIME,
    reply_by      varchar(36)
);

IF OBJECT_ID('TypeExercise', 'U') IS NULL
CREATE TABLE TypeExercise
(
    id           varchar(36) primary key,
    typename     nvarchar(255),
    created_time SMALLDATETIME not null,
    created_by   varchar(36),
    updated_time SMALLDATETIME not null,
    updated_by   varchar(36),
);

IF OBJECT_ID('Coach', 'U') IS NULL
CREATE TABLE Coach
(
    id            varchar(36) primary key,
    name          nvarchar(255) not null,
    date_of_birth DATE,
    email         varchar(255)  not null,
    phone_number  varchar(255)  not null,
    description   nvarchar(1000),
    id_type       varchar(36),
    created_time  SMALLDATETIME not null,
    created_by    varchar(36),
    updated_time  SMALLDATETIME not null,
    updated_by    varchar(36),
    CONSTRAINT fk_type_coach foreign key (id_type) references TypeExercise (id)
        ON DELETE CASCADE,
);

IF OBJECT_ID('Package', 'U') IS NULL
CREATE TABLE Package
(
    id            varchar(36) primary key,
    name          nvarchar(255) not null,
    time_duration nvarchar(255),
    description   nvarchar(1000),
    price         bigint,
    is_deleted    bit,
    id_type       varchar(36),
    created_time  SMALLDATETIME not null,
    created_by    varchar(36),
    updated_time  SMALLDATETIME not null,
    updated_by    varchar(36),
    CONSTRAINT fk_type_pac foreign key (id_type) references TypeExercise (id)
        ON DELETE CASCADE,
);

-- IF OBJECT_ID('Coach_Package', 'U') IS NULL
-- CREATE TABLE Coach_Package
-- (
--     id           varchar(36) primary key,
--     id_coach     varchar(36),
--     id_package   varchar(36),
--     created_time SMALLDATETIME not null,
--     created_by   varchar(36),
--     updated_time SMALLDATETIME not null,
--     updated_by   varchar(36),
--     CONSTRAINT fk_many_coach foreign key (id_coach) references Coach (id)
--         ON DELETE CASCADE,
--     CONSTRAINT fk_many_package_coa foreign key (id_package) references Package (id)
--         ON DELETE CASCADE
-- );

IF OBJECT_ID('Coach_Package', 'U') IS NULL
CREATE TABLE Coach_Package
(
    id_coach     varchar(36),
    id_package   varchar(36),
    created_time SMALLDATETIME not null,
    created_by   varchar(36),
    updated_time SMALLDATETIME not null,
    updated_by   varchar(36),
    PRIMARY KEY (id_coach, id_package),
    CONSTRAINT fk_many_coach foreign key (id_coach) references Coach (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_many_package_coa foreign key (id_package) references Package (id)
);

IF OBJECT_ID('Customer', 'U') IS NULL
CREATE TABLE Customer
(
    id           varchar(36) primary key,
    name         nvarchar(255) not null,
    phone_number varchar(255)  not null,
    email        varchar(255)  not null,
    created_time SMALLDATETIME not null,
    created_by   varchar(36),
    updated_time SMALLDATETIME not null,
    updated_by   varchar(36)
);

-- IF OBJECT_ID('Customer_Package', 'U') IS NULL
-- CREATE TABLE Customer_Package
-- (
--     id           varchar(36) primary key,
--     id_customer  varchar(36),
--     id_package   varchar(36),
--     created_time SMALLDATETIME not null,
--     created_by   varchar(36),
--     updated_time SMALLDATETIME not null,
--     updated_by   varchar(36),
--     CONSTRAINT fk_many_customer foreign key (id_customer) references Coach (id)
--         ON DELETE CASCADE,
--     CONSTRAINT fk_many_package_cus foreign key (id_package) references Package (id)
--         ON DELETE CASCADE
-- )

IF OBJECT_ID('Customer_Package', 'U') IS NULL
CREATE TABLE Customer_Package
(
    id_customer  varchar(36),
    id_package   varchar(36),
    created_time SMALLDATETIME not null,
    created_by   varchar(36),
    updated_time SMALLDATETIME not null,
    updated_by   varchar(36),
    PRIMARY KEY (id_customer, id_package),
    CONSTRAINT fk_many_customer foreign key (id_customer) references Coach (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_many_package_cus foreign key (id_package) references Package (id)
)