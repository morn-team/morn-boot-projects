package site.morn.boot.jpa;

import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 * JPA约束
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/15
 */
public class JpaRestrain {

  private final CriteriaBuilder builder;

  private JpaPredicateCollector collector;

  public JpaRestrain(CriteriaBuilder builder) {
    this.builder = builder;
    collector = new JpaPredicateCollector();
  }

  /**
   * 拼接And条件 Only merge restrictions
   *
   * @param restrictions 查询条件
   * @return Predicate
   * @see CriteriaBuilder
   */
  public Predicate and(Predicate... restrictions) {
    return builder.and(restrictions);
  }

  /**
   * 拼接Or条件 Only merge restrictions
   *
   * @param restrictions 查询条件
   * @return Predicate
   * @see CriteriaBuilder
   */
  public Predicate or(Predicate... restrictions) {
    return builder.or(restrictions);
  }

  public JpaPredicateCollector collector() {
    return collector;
  }

  public Predicate get() {
    return collector().get();
  }

  private Predicate[] filterNotNull(Predicate... restrictions) {
    return Stream.of(restrictions).filter(Objects::nonNull).toArray(Predicate[]::new);
  }

  public class JpaPredicateCollector {

    private Predicate predicate;

    /**
     * 拼接And条件至WHERE语句 merge the And restrictions to WHERE clause
     *
     * @param restrictions 查询条件
     * @return JPA Condition
     * @see CriteriaBuilder
     */
    public JpaPredicateCollector and(Predicate... restrictions) {
      // 过滤空值
      Predicate[] predicates = Stream.of(restrictions).filter(Objects::nonNull)
          .toArray(Predicate[]::new);
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
    public JpaPredicateCollector or(Predicate... restrictions) {
      // 过滤空值
      Predicate[] predicates = Stream.of(restrictions).filter(Objects::nonNull)
          .toArray(Predicate[]::new);
      Predicate or = builder.or(predicates);
      predicate = Objects.isNull(predicate) ? or : builder.or(predicate, or);
      return this;
    }

    private Predicate get() {
      return predicate;
    }
  }

}
