# Morn Boot Projects
基于SpringBoot的系列开发工具包，MornBoot不是SpringBoot的替代品，而是其向业务方向延伸。MornBoot初衷是提供简洁的、可拓展的通用业务实现。

## Features
* “零”配置实现通用业务
* 极简风格代码，良好可读性
* 友好的IDE提示信息
* 支持自定义扩展

## Getting Help
MornBoot没有强制依赖SpringBoot，你必须在项目中引入SpringBoot相关包，好处是你可以自由选择依赖版本。建议SpringBoot版本为1.5.X+，目前为止MornBoot的兼容性是很好的，可以在低版本中运行。

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
#自动处理MySQL异常（开发中）
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

实体类：User.java

```
@NotNull
@Size(min = 4, max = 32) // 支持javax.validation注解
private String username;

@NotNull
@Size(min = 4, max = 32) // 支持javax.validation注解
private String password;
```

控制器：LoginController.java

```
@PostMapping("/login")
public Object login(@Valid User user) {
    return Rests.ok(); // 这里可以是任意返回值
}
```

异常处理类：

```
@ExceptionHandler
@ResponseBody
public Object doResolveException(Exception e) {
  // 解析异常
  ExceptionMessage exceptionMessage = exceptionProcessor.process(e);
  System.out.println(exceptionMessage.getMessage());
  return Rests.ok(); // 这里可以是任意返回值
}
```


发送请求：

```
curl -X POST \
  http://localhost:8080/login \
  -d password=123
```

输出结果：

```
user.password个数必须在4和32之间,user.username不能为null
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
// 4
Rests.buildOk().convert(Foo.class);
```

Result

```
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
// 4
```

### 国际化
