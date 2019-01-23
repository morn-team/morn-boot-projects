package site.morn.boot.jpa;

import javax.persistence.criteria.Predicate;

/**
 * JPA查询条件
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public interface JpaCondition {

  Predicate equal(String name);

  Predicate like(String name, String prefix, String suffix);

  Predicate in(String name);
}
