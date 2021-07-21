package site.morn.boot.message;

import java.util.concurrent.CompletableFuture;

/**
 * 消息解析操作
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/12
 */
public interface BroadcastMessageResolvingOperations<T> {

  /**
   * 异步解析消息
   *
   * @param message 消息
   * @return 是否解析成功
   */
  CompletableFuture<Boolean> asyncResolve(T message);

  /**
   * 同步解析消息
   *
   * @param message 消息
   * @return 是否解析成功
   */
  boolean syncResolve(T message);
}
