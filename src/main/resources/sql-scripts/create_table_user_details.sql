CREATE TABLE user_details (
    id INT AUTO_INCREMENT NOT NULL,
    districts VARCHAR(255),
    provinces VARCHAR(255),
    localities VARCHAR(255),
    uss VARCHAR(255),
    user_id VARCHAR(255) UNIQUE NOT NULL,
    last_login_date VARCHAR(255),
    password_last_change_date VARCHAR(255),
    profile_id VARCHAR(255),
    entry_point VARCHAR(255),
    partner_id VARCHAR(255),
    next_clean_date VARCHAR(255),
    was_cleaned VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;