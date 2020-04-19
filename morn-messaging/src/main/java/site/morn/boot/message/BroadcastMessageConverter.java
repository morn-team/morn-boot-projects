package site.morn.boot.message;

import site.morn.core.BeanConverter;

/**
 * 消息转换器
 *
 * @param <T> 目标类型
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public interface BroadcastMessageConverter<T> extends BeanConverter<BroadcastMessage<?>, T> {

}
