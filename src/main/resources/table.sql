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