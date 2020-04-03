package site.morn.boot.jpa;


import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.Assert;
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
public class JpaReference<M> {

  private final Path<M> path;

  private final CriteriaQuery<?> query;

  private final CriteriaBuilder builder;

  public <T> Root<T> root() {
    return TypeUtils.cast(path);
  }

  /**
   * 获取实体类属性
   *
   * @return 属性集合
   */
  public Set<Attribute<? super M, ?>> attributes() {
    Assert.isInstanceOf(Root.class, path);
    Root<M> root = TypeUtils.cast(path);
    return root.getModel().getAttributes();
  }

  /**
   * 获得实体类属性Stream
   *
   * @return Stream<Attribute>
   */
  public Stream<Attribute<? super M, ?>> attributeStream() {
    return attributes().stream();
  }

  /**
   * 获得实体类属性Stream
   *
   * @return Stream<Attribute>
   */
  public Stream<Attribute<? super M, ?>> attributeStreamIncludes(String... names) {
    java.util.function.Predicate<Attribute> includes = attribute -> Stream.of(names)
        .anyMatch(s -> Objects.equals(s, attribute.getName()));
    return attributes().stream().filter(includes);
  }

}
