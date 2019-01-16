package site.morn.boot.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * site.morn.boot.jpa
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

  private JpaConditionPair<M> pair;

  @Override
  public Predicate[] equalAll() {
    return new Predicate[0];
  }

  @Override
  public Predicate equal(String name) {
    return null;
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
