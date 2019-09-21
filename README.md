[Message]:https://github.com/morn-team/morn-boot-projects/wiki/Application-Message-%E5%BA%94%E7%94%A8%E6%B6%88%E6%81%AF
[Bean]:https://github.com/morn-team/morn-boot-projects/wiki/Bean-Enhance-%E5%AE%9E%E4%BE%8B%E5%A2%9E%E5%BC%BA
[Exception]:https://github.com/morn-team/morn-boot-projects/wiki/Exception-Interpreter-%E5%BC%82%E5%B8%B8%E8%A7%A3%E9%87%8A
[JPA]:https://github.com/morn-team/morn-boot-projects/wiki/JPA-Assist-%E6%8C%81%E4%B9%85%E5%8C%96%E8%BE%85%E5%8A%A9
[Operation]:https://github.com/morn-team/morn-boot-projects/wiki/Operation-Log-%E6%93%8D%E4%BD%9C%E6%97%A5%E5%BF%97
[REST]:https://github.com/morn-team/morn-boot-projects/wiki/REST-Model---%E7%BB%9F%E4%B8%80REST%E6%A8%A1%E5%9E%8B
[Validation]:https://github.com/morn-team/morn-boot-projects/wiki/Validation-%E6%95%B0%E6%8D%AE%E6%A0%A1%E9%AA%8C


# Morn Boot Projects
 
[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)
[![Build Status](https://travis-ci.com/morn-team/morn-boot-projects.svg?branch=master)](https://travis-ci.com/morn-team/morn-boot-projects)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/973f6c34502a461f86aecdf88d8b989f)](https://app.codacy.com/app/morn-team/morn-boot-projects?utm_source=github.com&utm_medium=referral&utm_content=morn-team/morn-boot-projects&utm_campaign=Badge_Grade_Dashboard)
[![codecov](https://codecov.io/gh/morn-team/morn-boot-projects/branch/master/graph/badge.svg)](https://codecov.io/gh/morn-team/morn-boot-projects)
[![LICENSE](https://img.shields.io/badge/license-Apache--2.0-brightgreen.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/996icu/996.ICU/blob/master/LICENSE)

MornBoot是基于SpringBoot的标准API框架，致力于为JavaWeb项目提供标准化API。MornBoot初衷是提供简洁的、可拓展的通用功能实现，为SpringBoot项目提供一个良好的开端。MornBoot侧重于开发风格、标准、规范，提供开箱即用的优秀实践。

> 如果你的所有项目都使用同一套API开发，那么更新、维护将变得多么简单！


## Features

* “零配置”轻量级框架
* 相同API，不同结果呈现
* 极简风格代码，良好可读性
* 友好的IDE提示信息


## Getting Help

MornBoot没有强制依赖SpringBoot，你必须在项目中引入SpringBoot相关包，好处是你可以自由选择依赖版本。建议SpringBoot版本为1.5.X+，尚未测试最低兼容版本。


## Quick Start

### 最新版本: `1.0.2`

Maven Dependency

```
<!--自动化配置-->
<dependency>
  <groupId>site.morn.boot</groupId>
  <artifactId>morn-boot-autoconfigure</artifactId>
  <version>${morn.version}</version>
</dependency>
<!--核心库-->
<dependency>
  <groupId>site.morn.boot</groupId>
  <artifactId>morn-boot-core</artifactId>
  <version>${morn.version}</version>
</dependency>
```


## Function

### [Application Message 应用消息][Message]

MornBoot提供快速构建应用提示消息和应用异常的工具类。使用`ApplicationMessages`生成应用消息：

```
ApplicationMessage message = ApplicationMessages.translateMessage("login.password-is-null");
log.info(message.toString());
// ApplicationMessage(code=login.password-is-null, message=登录密码不能为空, solution=请输入登录密码)
```

[更多示例][Message]

### [Bean Enhance 实例增强][Bean]

MornBoot提供更多的注解对实例进行描述，使用`BeanCaches`实例缓存**批量检索**需要的实例。在`2.1.0+`版本中，实例增强还支持对方法的缓存、检索和调用。

```
// 获取名为`caramel`的宠物
Pet caramel = BeanCaches.nameBean(Pet.class, "caramel");

// 获取标签为`small`的宠物
Pet small = (Pet) BeanCaches.tagBean(null, "small"); 

// 获取所有目标为`Food`的宠物
List<Pet> foods = BeanCaches.targetBeans(Pet.class, Food.class);

caramel.eat(new Food("fish")); // log：caramel在吃fish...
```

[更多示例][Bean]

### [Exception Interpreter 异常解释][Exception]

MornBoot可以对常见异常进行自动处理，当代码执行异常时，会自动捕获异常并解释为友好的提示信息。

MySQL唯一约束提示

```
{
    "code": "morn.error",
    "level": "error",
    "success": false,
    "message": "数据重复，user.username已存在"
}
```

[更多示例][Exception]

### [JPA Assist 持久化辅助][JPA]

MornBoot提供JPA相关辅助功能，以简化JPA标准查询的开发工作。

```
// WHERE id = 1 AND username = 'timely-rain'
// password为空，所以忽略
Predicate[] equalAll = condition.equalAll();

// WHERE id = 1 
// password为空，所以忽略
Predicate[] equals = condition.equals("id", "password");

// AND username LIKE '%timely%'
Predicate keywords = condition.contain("username", "keywords");
```

[更多示例][JPA]

### [Operation Log 操作日志][Operation]

MornBoot提供通用的注解及处理器，完成对操作日志的监听和处理。

```
// 操作分组
@OperateGroup("user")
public class UserController {

  // 新增操作
  @OperateAction("add")
  Map<String, Object> addUser(Map<String, Object> user) {
    OperateArguments.add(user.get("username"));
    return user;
  }

  // 更新操作
  @OperateAction("update")
  Map<String, Object> updateUser(Map<String, Object> user) {
    OperateArguments.add(user.get("username"));
    OperateArguments.add(user.get("password"));
    throw new RuntimeException("异常测试");
  }
}
```

[更多示例][Operation]

### [REST Model 统一REST模型][REST]

构建消息

```
RestBuilders.successMessage();
```

输出结果

```
{
    "code": "success",
    "data": null,
    "level": "info",
    "message": "操作成功",
    "success": true
}
```

[更多示例][REST]

### [Validation 数据校验][Validation]

校验方法

```
@PostMapping("/login")
public Object login(@Valid User user) {
    return null; // 校验失败时，此处代码不会执行
}
```

输出结果

```
{
    "code": "morn.error",
    "level": "error",
    "success": false,
    "message": "user.password个数必须在4和32之间,user.username不能为null"
}
```

[更多示例][Validation]
