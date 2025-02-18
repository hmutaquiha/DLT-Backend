ALTER TABLE `dreams_db`.`beneficiaries` 
ADD COLUMN `vulnerable` INT NOT NULL DEFAULT '0' AFTER `completion_status`;
