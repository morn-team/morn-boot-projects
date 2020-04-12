package site.morn.boot.message;

import site.morn.core.BeanConverter;

/**
 * 消息结果转换器
 *
 * @param <S> 源结果类型
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public interface MessageResultConverter<S> extends BeanConverter<S, MessageResult> {

}
