package site.morn.boot.template.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import site.morn.boot.template.TemplateConfiguration;

/**
 * 开启模板转义
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/15
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(TemplateConfiguration.class)
public @interface EnableTemplate {

}
