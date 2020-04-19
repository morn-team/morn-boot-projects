package site.morn.bean.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 目标类注解
 *
 * <p>目标类是唯一的，通常代表实例的输出类型
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ElementType.TYPE, ElementType.METHOD})
public @interface Target {

  /**
   * 获取目标类
   *
   * @return 目标类
   */
  Class<?> value();
}
