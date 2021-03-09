-- 20200329 User类增加uuid列、enable_flag列、create_user列、create_time列、update_user列、update_time列
ALTER TABLE `userdb`.`user` ADD COLUMN `uuid` VARCHAR(36) NOT NULL COMMENT 'uuid' AFTER `id`;
ALTER TABLE `userdb`.`user` ADD COLUMN `enable_flag` TINYINT(1) NOT NULL DEFAULT 0 COMMENT 'enable flag' AFTER `name`;
ALTER TABLE `userdb`.`user` ADD COLUMN `create_user` VARCHAR(32) DEFAULT NULL COMMENT 'create user' AFTER `enable_flag`;
ALTER TABLE `userdb`.`user` ADD COLUMN `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create time' AFTER `create_user`;
ALTER TABLE `userdb`.`user` ADD COLUMN `update_user` VARCHAR(32) DEFAULT NULL COMMENT 'update user' AFTER `create_time`;
ALTER TABLE `userdb`.`user` ADD COLUMN `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update time' AFTER `update_user`;