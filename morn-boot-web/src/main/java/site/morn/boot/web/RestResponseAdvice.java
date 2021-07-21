package site.morn.boot.web;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import site.morn.boot.rest.RestBuilders;
import site.morn.boot.rest.RestProperties;
import site.morn.exception.ApplicationMessage;
import site.morn.exception.ExceptionInterpreterService;
import site.morn.rest.RestConverterService;
import site.morn.rest.RestMessage;
import site.morn.rest.SerialMessage;
import site.morn.rest.SerialMessageRegistry;
import site.morn.rest.annotation.RestResponse;
import site.morn.rest.support.RestResponseUtils;

/**
 * REST响应统一处理
 *
 * @author timely-rain
 * @since 1.2.2, 2020/7/25
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestResponseAdvice implements ResponseBodyAdvice<Object> {

  /**
   * 消息配置项
   */
  private final RestProperties properties;

  /**
   * 消息转换服务
   */
  private final RestConverterService converterService;

  /**
   * 异常处理者
   */
  private final ExceptionInterpreterService interpreterService;

  /**
   * 序列消息注册表
   */
  private final SerialMessageRegistry registry;

  @Override
  public boolean supports(MethodParameter returnType,
      Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body, MethodParameter returnType,
      MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request, ServerHttpResponse response) {
    Object responseBody = body;
    Method method = returnType.getMethod(); // 获取当前执行的方法
    Assert.notNull(method, "REST|Failure:执行方法不能为空");
    ExceptionHandler exceptionHandler = AnnotationUtils
        .findAnnotation(method, ExceptionHandler.class);
    if (Objects.nonNull(exceptionHandler)) { // 全局异常处理方法中，REST消息已经处理过了
      return responseBody;
    }
    RestResponse restResponse = RestResponseUtils.getRestResponse(method);
    if (!needConvert(restResponse)) {
      return responseBody;
    }
    if (needSerial(responseBody)) { // 将非序列消息组装为REST消息
      responseBody = RestBuilders.successMessage(responseBody);
    }
    if (Objects.isNull(responseBody)) {
      return null;
    }
    // 从REST方法和REST配置项中推断响应类
    Class<?> responseClass = RestResponseUtils
        .getResponseClass(restResponse, properties.getResponseClass());
    // 将响应消息转换为指定类型的响应消息
    return deduce(responseBody, responseClass);
  }

  /**
   * 全局异常处理
   */
  @ResponseBody
  @ExceptionHandler
  public Object handleException(Exception e, HandlerMethod handlerMethod) {
    RestMessage restMessage;
    log.error("REST|Error:" + e.toString(), e);
    // 将异常解释为应用消息
    ApplicationMessage am = interpreterService.interpret(e);
    RestResponse restResponse = RestResponseUtils.getRestResponse(handlerMethod.getMethod());
    if (!needConvert(restResponse)) { // 判断异常消息是否需要转义为序列消息
      // 直接返回异常信息
      return Optional.ofNullable(am).map(ApplicationMessage::getMessage)
          .orElse(StringUtils.isEmpty(e.getMessage()) ? e.toString() : e.getMessage());
    }
    if (Objects.isNull(am)) { // 无法将异常解释为ApplicationMessage，则构建一个默认REST消息
      log.error("REST|ErrorMiss:{}", e.getClass().getSimpleName());
      String message = Optional.ofNullable(e.getMessage()).orElse(e.toString());
      restMessage = RestBuilders.failureBuilder().message(message).build();
    } else {
      log.info("REST|ErrorHandle:" + am.toString());
      restMessage = converterService.transform(am, RestMessage.class);
    }
    Class<?> responseClass = RestResponseUtils
        .getResponseClass(restResponse, properties.getResponseClass());
    return deduce(restMessage, responseClass);
  }

  /**
   * 判断是否继续转换
   */
  private boolean needConvert(RestResponse restResponse) {
    return Objects.nonNull(restResponse) || properties.isForceSerial();
  }

  /**
   * 判断是否需要序列处理，即将方法返回值包装为{@link SerialMessage}
   *
   * @param returnObject REST方法返回值
   * @return 是否需要序列处理
   */
  private boolean needSerial(Object returnObject) {
    return !RestResponseUtils.isSerialMessage(returnObject, registry.getSerialMessageClasses());
  }

  /**
   * 按预期类型转换REST消息
   */
  private Object deduce(Object responseMessage, Class<?> responseClass) {
    Object deduce = converterService.deduce(responseMessage, responseClass);
    if (!responseClass.isAssignableFrom(deduce.getClass())) {
      String msg = String.format(
          "计划响应类型为[%s]，当前响应类型为[%s]，请按提示注册合适的转换器/解析器。若当前响应类型符合预期，请全局配置%s或修改%s",
          responseClass.getSimpleName(), deduce.getClass().getSimpleName(),
          "\"morn.rest.response-class\"", "\"@RestResponse.value()\"");
      log.warn("REST|ErrorConvert:{}", msg);
      return RestBuilders.failureBuilder().code("rest.convert-error").message(msg).build();
    }
    return deduce;
  }
}
