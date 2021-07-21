# Morn Boot Projects

[![Maven Central](https://img.shields.io/maven-central/v/site.morn.boot/morn-boot-projects)](https://search.maven.org/search?q=morn-boot-projects)
[![Build Status](https://app.travis-ci.com/morn-team/morn-boot-projects.svg?branch=master)](https://app.travis-ci.com/morn-team/morn-boot-projects)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/314790ce1238478c8607ebfd5425a3af)](https://www.codacy.com/gh/morn-team/morn-boot-projects/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=morn-team/morn-boot-projects&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/morn-team/morn-boot-projects/branch/master/graph/badge.svg?token=YjvGgM8qf9)](https://codecov.io/gh/morn-team/morn-boot-projects)
[![LICENSE](https://img.shields.io/badge/license-Apache--2.0-brightgreen.svg)](https://www.apache.org/licenses/LICENSE-2.0)
![GitHub top language](https://img.shields.io/github/languages/top/morn-team/morn-boot-projects)

MornBoot是基于SpringBoot的标准API框架，致力于为JavaWeb项目提供标准化API。MornBoot初衷是提供简洁的、可拓展的通用功能实现，为SpringBoot项目提供一个良好的开端。MornBoot侧重于开发风格、标准、规范，提供开箱即用的优秀实践。

> 如果你的所有项目都使用同一套API开发，那么更新、维护将变得多么简单！

## Features

* “零配置”轻量级框架
* 相同API，不同结果呈现
* 极简风格代码，良好可读性
* 友好的IDE提示信息

## Getting Help

MornBoot没有强制依赖SpringBoot，你必须在项目中引入SpringBoot相关包，好处是你可以自由选择依赖版本。建议SpringBoot版本为2.1.X。

[ApplicationMessage]:https://github.com/morn-team/morn-boot-projects/wiki/ApplicationMessage-%E5%BA%94%E7%94%A8%E6%B6%88%E6%81%AF

[BeanEnhance]:https://github.com/morn-team/morn-boot-projects/wiki/BeanEnhance-%E5%AE%9E%E4%BE%8B%E5%A2%9E%E5%BC%BA

[CacheGroup]:https://github.com/morn-team/morn-boot-projects/wiki/CacheGroup-%E5%88%86%E7%BB%84%E7%BC%93%E5%AD%98

[Cipher]:https://github.com/morn-team/morn-boot-projects/wiki/Cipher-%E6%B6%88%E6%81%AF%E5%8A%A0%E5%AF%86

[ExceptionInterpreter]:https://github.com/morn-team/morn-boot-projects/wiki/ExceptionInterpreter-%E5%BC%82%E5%B8%B8%E8%A7%A3%E9%87%8A

[JpaAssist]:https://github.com/morn-team/morn-boot-projects/wiki/JpaAssist-JPA%E8%BE%85%E5%8A%A9

[JSON]:https://github.com/morn-team/morn-boot-projects/wiki/JSON-%E5%BA%8F%E5%88%97%E5%8C%96

[MessageQueue]:https://github.com/morn-team/morn-boot-projects/wiki/MessageQueue-%E6%B6%88%E6%81%AF%E9%98%9F%E5%88%97

[MornBoot]:https://github.com/morn-team/morn-boot-projects

[Notify]:https://github.com/morn-team/morn-boot-projects/wiki/Notify-%E7%B3%BB%E7%BB%9F%E9%80%9A%E7%9F%A5

[OperationLog]:https://github.com/morn-team/morn-boot-projects/wiki/OperationLog-%E6%93%8D%E4%BD%9C%E6%97%A5%E5%BF%97

[ParamsValidation]:https://github.com/morn-team/morn-boot-projects/wiki/ParamsValidation-%E6%95%B0%E6%8D%AE%E6%A0%A1%E9%AA%8C

[PersistFunction]:https://github.com/morn-team/morn-boot-projects/wiki/PersistFunction-%E6%8C%81%E4%B9%85%E5%8C%96%E5%87%BD%E6%95%B0

[RestMessage]:https://github.com/morn-team/morn-boot-projects/wiki/RestMessage-REST%E6%B6%88%E6%81%AF

[SpringBoot]:https://spring.io/projects/spring-boot

## Quick Start

### Maven Dependency

最新版本: [![Maven Central](https://img.shields.io/maven-central/v/site.morn.boot/morn-boot-projects)](https://search.maven.org/search?q=morn-boot-projects)

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

### 必要配置

SpringBootApplication

```
@EnableCaching // 开启缓存
```

## Reference

### Specification 规范

基于[SpringBoot][SpringBoot]提供常用业务组件的基础规范及组件，这些组件更类似优秀实践。
它们介于实际业务和Framework之间，同时这也是[MornBoot][MornBoot]框架的定位。 不同业务框架往往会开发各式各样的业务组件，功能大同小异，质量参差不齐，结构缺乏包容性。
[MornBoot][MornBoot]设计的初衷就是提供标准组件，替代这些业务组件，并提供足够高的扩展性以包容各种业务场景。

* [ApplicationMessage][ApplicationMessage]：应用消息
* [Notify][Notify]：系统通知
* [OperationLog][OperationLog]：操作日志
* [RestMessage][RestMessage]：REST消息

### CRUD 搬砖

主要提供`MVC`、`ORM`业务中，较为常见和基础的组件、规范。

* [CacheGroup][CacheGroup]：分组缓存
* [Cipher][Cipher]：消息加密
* [JSON][JSON]：序列化
* [ParamsValidation][ParamsValidation]：参数校验
* [PersistFunction][PersistFunction]：持久化函数

### Features 特性

主要提供[MornBoot][MornBoot]特有的特性、功能，[MornBoot][MornBoot]
中的许多组件依赖这些特性进行开发，部分特性拥有极高的扩展性，并不仅限于供[MornBoot][MornBoot]使用。部分特性的设计初衷就是让使用者依据自身业务框架进行补充和扩展。

* [BeanEnhance][BeanEnhance]：实例增强
* [ExceptionInterpreter][ExceptionInterpreter]：异常解释

### Framework 框架

主要提供主流开源框架的封装、扩展，提供更具业务化的组件，提升开发效率。这些组件并不是单纯的对框架进行使用，也提供了一些实践思路，和包容性的结构，以及对框架使用过程的优化和完善。

> 开源框架、中间件通常倾向于提高特性、功能、性能，而MornBoot则侧重提升框架使用体验和效率，并尽可能兼容足够多的框架能力。

* [JpaAssist][JpaAssist]：JPA辅助
* [MessageQueue][MessageQueue]：消息队列
