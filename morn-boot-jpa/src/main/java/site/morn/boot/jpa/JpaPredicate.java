package site.morn.boot.jpa;

import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * site.morn.boot.jpa
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/15
 */
@Accessors(chain = true, fluent = true)
public class JpaPredicate {

  private Predicate clausePredicate;

  @Setter
  private CriteriaBuilder builder;

  /**
   * 拼接And条件至WHERE语句 merge the And restrictions to WHERE clause
   *
   * @param restrictions 查询条件
   * @return JPA Condition
   * @see CriteriaBuilder
   */
  public JpaPredicate clauseAnd(Predicate... restrictions) {
    // 过滤空值
    Predicate[] predicates = Stream.of(restrictions).filter(Objects::nonNull)
        .toArray(Predicate[]::new);
    Predicate and = builder.and(predicates);
    clausePredicate = Objects.isNull(clausePredicate) ? and : builder.and(clausePredicate, and);
    return this;
  }

  /**
   * 拼接Or条件至WHERE语句 merge the Or restrictions to WHERE clause
   *
   * @param restrictions 查询条件
   * @return JPA Condition
   * @see CriteriaBuilder
   */
  public JpaPredicate clauseOr(Predicate... restrictions) {
    // 过滤空值
    Predicate[] predicates = Stream.of(restrictions).filter(Objects::nonNull)
        .toArray(Predicate[]::new);
    Predicate or = builder.or(predicates);
    clausePredicate = Objects.isNull(clausePredicate) ? or : builder.or(clausePredicate, or);
    return this;
  }

  /**
   * 拼接And条件 Only merge restrictions
   *
   * @param restrictions 查询条件
   * @return Predicate
   * @see CriteriaBuilder
   */
  public Predicate mergeAnd(Predicate... restrictions) {
    return builder.and(restrictions);
  }

  /**
   * 拼接Or条件 Only merge restrictions
   *
   * @param restrictions 查询条件
   * @return Predicate
   * @see CriteriaBuilder
   */
  public Predicate mergeOr(Predicate... restrictions) {
    return builder.or(restrictions);
  }

  public Predicate clause() {
    return clausePredicate;
  }

  private Predicate[] filterNotNull(Predicate... restrictions) {
    return Stream.of(restrictions).filter(Objects::nonNull).toArray(Predicate[]::new);
  }

}
