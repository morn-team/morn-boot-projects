package site.morn.boot.notify.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import site.morn.boot.notify.NotifyConfiguration;

/**
 * 开启系统通知
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(NotifyConfiguration.class)
public @interface EnableNotify {

}
