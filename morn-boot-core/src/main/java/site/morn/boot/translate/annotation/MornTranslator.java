package site.morn.boot.translate.annotation;

import org.springframework.stereotype.Component;
import site.timely.translate.TranslateHolder;

import java.lang.annotation.*;

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
