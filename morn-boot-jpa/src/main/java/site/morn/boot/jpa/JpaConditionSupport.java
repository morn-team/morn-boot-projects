package site.morn.boot.jpa;

import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * JPA查询条件
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
@Accessors(chain = true, fluent = true)
@Setter
public class JpaConditionSupport<M> implements JpaBatchCondition {

  private Path<?> path;

  private CriteriaQuery<?> query;

  private CriteriaBuilder builder;

  private JpaParameter<M> parameter;

  @Override
  public Predicate[] equalAll() {
    return new Predicate[0];
  }

  @Override
  public Predicate equal(String name) {
    Optional<Object> optional = parameter.getOptional(name);
    return optional.map(o -> builder.equal(getPath(name), o)).orElse(null);
  }

  @Override
  public Predicate like(String name, String prefix, String suffix) {
    return null;
  }

  @Override
  public Predicate in(String name) {
    return null;
  }

  private <P> Path<P> getPath(String name) {
    return path.get(name);
  }
}
