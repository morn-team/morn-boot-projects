package site.morn.boot.netty.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import site.morn.boot.netty.constant.TerminalType;

/**
 * 终端
 *
 * @author timely-rain
 * @since 2.1.0, 2019/6/5
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Terminal {

  /**
   * 终端类型
   */
  TerminalType value();
}
