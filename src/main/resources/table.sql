CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
	 fullname VARCHAR(100) NOT NULL,
	 `status` INT DEFAULT 0,
	 `role` INT DEFAULT 0,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Thời gian cập nhật, tự động thay đổi mỗi khi có cập nhật
    UNIQUE (username)
);

CREATE TABLE company (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(4000),
	 industry VARCHAR(100),
	 tax VARCHAR(100) NOT NULL,
	 email VARCHAR(100) NOT NULL,
	 phone VARCHAR(20) NOT NULL,
	 represent VARCHAR(100) NOT NULL,
	 address VARCHAR(500),
	 bank VARCHAR(100) NOT NULL,
	 `status` INT DEFAULT 0,
	 create_by INT NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by INT,
    updated_reason VARCHAR(1000),
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Th?i gian c?p nh?t, t? ??ng thay ??i m?i khi c� c?p nh?t
    bussiness_care INT,
    user_id INT NOT NULL,
	 UNIQUE (user_id)
);

CREATE TABLE wallet (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        company_id int,
                        balance int default 0,
                        last_topup_id int default -1,
                        status ENUM('-1', '0', '1') DEFAULT '0',
                        FOREIGN KEY (company_id) REFERENCES company(id)
                            ON DELETE CASCADE
                            ON UPDATE CASCADE
);

create table transaction_top_up (
                                    id int auto_increment primary key,
                                    wallet_id int not null,
                                    value int not null,
                                    wallet_balance int,
                                    status ENUM('-1', '0', '1') DEFAULT '0',
                                    bussiness_id int not null,
                                    transaction_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    INDEX idx_name (bussiness_id),
                                    foreign key (wallet_id) references wallet(id),
                                    foreign key (bussiness_id) references user(id)
);


select * from company c ;

ALTER TABLE wallet
    ADD COLUMN status ENUM('-1', '0', '1') DEFAULT '0';

select * from wallet;

