package site.morn.boot.jpa;

import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 * JPA查询断言
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/15
 */
public class JpaPredicate {

  private final CriteriaBuilder builder;

  private Predicate predicate;

  public JpaPredicate(CriteriaBuilder builder) {
    this.builder = builder;
  }

  /**
   * 拼接And条件 Only merge restrictions
   *
   * @param restrictions 查询条件
   * @return Predicate
   * @see CriteriaBuilder
   */
  public Predicate mergeAnd(Predicate... restrictions) {
    return builder.and(filterNotNull(restrictions));
  }

  /**
   * 拼接Or条件 Only merge restrictions
   *
   * @param restrictions 查询条件
   * @return Predicate
   * @see CriteriaBuilder
   */
  public Predicate mergeOr(Predicate... restrictions) {
    return builder.or(filterNotNull(restrictions));
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
    Predicate[] predicates = filterNotNull(restrictions);
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
    Predicate[] predicates = filterNotNull(restrictions);
    Predicate or = builder.or(predicates);
    predicate = Objects.isNull(predicate) ? or : builder.or(predicate, or);
    return this;
  }

  public Predicate get() {
    return predicate;
  }

  private Predicate[] filterNotNull(Predicate... restrictions) {
    return Stream.of(restrictions).filter(Objects::nonNull).toArray(Predicate[]::new);
  }

}
