package site.morn.boot.jpa;

import javax.persistence.criteria.Predicate;

/**
 * site.morn.boot.jpa
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public interface JpaCondition<T> {

  Predicate equal(String name);

  Predicate like(String name, String prefix, String suffix);

  Predicate in(String name);
}
