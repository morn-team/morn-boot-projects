package site.morn.boot.jpa;

import java.util.Arrays;
import java.util.Objects;
import javax.persistence.criteria.Predicate;

/**
 * site.morn.boot.jpa
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public interface JpaBatchCondition extends JpaCondition {

  Predicate[] equalAll();

  default Predicate[] equals(String... names) {
    return Arrays.stream(names).map(this::equal).filter(Objects::nonNull).toArray(Predicate[]::new);
  }

  default Predicate[] contains(String... names) {
    return Arrays.stream(names).map(s -> like(s, "%", "%")).filter(Objects::nonNull)
        .toArray(Predicate[]::new);
  }

  default Predicate[] startWith(String... names) {
    return Arrays.stream(names).map(s -> like(s, "", "%")).filter(Objects::nonNull)
        .toArray(Predicate[]::new);
  }

  default Predicate[] endWith(String... names) {
    return Arrays.stream(names).map(s -> like(s, "%", "")).filter(Objects::nonNull)
        .toArray(Predicate[]::new);
  }

  default Predicate[] ins(String... names) {
    return Arrays.stream(names).map(this::in).filter(Objects::nonNull)
        .toArray(Predicate[]::new);
  }
}
