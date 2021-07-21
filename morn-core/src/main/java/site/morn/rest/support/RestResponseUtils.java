package site.morn.rest.support;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.core.annotation.AnnotationUtils;
import site.morn.core.None;
import site.morn.rest.RestMessage;
import site.morn.rest.SerialMessage;
import site.morn.rest.annotation.RestResponse;

/**
 * REST响应工具类
 *
 * @author timely-rain
 * @since 1.2.2, 2020/7/25
 */
@UtilityClass
public class RestResponseUtils {

  /**
   * 判断交互消息是否为序列消息
   *
   * @param message 交互消息
   * @return 是否为序列消息
   */
  public boolean isSerialMessage(Object message, List<Class<?>> serialMessageClasses) {
    if (Objects.isNull(message)) {
      return false;
    }
    if (message instanceof SerialMessage) {
      return true;
    }
    for (Class<?> messageClass : serialMessageClasses) {
      if (messageClass.isAssignableFrom(message.getClass())) {
        return true;
      }
    }
    return false;
  }

  /**
   * 获取REST响应类
   *
   * @param responseClasses 响应类数组
   * @return REST响应类
   */
  public Class<?> getResponseClass(Class<?>... responseClasses) {
    for (Class<?> responseClass : responseClasses) {
      if (!None.class.isAssignableFrom(responseClass)) {
        return responseClass;
      }
    }
    return RestMessage.class;
  }

  /**
   * 获取REST响应类
   *
   * @param restResponse REST响应注解
   * @param globalClass  全局响应类
   * @return REST响应类
   */
  public Class<?> getResponseClass(RestResponse restResponse, Class<?> globalClass) {
    if (Objects.isNull(restResponse)) {
      return null;
    }
    return getResponseClass(restResponse.returnClass(), globalClass);
  }

  /**
   * 获取{@link RestResponse}注解
   * <p>当方法上不存在{@link RestResponse}注解时，尝试从类上获取</p>
   *
   * @param method 方法
   * @return RestResponse
   */
  public RestResponse getRestResponse(Method method) {
    RestResponse restResponse = AnnotationUtils.findAnnotation(method, RestResponse.class);
    if (Objects.isNull(restResponse)) {
      Class<?> declaringClass = method.getDeclaringClass();
      restResponse = AnnotationUtils.findAnnotation(declaringClass, RestResponse.class);
    }
    return restResponse;
  }
}
