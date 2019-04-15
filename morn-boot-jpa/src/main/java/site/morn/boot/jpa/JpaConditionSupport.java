package site.morn.boot.jpa;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.Assert;
import site.morn.util.TypeUtils;

/**
 * JPA查询条件
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
@Accessors(chain = true, fluent = true)
@Setter
public class JpaConditionSupport<M> implements JpaBatchCondition {

  private Path<M> path;

  private CriteriaQuery<?> query;

  private CriteriaBuilder builder;

  private JpaParameter<M> parameter;

  @Override
  public Predicate[] equalAll() {
    Stream<Predicate> predicateStream = attributeStream().map(Attribute::getName).map(this::equal);
    return JpaConditionUtils.array(predicateStream);
  }

  @Override
  public Predicate equal(String name) {
    Optional<Object> optional = parameter.getOptional(name);
    return optional.map(o -> builder.equal(path.get(name), o)).orElse(null);
  }

  @Override
  public Predicate like(String name, String prefix, String suffix) {
    return null;
  }

  @Override
  public Predicate in(String name) {
    return null;
  }

  /**
   * 获取实体类属性
   *
   * @return 属性集合
   */
  private Set<Attribute<? super M, ?>> attributes() {
    Assert.isInstanceOf(Root.class, path);
    Root<M> root = TypeUtils.as(path);
    return root.getModel().getAttributes();
  }

  /**
   * 获得实体类属性Stream
   *
   * @return Stream<Attribute>
   */
  private Stream<Attribute<? super M, ?>> attributeStream() {
    return attributes().stream();
  }

}
