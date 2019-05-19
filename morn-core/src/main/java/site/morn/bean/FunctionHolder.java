package site.morn.bean;

import java.lang.reflect.Method;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 方法持有者
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/17
 */
@NoArgsConstructor
@Getter
@Setter
public class FunctionHolder extends AnnotationIdentifyCase implements AnnotationIdentify {

  /**
   * 实例
   */
  protected Object bean;

  /**
   * 方法
   */
  protected Method method;

  /**
   * 参数类型
   */
  protected Class<?>[] parameterTypes;

  public FunctionHolder(Object bean, AnnotationIdentify identify, Method method,
      Class<?>[] parameterTypes) {
    super(identify);
    this.bean = bean;
    this.method = method;
    this.parameterTypes = parameterTypes;
  }
}
