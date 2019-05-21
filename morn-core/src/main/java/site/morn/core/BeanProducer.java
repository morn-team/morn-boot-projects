package site.morn.core;

/**
 * 实例生产者
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/16
 */
@FunctionalInterface
public interface BeanProducer<T> {

  /**
   * 生产
   *
   * @return 实例
   */
  T product();
}
