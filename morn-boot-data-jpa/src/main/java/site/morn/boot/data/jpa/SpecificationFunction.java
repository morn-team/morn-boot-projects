package site.morn.boot.data.jpa;

/**
 * JPA查询规格函数
 *
 * @author timely-rain
 * @since 1.0.0, 2019/5/15
 */
@FunctionalInterface
public interface SpecificationFunction {

  /**
   * 查询规格
   *
   * @param reference 引用对象
   * @param restrain 约束构建
   * @param condition 条件构建
   */
  void predicate(JpaReference<?> reference, JpaPredicate restrain, JpaBatchCondition condition);
}
