package site.morn.boot.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.Specification;

/**
 * JPA查询规格构建器
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
@Accessors(chain = true, fluent = true)
public final class SpecificationBuilder<T, A> {

  private T model;

  private A attach;

  private Root<T> root;

  private CriteriaQuery<?> query;

  private CriteriaBuilder builder;

  private Predicate predicate = null;

  private SpecificationBuilder() {
  }

  public static <T, A> SpecificationBuilder<T, A> builder() {
    return new SpecificationBuilder<>();
  }

  /**
   * 构建JPA查询规格
   *
   * @return JPA查询规格
   */
  public Specification<T> build() {
    return ((r, cq, cb) ->
    {
      this.root = r;
      this.query = cq;
      this.builder = cb;
      return predicate;
    });
  }

  public SpecificationBuilder<T, A> model(T model) {
    this.model = model;
    return this;
  }

  public SpecificationBuilder<T, A> attach(A attach) {
    this.attach = attach;
    return this;
  }
}
