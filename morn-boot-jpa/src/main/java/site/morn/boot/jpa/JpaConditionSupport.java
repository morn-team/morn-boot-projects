package site.morn.boot.jpa;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Attribute;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * JPA查询条件
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
@Accessors(chain = true, fluent = true)
@Setter
public class JpaConditionSupport<M> implements JpaBatchCondition {

  private JpaReference<M> reference;

  private JpaParameter<M> parameter;

  @Override
  public Predicate[] equalAll() {
    Stream<Predicate> predicateStream = reference.attributeStream().map(Attribute::getName)
        .map(this::equal);
    return JpaPredicate.array(predicateStream);
  }

  @Override
  public Predicate equal(String name) {
    Optional<Object> optional = parameter.getOptional(name);
    return optional.map(o -> builder().equal(path().get(name), o)).orElse(null);
  }

  @Override
  public Predicate contain(String name) {
    return contain(name, name);
  }

  @Override
  public Predicate contain(String name, String valueName) {
    Path<String> attribute = path().get(name);
    Optional<String> optional = parameter.getOptional(valueName);
    String value = optional.orElse("");
    return contain(attribute, value);
  }

  @Override
  public Predicate contain(Expression<String> attribute, String value) {
    String contains = JpaConditionUtils.contains(value);
    return like(attribute, contains);
  }

  @Override
  public Predicate startWith(String name) {
    Path<String> attribute = path().get(name);
    String value = parameter.getStringOptional(name).orElse("");
    String startWith = JpaConditionUtils.startWith(value);
    return like(attribute, startWith);
  }

  @Override
  public Predicate endWith(String name) {
    Path<String> attribute = path().get(name);
    String value = parameter.getStringOptional(name).orElse("");
    String endWith = JpaConditionUtils.endWith(value);
    return like(attribute, endWith);
  }

  @Override
  public Predicate in(String name) {
    return null;
  }

  /**
   * 模糊搜索
   *
   * @param attribute 实体属性
   * @param expression 查询表达式
   * @return 条件断言
   */
  private Predicate like(Expression<String> attribute, String expression) {
    if (Objects.isNull(expression)) {
      return null;
    }
    return JpaConditionUtils.predicate(attribute, expression, builder()::like);
  }

  private Path<M> path() {
    return reference.path();
  }

  private CriteriaQuery<?> query() {
    return reference.query();
  }

  private CriteriaBuilder builder() {
    return reference.builder();
  }

}
