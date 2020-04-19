package site.morn.boot.message;

import site.morn.core.BeanConverter;

/**
 * 消息头解析器
 *
 * @param <S> 源消息
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public interface BroadcastMessageHeaderResolver<S> extends
    BeanConverter<S, BroadcastMessageHeaders> {

}
