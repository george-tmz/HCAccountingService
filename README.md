# HCAccountingService

Spring boot 练习项目--计算器

## Spring boot 开发四大步

- 添加相应依赖
- 添加相应注解
- 编写代码
- 添加相应配置

## 数据库初始化SQL

```mysql
CREATE DATABASE hardcore_test;

USE hardcore_test;
DROP TABLE IF EXISTS `hcas_user`;
CREATE TABLE `hcas_user`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT,
    `username`    varchar(64)         NOT NULL COMMENT 'user name',
    `password`    varchar(64)         NOT NULL,
    `create_time` datetime            NOT NULL,
    `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY `pk_id` (`id`),
    UNIQUE KEY `uk_username` (`username`)
);

INSERT hcas_user value (NULL, 'hardcore_admin', 'hardcore', NOW(), NULL);

SELECT *
from hardcore_test.hcas_user;

UPDATE hcas_user
SET password='accounting'
where username = 'hardcore_admin';
```
