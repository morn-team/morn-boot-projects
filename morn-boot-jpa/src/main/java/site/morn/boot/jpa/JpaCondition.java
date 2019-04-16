package site.morn.boot.jpa;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

/**
 * JPA查询条件
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public interface JpaCondition {

  Predicate equal(String name);

  Predicate in(String name);

  Predicate contain(String name);

  Predicate contain(String name, String valueName);

  Predicate contain(Expression<String> attribute, String value);

  Predicate startWith(String name);

  Predicate endWith(String name);
}
