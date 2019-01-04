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
import site.morn.exception.ApplicationMessage;
import site.morn.exception.ExceptionProcessor;
import site.morn.rest.RestBuilders;
import site.morn.util.ArrayUtils;

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
   * 异常处理器
   */
  private final ExceptionProcessor exceptionProcessor;

  /**
   * 全局异常处理切点
   */
  @Pointcut("@annotation(org.springframework.web.bind.annotation.ExceptionHandler)")
  public void pointcut() {
  }

  @Around("pointcut()")
  public Object aroundHandler(ProceedingJoinPoint point) {
    // 获取ExceptionHandler的响应参数
    HttpServletResponse response = ArrayUtils.first(point.getArgs(), HttpServletResponse.class);
    if (Objects.nonNull(response)) {
      response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
    // 获取ExceptionHandler的异常参数
    Exception exception = ArrayUtils.first(point.getArgs(), Exception.class);
    if (Objects.isNull(exception)) {
      log.warn("全局异常处理失败：ExceptionHandler必须包含Exception或其子类类型的参数");
      return proceed(point);
    }
    // 将异常解释为应用消息
    ApplicationMessage message = exceptionProcessor.process(exception);
    if (Objects.isNull(message)) {
      log.debug("全局异常处理失败：无法处理{}", exception.getClass().getSimpleName());
      return proceed(point);
    }
    // 将应用消息转换为REST模型
    return RestBuilders.errorBuilder().code(message.getCode()).message(message.getMessage())
        .build();
  }

  /**
   * 执行切点
   *
   * @param point 切点
   * @return 切点返回值
   */
  private Object proceed(ProceedingJoinPoint point) {
    try {
      return point.proceed();
    } catch (Throwable throwable) {
      log.error(throwable.getMessage(), throwable);
      return null;
    }
  }
}
