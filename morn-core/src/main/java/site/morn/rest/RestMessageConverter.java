package site.morn.rest;

/**
 * RestMessage转换器
 * <p>用于{@link RestMessage}与任意类型相互转换</p>
 *
 * @author timely-rain
 * @since 1.2.2, 2020/7/15
 */
public interface RestMessageConverter<T> extends RestConverter<RestMessage, T> {

}
