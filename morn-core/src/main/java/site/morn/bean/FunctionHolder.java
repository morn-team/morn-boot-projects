package site.morn.bean;

import java.lang.reflect.Method;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;
import site.morn.bean.support.SimpleAnnotationFeature;

/**
 * 方法持有者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/17
 */
@NoArgsConstructor
@Getter
@Setter
public class FunctionHolder extends SimpleAnnotationFeature implements AnnotationFeature {

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

  public FunctionHolder(Object bean, AnnotationFeature feature, Method method,
      Class<?>[] parameterTypes) {
    super(feature);
    this.bean = bean;
    this.method = method;
    this.parameterTypes = parameterTypes;
  }

  public String getBeanName() {
    return StringUtils.uncapitalize(bean.getClass().getSimpleName());
  }

  public String getBeanPath() {
    return bean.getClass().getName();
  }

  /**
   * 获取方法路径
   *
   * @return 方法路径
   */
  public String getMethodPath() {
    return getBeanPath() + "." + method.getName();
  }
}
