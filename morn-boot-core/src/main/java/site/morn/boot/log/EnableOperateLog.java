package site.morn.boot.log;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * 开启操作日志
 *
 * @author timely-rain
 * @see OperateConfiguration
 * @see site.morn.boot.autoconfigure.OperateAutoConfiguration
 * @since 1.2.2, 2021/7/5
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(OperateConfiguration.class)
public @interface EnableOperateLog {

}
