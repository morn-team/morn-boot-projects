package site.morn.boot.data.jpa;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * JPA查询条件
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public interface JpaCondition {

  /**
   * 等于
   *
   * @param name 实体属性名称，数据属性名称
   * @return 查询断言
   * @see javax.persistence.criteria.CriteriaBuilder#equal(Expression, Object)
   */
  Predicate eq(String name);

  /**
   * 等于
   *
   * @param name 实体属性名称
   * @param valueName 数据属性名称
   * @return 查询断言
   * @see javax.persistence.criteria.CriteriaBuilder#equal(Expression, Object)
   */
  Predicate eq(String name, String valueName);

  /**
   * 不等
   *
   * @param name 实体属性名称，数据属性名称
   * @return 查询断言
   * @see javax.persistence.criteria.CriteriaBuilder#notEqual(Expression, Object)
   */
  Predicate notEqual(String name);

  /**
   * 不等
   *
   * @param name 实体属性名称
   * @param valueName 数据属性名称
   * @return 查询断言
   * @see javax.persistence.criteria.CriteriaBuilder#notEqual(Expression, Object)
   */
  Predicate notEqual(String name, String valueName);

  /**
   * 在...内
   *
   * @param name 实体属性名称，数据属性名称
   * @return 查询断言
   * @see Expression#in(Object...)
   */
  Predicate in(String name);

  /**
   * 在...内
   *
   * @param name 实体属性名称
   * @param valueName 数据属性名称
   * @return 查询断言
   * @see Expression#in(Object...)
   */
  Predicate in(String name, String valueName);

  /**
   * 包含
   *
   * @param name 实体属性名称，数据属性名称
   * @return 查询断言
   * @see javax.persistence.criteria.CriteriaBuilder#like(Expression, String)
   */
  Predicate contain(String name);

  /**
   * 包含
   *
   * @param name 实体属性名称
   * @param valueName 数据属性名称
   * @return 查询断言
   * @see javax.persistence.criteria.CriteriaBuilder#like(Expression, String)
   */
  Predicate contain(String name, String valueName);

  /**
   * 以...开始
   *
   * @param name      实体属性名称，数据属性名称
   * @param valueName 数据属性名称
   * @return 查询断言
   * @see javax.persistence.criteria.CriteriaBuilder#like(Expression, String)
   */
  Predicate startWith(String name, String valueName);

  /**
   * 以...结束
   *
   * @param name      实体属性名称，数据属性名称
   * @param valueName 数据属性名称
   * @return 查询断言
   * @see javax.persistence.criteria.CriteriaBuilder#like(Expression, String)
   */
  Predicate endWith(String name, String valueName);

  /**
   * 以...开始
   *
   * @param name 实体属性名称，数据属性名称
   * @return 查询断言
   * @see javax.persistence.criteria.CriteriaBuilder#like(Expression, String)
   */
  default Predicate startWith(String name) {
    return startWith(name, name);
  }

  /**
   * 以...结束
   *
   * @param name 实体属性名称，数据属性名称
   * @return 查询断言
   * @see javax.persistence.criteria.CriteriaBuilder#like(Expression, String)
   */
  default Predicate endWith(String name) {
    return endWith(name, name);
  }
}
