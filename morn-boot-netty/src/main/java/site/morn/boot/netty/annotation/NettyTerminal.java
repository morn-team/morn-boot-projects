package site.morn.boot.netty.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import site.morn.boot.netty.constant.TerminalTypeConstants;

/**
 * Netty终端注解
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/5
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NettyTerminal {

  /**
   * 终端类型
   *
   * @see TerminalTypeConstants
   */
  String value();
}
