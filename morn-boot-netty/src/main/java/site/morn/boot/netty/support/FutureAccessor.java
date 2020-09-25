package site.morn.boot.netty.support;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.function.Consumer;
import lombok.Getter;

/**
 * 异步操作访问器
 *
 * @param <V> 操作对象类型
 * @author timely-rain
 * @since 1.2.2, 2020/9/15
 */
@Getter
public class FutureAccessor<V> {

  /**
   * 异步操作对象
   */
  private final Future<V> future;

  private FutureAccessor(Future<V> future) {
    this.future = future;
  }

  /**
   * 完成回调
   */
  public FutureAccessor<V> completeThen(Consumer<V> consumer) {
    future.addListener(completeListener(consumer));
    return this;
  }

  /**
   * 失败回调
   */
  public FutureAccessor<V> failureThen(Consumer<Throwable> consumer) {
    future.addListener(failureListener(consumer));
    return this;
  }

  /**
   * 成功回调
   */
  public FutureAccessor<V> successThen(Consumer<V> consumer) {
    future.addListener(successListener(consumer));
    return this;
  }

  /**
   * 创建完成状态监听器
   */
  public GenericFutureListener<Future<V>> completeListener(Consumer<V> consumer) {
    return f -> {
      if (f.isDone()) {
        V v = f.getNow();
        consumer.accept(v);
      }
    };
  }

  /**
   * 创建失败状态监听器
   */
  public GenericFutureListener<Future<V>> failureListener(Consumer<Throwable> consumer) {
    return f -> {
      if (!f.isSuccess()) {
        consumer.accept(f.cause());
      }
    };
  }

  /**
   * 创建成功状态监听器
   */
  public GenericFutureListener<Future<V>> successListener(Consumer<V> consumer) {
    return f -> {
      if (f.isSuccess()) {
        V v = f.getNow();
        consumer.accept(v);
      }
    };
  }

  /**
   * 生成异步操作访问器
   *
   * @param future 异步操作
   * @param <T>    操作类型
   * @return 异步操作访问器
   */
  public static <T> FutureAccessor<T> from(Future<T> future) {
    return new FutureAccessor<>(future);
  }
}
