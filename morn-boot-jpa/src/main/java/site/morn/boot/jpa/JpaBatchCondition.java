package site.morn.boot.jpa;

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

  Predicate[] equalAll();

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

  default Predicate[] endWithes(String... names) {
    Stream<Predicate> predicateStream = Arrays.stream(names).map(this::endWith);
    return JpaPredicate.array(predicateStream);
  }

  default Predicate[] ins(String... names) {
    Stream<Predicate> predicateStream = Arrays.stream(names).map(this::in);
    return JpaPredicate.array(predicateStream);
  }
}
