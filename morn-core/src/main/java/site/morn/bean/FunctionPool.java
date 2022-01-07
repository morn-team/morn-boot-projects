package site.morn.bean;

import java.util.List;

/**
 * 函数池
 * <p>
 * 从{@link BeanCache}获取特定函数对象，提供简洁的检索API
 *
 * @author timely-rain
 * @since 1.2.2, 2021/9/27
 */
public interface FunctionPool {

  /**
   * 按特征检索函数
   *
   * @param functionFeature 函数特征
   * @return 函数集合
   */
  default List<FunctionHolder> functions(AnnotationFeature functionFeature) {
    return functions((AnnotationFeature) null, functionFeature);
  }

  /**
   * 按特征检索函数
   *
   * @param beanFeature     实例特征
   * @param functionFeature 函数特征
   * @return 函数集合
   */
  List<FunctionHolder> functions(AnnotationFeature beanFeature,
      AnnotationFeature functionFeature);


  /**
   * 按特征检索函数
   *
   * @param holders         实例持有者集合
   * @param functionFeature 函数特征
   * @return 函数集合
   */
  <T> List<FunctionHolder> functions(List<BeanHolder<T>> holders,
      AnnotationFeature functionFeature);
}
