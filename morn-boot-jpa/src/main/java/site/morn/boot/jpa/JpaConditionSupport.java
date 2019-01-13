package site.morn.boot.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * site.morn.boot.jpa
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public abstract class JpaConditionSupport<T, D> implements JpaBatchCondition<T>,
    JpaConditionPair<D>,
    JpaConditionValue {

  private Root<T> root;

  private Path<T> path;

  private CriteriaQuery<?> query;

  private CriteriaBuilder builder;

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

  @Override
  public JpaConditionPair withNamePair(String pathName, String model) {
    return null;
  }

  @Override
  public <V> JpaConditionPair withValuePair(String pathName, V value) {
    return null;
  }

  private <P> Path<P> getPath(String name) {
    return path.get(name);
  }
}
