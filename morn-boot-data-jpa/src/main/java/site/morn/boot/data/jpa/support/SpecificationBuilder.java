package site.morn.boot.data.jpa.support;

import java.util.Objects;
import java.util.function.Consumer;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.Specification;
import site.morn.boot.data.jpa.JpaBatchCondition;
import site.morn.boot.data.jpa.JpaCommon;
import site.morn.boot.data.jpa.JpaParameter;
import site.morn.boot.data.jpa.JpaPredicate;
import site.morn.boot.data.jpa.JpaReference;
import site.morn.boot.data.jpa.SpecificationFunction;
import site.morn.core.CriteriaMap;

/**
 * JPA查询规格构建器
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class SpecificationBuilder<M> {

  private JpaCommon<M> common;

  private SpecificationBuilder(M model, CriteriaMap attach,
      Consumer<JpaParameter<M>> parameterConsumer) {
    this.common = new JpaCommon<>();
    JpaParameter<M> parameter = this.common.model(model).attach(attach).buildParameter();
    if (Objects.nonNull(parameterConsumer)) {
      parameterConsumer.accept(parameter);
    }
  }

  public static <T> SpecificationBuilder<T> withParameter(T model) {
    return withParameter(model, null);
  }

  public static <T> SpecificationBuilder<T> withParameter(T model, CriteriaMap attach) {
    return new SpecificationBuilder<>(model, attach, null);
  }

  public static <T> SpecificationBuilder<T> withParameter(T model, CriteriaMap attach,
      Consumer<JpaParameter<T>> parameterConsumer) {
    return new SpecificationBuilder<>(model, attach, parameterConsumer);
  }

  /**
   * 构建JPA查询规格
   *
   * @return JPA查询规格
   */
  public static <T> Specification<T> specification(SimpleFunction<T> function) {
    return ((root, query, builder) ->
    {
      JpaPredicate predicate = new JpaPredicate(builder);
      function.predicate(root, query, builder, predicate);
      return predicate.get();
    });
  }

  /**
   * 构建JPA查询规格
   *
   * @return JPA查询规格
   */
  public Specification<M> specification(SpecificationFunction function) {
    return (root, query, builder) -> {
      common.path(root).query(query).builder(builder).parameter(common.parameter());
      JpaReference<M> reference = common.buildReference();
      JpaPredicate predicate = common.buildPredicate();
      JpaBatchCondition condition = common.buildCondition();
      function.predicate(reference, predicate, condition);
      return predicate.get();
    };
  }

  @FunctionalInterface
  public interface SimpleFunction<T> {

    void predicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder,
        JpaPredicate restrain);
  }

}
