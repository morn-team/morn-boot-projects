package site.morn.bean.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 目标注解
 *
 * <p>目标是唯一的，通常代表实例的处理类型
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/25
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(ElementType.TYPE)
@Objective
public @interface Target {

  /**
   * 获取目标
   *
   * @return 目标
   */
  Class<?> value();
}
