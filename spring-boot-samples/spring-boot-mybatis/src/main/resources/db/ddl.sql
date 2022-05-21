CREATE TABLE `user`
(
    `id`          bigint(18) NOT NULL AUTO_INCREMENT,
    `user_name`   varchar(20)                                   DEFAULT NULL,
    `sex`         enum ('1','0')                                DEFAULT '0',
    `birth_day`   date                                          DEFAULT NULL,
    `education`   enum ('UNIVERSITY','HIGH','JUNIOR','PRIMARY') DEFAULT 'PRIMARY',
    `create_time` datetime                                      DEFAULT NULL,
    `update_time` datetime                                      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
