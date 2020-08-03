package site.morn.rest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import site.morn.core.None;

/**
 * REST响应
 * <p>标注REST响应消息需要后置处理，例如：对响应结果进行转义</p>
 *
 * @author yanhy
 * @since 1.2.2, 2020/7/15 1:44:4
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RestResponse {

    /**
     * 结果对象类型.
     */
    @AliasFor("returnClass")
    Class<?> value() default None.class;

    /**
     * 结果对象类型.
     */
    @AliasFor("value")
    Class<?> returnClass() default None.class;
}
