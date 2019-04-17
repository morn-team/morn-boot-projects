package site.morn.boot.jpa;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

  private InnerBuilder innerBuilder;

  @Override
  public Predicate[] equalAll() {
    Stream<Predicate> predicateStream = reference.attributeStream().map(Attribute::getName)
        .map(this::equal);
    return JpaPredicate.array(predicateStream);
  }

  @Override
  public Predicate equal(String name) {
    return innerBuilder().predicate(name, builder()::equal);
  }

  @Override
  public Predicate notEqual(String name) {
    return innerBuilder().predicate(name, builder()::notEqual);
  }

  @Override
  public Predicate contain(String name) {
    return contain(name, name);
  }

  @Override
  public Predicate contain(String name, String valueName) {
    Path<String> attribute = path().get(name);
    String value = parameter.getStringOptional(valueName).orElse("");
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
    String startWith = parameter.mapOptional(name, JpaConditionUtils::startWith);
    return like(attribute, startWith);
  }

  @Override
  public Predicate endWith(String name) {
    Path<String> attribute = path().get(name);
    String endWith = parameter.mapOptional(name, JpaConditionUtils::endWith);
    return like(attribute, endWith);
  }

  @Override
  public Predicate in(String name) {
    Expression<?> expression = path().get(name);
    return parameter.mapOptional(name, expression::in);
  }

  /**
   * 模糊搜索
   *
   * @param attribute 实体属性
   * @param expression 查询表达式
   * @return 条件断言
   */
  private Predicate like(Expression<String> attribute, String expression) {
    return JpaConditionUtils.predicate(attribute, expression, builder()::like);
  }


  public CriteriaBuilder builder() {
    return reference.builder();
  }

  public CriteriaQuery<?> query() {
    return reference.query();
  }

  public Path<M> path() {
    return reference.path();
  }

  public <T> Root<T> root() {
    return reference.root();
  }

  /**
   * 获取内置条件构建器
   *
   * @return 内置条件构建器
   */
  public InnerBuilder innerBuilder() {
    if (Objects.isNull(innerBuilder)) {
      innerBuilder = new InnerBuilder();
    }
    return innerBuilder;
  }

  public class InnerBuilder {

    private <T, V> Predicate predicate(String name,
        BiFunction<Expression<T>, V, Predicate> function) {
      Expression<T> expression = path().get(name);
      Optional<V> optional = parameter.getOptional(name);
      return predicate(expression, optional, function);
    }

    private <T, V> Predicate predicate(String name, V value,
        BiFunction<Expression<T>, V, Predicate> function) {
      Expression<T> expression = path().get(name);
      Optional<V> optional = Optional.ofNullable(value);
      return predicate(expression, optional, function);
    }

    private <T, V> Predicate predicate(Expression<T> expression, Optional<V> valueOptional,
        BiFunction<Expression<T>, V, Predicate> function) {
      return parameter
          .mapOptional(valueOptional, o -> JpaConditionUtils.predicate(expression, o, function));
    }

  }

}
