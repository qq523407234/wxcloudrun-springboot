CREATE TABLE `Counters`
(
    `id`        int(11)   NOT NULL AUTO_INCREMENT,
    `count`     int(11)   NOT NULL DEFAULT '1',
    `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB AUTO_INCREMENT = 3 DEFAULT CHARSET = utf8;


CREATE TABLE `access_token_management`
(
    `app_id`       varchar(18)  NOT NULL DEFAULT '',
    `access_token` varchar(512) NOT NULL DEFAULT '',
    `in_time`      DATETIME     NULL,
    `out_time`     DATETIME     NULL,
    PRIMARY KEY (`app_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;
