ALTER TABLE `dreams_db`.`profiles` 
ADD COLUMN `status` VARCHAR(45) NOT NULL DEFAULT '1' AFTER `description`;

UPDATE `dreams_db`.`profiles` SET `status` = '0' WHERE (`id` = '15');

INSERT INTO `dreams_db`.`profiles` (`name`, `description`) VALUES ('MISAU', 'Agente do MISAU');

INSERT INTO `dreams_db`.`profiles` (`name`, `description`) VALUES ('M&E_DOADOR', 'Monitoria e Avaliação do Doador');