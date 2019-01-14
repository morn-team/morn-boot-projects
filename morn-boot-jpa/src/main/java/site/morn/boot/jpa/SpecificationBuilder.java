package site.morn.boot.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA查询规格构建器
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
@Accessors(chain = true, fluent = true)
public final class SpecificationBuilder<M> {

  private JpaConditionPair<M> pair;

  private SpecificationBuilder() {
  }

  public static <T> SpecificationBuilder<T> builder() {
    return new SpecificationBuilder<>();
  }

  public SpecificationBuilder<M> pair(JpaConditionPair<M> pair) {
    this.pair = pair;
    return this;
  }

  /**
   * 构建JPA查询规格
   *
   * @return JPA查询规格
   */
  public Specification<M> specification(SpecificationFunction function) {
    return ((root, query, builder) ->
    {
      Reference reference = new Reference(root, query, builder);
      JpaBatchCondition condition = new JpaConditionSupport<M>().path(root).query(query)
          .builder(builder)
          .pair(pair);
      JpaPredicate predicate = new JpaPredicate().builder(builder);
      function.predicate(predicate, condition, reference);
      return predicate.clause();
    });
  }

  @FunctionalInterface
  public interface SpecificationFunction {

    void predicate(JpaPredicate predicate, JpaBatchCondition condition, Reference reference);
  }

  @RequiredArgsConstructor
  public static class Reference {

    private final Path<?> path;

    private final CriteriaQuery<?> query;

    private final CriteriaBuilder builder;
  }
}
