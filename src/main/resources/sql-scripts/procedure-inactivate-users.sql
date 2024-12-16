DROP PROCEDURE IF EXISTS inactivate_users;
DELIMITER $$
CREATE PROCEDURE inactivate_users ()
BEGIN
    UPDATE users u
    JOIN user_details ud ON u.id = ud.user_id
    SET u.status = 0, 
        u.date_updated = NOW(), 
        u.updated_by = 7
    WHERE DATEDIFF(NOW(), ud.last_login_date) / 30 >= 6;
END;
$$
DELIMITER ;
