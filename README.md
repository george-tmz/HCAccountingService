# AccountingService 开发过程记录

Spring boot 练习项目--计算器

## Spring boot 开发四大步

- 添加相应依赖
- 添加相应注解
- 编写代码
- 添加相应配置

## 项目代码结构与数据模型转换

### 项目代码组织结构

```text
└── src
    └── main
        └── java
            └── com.hardcore.accounting
              └── config
            	└── controller
            	└── converter
  	          └── manager
  	          └── dao
  	          └── external
            	└── model
            	└── exception
```

### 数据模型转换

#### 阿里分层领域模型规约参考

- DO（Data Object）：此对象与数据库表结构一一对应，通过 DAO 层向上传输数据源对象。
- DTO（Data Transfer Object）：数据传输对象，Service 或 Manager 向外传输的对象。
- BO（Business Object）：业务对象，由 Service 层输出的封装业务逻辑的对象。
- AO（Application Object）：应用对象，在 Web 层与 Service 层之间抽象的复用对象模型，极为贴近展示层，复用度不高。
- VO（View Object）：显示层对象，通常是 Web 向模板渲染引擎层传输的对象

#### 简单的三层数据模型

- persistence: 对应数据库表结构
- common: 对应Manager层使用
- service: 对应Controller层使用

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

JUnit5 = JUnit Platform + JUnit Jupiter + JUnit Vintage
https://junit.org/junit5/docs/current/user-guide/
Mockito
https://site.mockito.org/

##### annotation

```
@Mock

@ExtendWith(MockitoExtension.class)

@InjectMocks
```

### 引入CheckStyle

- pox.xml中添加依赖
- 创建checkStyle配置文件。放在resource包中
- idea安装CheckStyle插件
- 设置插件使用的检测标准

### Jcoco插件

检测单元测试覆盖率

### Spotbugs 插件

### 配置 GitHub CI workflow

### 用户密码加密

#### 基本加密

MD5(password)
HAS(password)

#### 加盐加密 Salt

随机生成一个盐```salt = UUID()```
```MD5(password + salt)``` -> database

#### 多次迭代加盐加密

Round 1: MD5(password + salt) -> s1

Round 2: MD5(s1 + salt) -> s2

Round 3: MD5(s2 + salt) -> s3

### 使用Shrio实现登录功能

官网 https://shiro.apache.org/

#### 导入依赖

```xml

<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring</artifactId>
    <version>1.4.1</version>
</dependency>
```

#### 编写Shrio配置类

- 定义Realm类
- 定义SecurityManager

#### 定义filter

Shiro内置Filter，可以实现权限相关的拦截器

常用Filter：

anon： 无需认证登录可以访问

authc：必须认证才可以访问

user：如果使用rememberMe的功能可以直接访问

perms：该资源必须得到资源权限才可以访问

role：该资源必须得到角色权限才可以访问

| 过滤器简       | 对应的java类                                                         |
|------------|------------------------------------------------------------------|
| anon	      | org.apache.shiro.web.filter.authc.AnonymousFilter                |
| authc      | org.apache.shiro.web.filter.authc.FormAuthenticationFilter       |
| authcBasic | org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter  |
| perms      | org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter |
| port       | org.apache.shiro.web.filter.authz.PortFilter                     |
| rest       | org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter     |
| roles      | org.apache.shiro.web.filter.authz.RolesAuthorizationFilter       |
| ssl        | org.apache.shiro.web.filter.authz.SslFilter                      |
| user       | org.apache.shiro.web.filter.authc.UserFilter                     |
| logout     | org.apache.shiro.web.filter.authc.LogoutFilter                   |

