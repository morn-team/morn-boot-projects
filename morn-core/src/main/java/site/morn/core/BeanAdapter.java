package site.morn.core;

/**
 * 实例适配器
 *
 * @author TianGanLin
 * @since 0.0.1-SNAPSHOT, 2019/4/29
 */
@FunctionalInterface
public interface BeanAdapter<S> {

  /**
   * 适配
   *
   * @param source 源
   * @return 目标
   */
  S adaption(S source);
}
