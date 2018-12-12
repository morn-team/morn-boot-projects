package site.morn.boot.web;

import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.MediaType;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.exception.ApplicationMessage;
import site.morn.exception.ExceptionInterpreter;
import site.morn.rest.Rests;

/**
 * 全局异常处理切面
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/12
 */
@Aspect
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlerAspect {

  /**
   * 实例缓存
   */
  private final IdentifiedBeanCache beanCache;

  /**
   * 全局异常处理切点
   */
  @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
  public void pointcut() {
    throw new UnsupportedOperationException("全局异常处理切点：切点方法不允许调用");
  }

  @Around("pointcut()")
  public Object aroundHandler(ProceedingJoinPoint point) {
    // 获取ExceptionHandler的响应参数
    HttpServletResponse response = getArgument(point.getArgs(), HttpServletResponse.class);
    if (Objects.nonNull(response)) {
      response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
    // 获取ExceptionHandler的异常参数
    Exception exception = getArgument(point.getArgs(), Exception.class);
    if (Objects.isNull(exception)) {
      log.warn("全局异常处理失败：ExceptionHandler必须包含Exception或其子类类型的参数");
      return proceed(point);
    }
    // 获取异常解释器
    ExceptionInterpreter interpreter = beanCache
        .bean(ExceptionInterpreter.class, exception.getClass());
    if (Objects.isNull(interpreter)) {
      log.debug("全局异常处理失败：尚未发现处理{}的异常解释器", exception.getClass().getSimpleName());
      return proceed(point);
    }
    // 将异常解释为应用消息
    ApplicationMessage message = interpreter.resolve(exception);
    // 将应用消息转换为REST模型
    return Rests.buildError().code(message.getCode())
        .message(message.getMessage()).generate();
  }

  private Object proceed(ProceedingJoinPoint point) {
    try {
      return point.proceed();
    } catch (Throwable throwable) {
      log.error(throwable.getMessage(), throwable);
      return null;
    }
  }

  private <T> T getArgument(Object[] arguments, Class<T> cls) {
    for (Object argument : arguments) {
      if (Objects.isNull(argument)) {
        continue;
      }
      if (cls.isAssignableFrom(argument.getClass())) {
        return (T) argument;
      }
    }
    return null;
  }
}
