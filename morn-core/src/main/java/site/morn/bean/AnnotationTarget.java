package site.morn.bean;

/**
 * 注解目标
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/25
 */
public interface AnnotationTarget {

  /**
   * 获取目标
   *
   * @return 目标
   */
  Class<?> getTarget();
}
