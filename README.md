# Morn Boot Projects
基于SpringBoot的系列开发工具包，MornBoot不是SpringBoot的替代品，而是其向业务方向延伸。MornBoot初衷是提供简洁的、可拓展的通用业务实现。

## Features
* “零”配置实现通用业务
* 极简风格代码，良好可读性
* 友好的IDE提示信息
* 支持自定义扩展

## Getting Help
MornBoot没有强制依赖SpringBoot，你必须在项目中引入SpringBoot相关包，好处是你可以自由选择依赖版本。建议SpringBoot版本为1.5.X+，目前为止MornBoot的兼容性是很好的，可以在低版本中运行。

## Definitions
- `[dev]`表示该功能正在开发中
- `[expect]`表示该功能将在后续版本中推出

## Quick Start
除了`morn-boot-autoconfigure`外都是可选包。Download the jar through Maven:

```xml
<!--支持自动化配置-->
<dependency>
  <groupId>site.morn.boot</groupId>
  <artifactId>morn-boot-autoconfigure</artifactId>
  <version>${morn.version}</version>
</dependency>
<!--Web应用相关-->
<dependency>
  <groupId>site.morn.boot</groupId>
  <artifactId>morn-boot-web</artifactId>
  <version>${morn.version}</version>
</dependency>
<!--数据校验-->
<dependency>
  <groupId>site.morn.boot</groupId>
  <artifactId>morn-boot-validator</artifactId>
  <version>${morn.version}</version>
</dependency>
```

## Autoconfigure
application.properties

```
#开启数据校验
morn.validator.enabled=true
#开启国际化
morn.translator.enabled=true
#开启异常处理
morn.exception.enabled=true
#自动处理数据绑定异常
morn.exception.bind.enabled=true
#自动处理MySQL异常 [expect]
morn.exception.mysql.enabled=true
```

## Example

### 数据校验
配置

```
#开启异常处理
morn.exception.enabled=true
#自动处理数据绑定异常
morn.exception.bind.enabled=true
```

Entity - 使用javax.validation注解约束属性

```
@NotNull
@Size(min = 4, max = 32)
private String username;

@NotNull
@Size(min = 4, max = 32)
private String password;
```

Controller - 使用Valid注解校验参数

```
@PostMapping("/login")
public Object login(@Valid User user) {
    return Rests.ok(); // 校验失败时，此处代码不会执行
}
```

ExceptionHandler - 使用ExceptionMessage解析校验结果

> 通常使用Spring容器注入ExceptionMessage

```
@ExceptionHandler
@ResponseBody
public Object doResolveException(Exception e) {
  // 解析异常
  ExceptionMessage exceptionMessage = exceptionProcessor.process(e);
  System.out.println(exceptionMessage.getMessage());
  return Rests.buildError().from(exceptionMessage); // [dev] 返回json格式的提示信息 
  // [expect] 在后续版本中，不需要在ExceptionHandler编写任何代码，即可处理此类异常
}
```


发送请求：

```
curl -X POST \
  http://localhost:8080/login \
  -d password=123
```

输出结果：

> 输出内容支持国际化，属性的国际化将在后续版本中支持

```
user.password个数必须在4和32之间,user.username不能为null

密码个数必须在4和32之间,用户名不能为null [dev]
```

### Rests
配置

```
#开启国际化
morn.translator.enabled=true
```

Controller returns

```
// 1
Rests.ok();
// 2
Rests.error();
// 3
List<User> users = searchUser();
Rests.ok(users);
// 4 [dev]
Rests.buildOk().from(foo);
// 5 [dev]
Rests.buildOk().to(Foo.class);
```

Result

```json
// 1
{
    "code": "morn.ok",
    "level": "info",
    "success": true,
    "message": "操作成功"
}
// 2
{
    "code": "morn.error",
    "level": "error",
    "success": false,
    "message": "操作失败"
}
// 3
{
    "code": "morn.ok",
    "data": [/*user1*/{}, /*user2*/{}],
    "level": "info",
    "success": true,
    "message": "操作成功"
}
// 4
```

### 国际化
