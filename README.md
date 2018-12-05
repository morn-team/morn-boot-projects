[Bean]:https://github.com/morn-team/morn-boot-projects/wiki/Bean-Enhance-%E5%AE%9E%E4%BE%8B%E5%A2%9E%E5%BC%BA
[Exception]:https://github.com/morn-team/morn-boot-projects/wiki/Exception-Interpreter-%E5%BC%82%E5%B8%B8%E8%A7%A3%E9%87%8A
[Operation]:https://github.com/morn-team/morn-boot-projects/wiki/Operation-Log-%E6%93%8D%E4%BD%9C%E6%97%A5%E5%BF%97
[REST]:https://github.com/morn-team/morn-boot-projects/wiki/REST-Model---%E7%BB%9F%E4%B8%80REST%E6%A8%A1%E5%9E%8B
[Validation]:https://github.com/morn-team/morn-boot-projects/wiki/Validation-%E6%95%B0%E6%8D%AE%E6%A0%A1%E9%AA%8C

# Morn Boot Projects
基于SpringBoot的系列开发工具包，MornBoot不是SpringBoot的替代品，而是其向业务方向延伸。MornBoot初衷是提供简洁的、可拓展的通用业务实现，为SpringBoot项目提供一个良好的开端。MornBoot侧重于开发风格、标准、规范，提供可供参考的优秀实践。

## Features
* “零”配置实现通用业务
* 极简风格代码，良好可读性
* 友好的IDE提示信息
* 支持自定义扩展

## Getting Help
MornBoot没有强制依赖SpringBoot，你必须在项目中引入SpringBoot相关包，好处是你可以自由选择依赖版本。建议SpringBoot版本为1.5.X+，尚未测试最低兼容版本。

## Definitions
- `[dev]`表示该功能正在开发中
- `[expect]`表示该功能将在后续版本中推出

## Quick Start

当前版本：`1.0.0-SNAPSHOT`

```xml
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

### [Bean Enhance 实例增强][Bean]

MornBoot提供更多的注解对实例进行描述，使用`IdentifiedBeanCache`实例缓存检索需要的实例。

```
// 根据标签和目标类型检索Bean
BeanIdentify beanIdentify = BeanIdentify.builder().tags(ArrayUtils.merge("odd"))
    .target(TestBeanB.class)
    .build();
List<Object> search = identifiedBeanCache.beans(Object.class, beanIdentify);
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
Rests.ok();
```

输出结果

```
{
    "code": "morn.ok",
    "level": "info",
    "success": true,
    "message": "操作成功"
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
