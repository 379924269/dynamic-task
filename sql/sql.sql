CREATE TABLE `schedule_setting`
(
    `job_id`          INT(11)      NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `bean_name`       VARCHAR(255) NULL DEFAULT NULL COMMENT 'bean名称',
    `method_name`     VARCHAR(255) NULL DEFAULT NULL COMMENT '方法名称',
    `method_params`   VARCHAR(255) NULL DEFAULT NULL COMMENT '方法参数',
    `cron_expression` VARCHAR(255) NULL DEFAULT NULL COMMENT 'cron表达式',
    `remark`          VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
    `job_status`      INT(11)      NULL DEFAULT NULL COMMENT '状态(1正常 0暂停)',
    `gmt_create`      DATETIME     NULL DEFAULT NULL COMMENT '创建时间',
    `gmt_modified`    DATETIME     NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`job_id`)
)
    COLLATE = 'utf8_general_ci'
    ENGINE = InnoDB
    AUTO_INCREMENT = 2
;
INSERT INTO `schedule_setting`(`job_id`, `bean_name`, `method_name`, `method_params`, `cron_expression`, `remark`,
                               `job_status`, `gmt_create`, `gmt_modified`)
VALUES (1, 'taskDemo', 'taskByParams', 'aaa', '*/2 * * * * ?', '', 0, '2023-03-27 16:22:19', '2023-03-27 16:22:19');
