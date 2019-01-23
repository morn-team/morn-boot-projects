package site.morn.boot.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import site.morn.util.TypeUtils;

/**
 * JPA原生对象
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/23
 */
@Accessors(fluent = true)
@RequiredArgsConstructor
@Getter
public class JpaReference {

  private final Path<?> path;

  private final CriteriaQuery<?> query;

  private final CriteriaBuilder builder;

  public <T> Root<T> root() {
    return TypeUtils.as(path);
  }
}
