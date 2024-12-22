CREATE TABLE user
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    username     VARCHAR(50)  NOT NULL,
    password     VARCHAR(255) NOT NULL,
    email        VARCHAR(100) NOT NULL,
    fullname     VARCHAR(100) NOT NULL,
    `status`     INT       DEFAULT 0,
    `role`       INT       DEFAULT 0,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Thời gian cập nhật, tự động thay đổi mỗi khi có cập nhật
    UNIQUE (username)
);

CREATE TABLE company
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    code           VARCHAR(50),
    name           VARCHAR(255) NOT NULL,
    description    VARCHAR(4000),
    industry       VARCHAR(100),
    tax            VARCHAR(100) NOT NULL,
    email          VARCHAR(100) NOT NULL,
    phone          VARCHAR(20)  NOT NULL,
    represent      VARCHAR(100) NOT NULL,
    address        VARCHAR(500),
    bank           VARCHAR(100) NOT NULL,
    `status`       INT       DEFAULT 0,
    create_by      INT          NOT NULL,
    created_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by     INT,
    updated_reason VARCHAR(1000),
    updated_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Th?i gian c?p nh?t, t? ??ng thay ??i m?i khi c� c?p nh?t
    bussiness_care INT,
    user_id        INT          NOT NULL,
    UNIQUE (user_id)
);

CREATE TABLE wallet
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    company_id    int,
    balance       int default 0,
    last_topup_id int default -1,
    status        int DEFAULT 0,
    FOREIGN KEY (company_id) REFERENCES company (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

create table transaction_top_up
(
    id               int auto_increment primary key,
    wallet_id        int not null,
    value            int not null,
    wallet_balance   int,
    status           int       DEFAULT 0,
    bussiness_id     int not null,
    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX            idx_name (bussiness_id),
    foreign key (wallet_id) references wallet (id),
    foreign key (bussiness_id) references user (id)
);


create table webservice_config
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    ws_name      varchar(50),
    api_url      varchar(500),
    msg_template varchar(1000),
    status       int DEFAULT 0,
    retry_num    int default 0
);
create table package_config
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    package_code    varchar(50)   not null,
    package_name    varchar(50)   not null,
    description     varchar(1000) not null,
    status          int       DEFAULT 0,
    add_value       int       default 0,
    ws_id           int           not null,
    valid_time      TIMESTAMP,
    create_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_user_id  int           not null,
    price           int       default 0,
    updated_user_id int,
    updated_reason  varchar(100),
    foreign key (ws_id) references webservice_config (id),
    foreign key (create_user_id) references user (id),
    foreign key (updated_user_id) references user (id),
    UNIQUE (package_code)
);

create table gamecode_model
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    model_name      varchar(50) not null,
    model_code      varchar(50) not null,
    discount        int       default 0,
    description     varchar(1000),
    status          int       DEFAULT 0,
    start_date      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valid_date      TIMESTAMP,
    create_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    create_user_id  int         not null,
    updated_date    TIMESTAMP,
    updated_user_id int,
    package_id      int         not null,
    number_required int         not null,
    foreign key (create_user_id) references user (id),
    foreign key (updated_user_id) references user (id),
    foreign key (package_id) references package (id)
);

create table transaction_buy_gamecode
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    company_id         int,
    wallet_before      int not null,
    wallet_after       int not null,
    wallet_consumption int not null,
    transaction_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_item         int not null,
    FOREIGN KEY (company_id) REFERENCES company (id)
);

create table service_config
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    company_id        int,
    gamecode_model_id int,
    create_date       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    start_date        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valid_date        TIMESTAMP,
    export_status     int       default 0,
    FOREIGN KEY (company_id) REFERENCES company (id),
    FOREIGN KEY (gamecode_model_id) REFERENCES gamecode_model (id)
);

create table configuration
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    `key`     varchar(100),
    content   varchar(1000),
    module_id int DEFAULT 0
);

create table gamecode_detail
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    gamecode    varchar(12),
    serial      VARCHAR(12),
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    start_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valid_date  TIMESTAMP,
    status      int       default 0
);