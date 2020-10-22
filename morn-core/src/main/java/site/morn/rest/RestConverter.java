package site.morn.rest;

import site.morn.core.BeanConverter;

/**
 * REST消息转换器
 * <p>用于任意类型之间相互转换</p>
 *
 * @author timely-rain
 * @since 1.0.0, 2018/7/25
 */
public interface RestConverter<S, T> extends BeanConverter<S, T> {

}
