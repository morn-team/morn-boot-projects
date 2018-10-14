package site.morn.boot.translate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;
import site.timely.translate.TranslateHolder;

/**
 * 翻译器注解
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/19
 * @since 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MornTranslator {

  /**
   * 获取翻译器名称
   *
   * @return 翻译器名称
   */
  String name() default TranslateHolder.DEFAULT;

  /**
   * 获取Bean名称
   *
   * @return Bean名称
   */
  String value() default "";
}
