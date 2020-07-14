package site.morn.boot.response.config;

import org.springframework.context.annotation.Import;
import site.morn.boot.notify.NotifyConfiguration;

import java.lang.annotation.*;

/**
 * 开启响应结果转换配置.
 *
 * @author yanhy
 * @since 2020-07-15 1:54:38
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(RestConfiguration.class)
public @interface EnableRestConverter {
}
