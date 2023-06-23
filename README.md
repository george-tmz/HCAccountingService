# AccountingService 开发过程记录

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

## API接口设计与异常处理

### API接口设计

REST: Representational State Transfer（表述性状态转移）

http://en.wikipedia.org/wiki/Representational_state_transfer

参考资料：https://github.com/Microsoft/api-guidelines/blob/vNext/Guidelines.md

### URL的结构

- 易于理解
- REST API不用动词，url都是对资源的描述
- 版本控制

```
http://localhost:8000/v1/users/1
```

### 支持方法

| Method  | Description                                                                                                                | Is Idempotent |
|---------|----------------------------------------------------------------------------------------------------------------------------|---------------|
| GET     | Return the current value of an object                                                                                      | True          |
| PUT     | Replace an object, or create a named object, when applicable                                                               | True          |
| DELETE  | Delete an object                                                                                                           | True          |
| POST    | Create a new object based on the data provided, or submit a command                                                        | False         |
| HEAD    | Return metadata of an object for a GET response. Resources that support the GET method MAY support the HEAD method as well | True          |
| PATCH   | Apply a partial update to an object                                                                                        | False         |
| OPTIONS | Get information about a request; see below for details.                                                                    | True          |

- GET /tickets - Retrieves a list of tickets
- GET /tickets/12 - Retrieves a specific ticket
- POST /tickets - Creates a new ticket
- PUT /tickets/12 - Updates ticket #12
- PATCH /tickets/12 - Partially updates ticket #12
- DELETE /tickets/12 - Deletes ticket #12

### Response格式

#### 基本要求

- JSON属性应该用camelCased
- Service应该将JSON作为默认编码
- Service必须要支持application/json，并且将application/json作为默认response format

```json
{
  "user": {
    "id": 1,
    "name": "george"
  }
}
```

#### 错误请求的Response

一定要用HTTP status code

最简单的三类：

- OK 200

- Client side error：4XX
- Service side error： 5XX

### 单元测试
测试的名称应包含三部分
- 要测试的方法名称
- 测试方案
- 调用方案的预期行为

#### AAA Pattern
Arrange-act-assert 单元测试的常见模式
- Arrange：安排好所有先要条件和输入，根据需要进行创建和设置
- Act：对要测试的对象或者方法进行调用
- Assert：断言结果是否按预期进行

#### 编写单元测试
