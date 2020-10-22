package site.morn.boot.data.jpa;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;
import javax.persistence.criteria.Predicate;

/**
 * JPA批量查询条件
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public interface JpaBatchCondition extends JpaCondition {

  /**
   * 全部匹配
   */
  Predicate[] equalAll();

  /**
   * 全部匹配
   *
   * @param names 排除的属性名
   */
  Predicate[] equalAllExcludes(String... names);

  default Predicate[] eqs(String... names) {
    return Arrays.stream(names).map(this::eq).filter(Objects::nonNull).toArray(Predicate[]::new);
  }

  default Predicate[] contains(String... names) {
    Stream<Predicate> predicateStream = Arrays.stream(names).map(this::contain);
    return JpaPredicate.array(predicateStream);
  }

  default Predicate[] contains(String[] names, String valueName) {
    Stream<Predicate> predicateStream = Arrays.stream(names).map(s -> this.contain(s, valueName));
    return JpaPredicate.array(predicateStream);
  }

  default Predicate[] startWithes(String... names) {
    Stream<Predicate> predicateStream = Arrays.stream(names).map(this::startWith);
    return JpaPredicate.array(predicateStream);
  }

  default Predicate[] startWithes(String[] names, String valueName) {
    Stream<Predicate> predicateStream = Arrays.stream(names).map(s -> this.startWith(s, valueName));
    return JpaPredicate.array(predicateStream);
  }

  default Predicate[] endWithes(String... names) {
    Stream<Predicate> predicateStream = Arrays.stream(names).map(this::endWith);
    return JpaPredicate.array(predicateStream);
  }

  default Predicate[] endWithes(String[] names, String valueName) {
    Stream<Predicate> predicateStream = Arrays.stream(names).map(s -> this.endWith(s, valueName));
    return JpaPredicate.array(predicateStream);
  }
}
