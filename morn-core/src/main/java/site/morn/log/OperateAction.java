package site.morn.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作行为
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OperateAction {

  /**
   * 操作名称
   */
  String value();

  /**
   * 例外异常类型
   */
  Class<? extends Throwable>[] excepts() default {};
}
