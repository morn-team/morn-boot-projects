package site.morn.core;

import java.util.concurrent.Callable;

/**
 * 实例生产者
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/16
 */
@FunctionalInterface
public interface BeanProducer<T> extends Callable<T> {

  /**
   * 生产
   *
   * @return 实例
   */
  T product();

  @Override
  default T call() {
    return product();
  }
}
