package site.morn.boot.message;

import site.morn.core.BeanConverter;

/**
 * 消息头转换器
 *
 * @param <T> 目标类型
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public interface BroadcastMessageHeaderConverter<T> extends
    BeanConverter<BroadcastMessageHeaders, T> {

  @Override
  default T convert(BroadcastMessageHeaders source) {
    return null;
  }
}
