package site.morn.core;

/**
 * 实例处理器
 *
 * @author timely-rain
 * @since 0.0.1-SNAPSHOT, 2019/5/7
 */
@FunctionalInterface
public interface BeanProcessor<T> {

  /**
   * 处理
   *
   * @param source 源
   */
  void handle(T source);
}
