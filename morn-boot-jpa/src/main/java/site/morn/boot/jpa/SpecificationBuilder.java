package site.morn.boot.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.Specification;
import site.morn.util.TypeUtils;

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
  public static <T> Specification<T> specification(SimpleFunction<T> function) {
    return ((root, query, builder) ->
    {
      JpaRestrain restrain = new JpaRestrain(builder);
      function.predicate(root, query, builder, restrain);
      return restrain.get();
    });
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
      JpaRestrain restrain = new JpaRestrain(builder);
      function.predicate(reference, restrain, condition);
      return restrain.get();
    });
  }

  @FunctionalInterface
  public interface SpecificationFunction {

    void predicate(Reference reference, JpaRestrain restrain,
        JpaBatchCondition condition);
  }

  @FunctionalInterface
  public interface SimpleFunction<T> {

    void predicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
        JpaRestrain restrain);
  }

  @Accessors(fluent = true)
  @RequiredArgsConstructor
  @Getter
  public static class Reference {

    private final Path<?> path;

    private final CriteriaQuery<?> query;

    private final CriteriaBuilder builder;

    public <T> Root<T> root() {
      return TypeUtils.as(path);
    }
  }
}
