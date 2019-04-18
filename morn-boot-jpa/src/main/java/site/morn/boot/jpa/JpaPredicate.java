package site.morn.boot.jpa;

import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import site.morn.util.ArrayUtils;

/**
 * JPA查询断言
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/15
 */
public class JpaPredicate {

  /**
   * 查询构建器
   */
  private final CriteriaBuilder builder;

  /**
   * 条件断言
   */
  private Predicate predicate;

  public JpaPredicate(CriteriaBuilder builder) {
    this.builder = builder;
  }

  /**
   * 将非空断言流转为数组
   *
   * @param predicates 断言数组
   * @return 断言数组
   */
  public static Predicate[] array(Predicate[] predicates) {
    return array(Stream.of(predicates));
  }

  /**
   * 将非空断言流转为数组
   *
   * @param stream 断言流
   * @return 断言数组
   */
  public static Predicate[] array(Stream<Predicate> stream) {
    return stream.filter(Objects::nonNull).toArray(Predicate[]::new);
  }

  /**
   * 拼接And条件 Only merge restrictions
   *
   * @param restrictions 查询条件
   * @return Predicate
   * @see CriteriaBuilder
   */
  public Predicate mergeAnd(Predicate... restrictions) {
    Predicate[] predicates = array(restrictions);
    if (ArrayUtils.isEmpty(predicates)) {
      return null;
    }
    return builder.and(predicates);
  }

  /**
   * 拼接Or条件 Only merge restrictions
   *
   * @param restrictions 查询条件
   * @return Predicate
   * @see CriteriaBuilder
   */
  public Predicate mergeOr(Predicate... restrictions) {
    Predicate[] predicates = array(restrictions);
    if (ArrayUtils.isEmpty(predicates)) {
      return null;
    }
    return builder.or(predicates);
  }

  /**
   * 拼接And条件至WHERE语句 merge the And restrictions to WHERE clause
   *
   * @param restrictions 查询条件
   * @return JPA Condition
   * @see CriteriaBuilder
   */
  public JpaPredicate applyAnd(Predicate... restrictions) {
    // 过滤空值
    Predicate[] predicates = array(restrictions);
    Predicate and = builder.and(predicates);
    predicate = Objects.isNull(predicate) ? and : builder.and(predicate, and);
    return this;
  }

  /**
   * 拼接Or条件至WHERE语句 merge the Or restrictions to WHERE clause
   *
   * @param restrictions 查询条件
   * @return JPA Condition
   * @see CriteriaBuilder
   */
  public JpaPredicate applyOr(Predicate... restrictions) {
    // 过滤空值
    Predicate[] predicates = array(restrictions);
    Predicate or = builder.or(predicates);
    predicate = Objects.isNull(predicate) ? or : builder.or(predicate, or);
    return this;
  }

  /**
   * 获取条件断言
   */
  public Predicate get() {
    return predicate;
  }

}
