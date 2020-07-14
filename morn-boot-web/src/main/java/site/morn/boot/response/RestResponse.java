package site.morn.boot.response;

import org.springframework.core.annotation.AliasFor;

/**
 * 确定想要的转换后的响应结果对象.
 *
 * @author yanhy
 * @since 2020-07-15 1:44:4
 */
public @interface RestResponse {

    /**
     * 结果对象类型.
     */
    @AliasFor("name")
    Class<?> value();

    /**
     * 结果对象类型.
     */
    @AliasFor("value")
    Class<?> name();

}
