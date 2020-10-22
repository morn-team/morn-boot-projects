package site.morn.boot.data.jpa.support;

import java.lang.reflect.Array;
import java.util.Collection;
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
import site.morn.boot.data.jpa.JpaBatchCondition;
import site.morn.boot.data.jpa.JpaParameter;
import site.morn.boot.data.jpa.JpaPredicate;
import site.morn.boot.data.jpa.JpaReference;
import site.morn.util.SpareArrayUtils;

/**
 * JPA查询条件
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
@Setter
@Accessors(chain = true, fluent = true)
public class SimpleJpaCondition<M> implements JpaBatchCondition {

  private JpaReference<M> reference;

  private JpaParameter<M> parameter;

  private InnerBuilder innerBuilder;

  @Override
  public Predicate[] equalAll() {
    Stream<Predicate> predicateStream = reference.attributeStream().map(Attribute::getName)
        .map(this::eq);
    return JpaPredicate.array(predicateStream);
  }

  @Override
  public Predicate[] equalAllExcludes(String... names) {
    Stream<Predicate> predicateStream = reference.attributeStream().map(Attribute::getName)
        .filter(name -> !SpareArrayUtils.contains(names, name)).map(this::eq);
    return JpaPredicate.array(predicateStream);
  }

  @Override
  public Predicate eq(String name) {
    return eq(name, name);
  }

  @Override
  public Predicate eq(String name, String valueName) {
    return innerBuilder().namePredicate(name, valueName, builder()::equal);
  }

  @Override
  public Predicate notEqual(String name) {
    return notEqual(name, name);
  }

  @Override
  public Predicate notEqual(String name, String valueName) {
    return innerBuilder().namePredicate(name, valueName, builder()::notEqual);
  }

  @Override
  public Predicate contain(String name) {
    return contain(name, name);
  }

  @Override
  public Predicate contain(String name, String valueName) {
    Path<String> attribute = path().get(name);
    String contains = parameter.mapOptional(valueName, JpaConditionUtils::contains);
    return like(attribute, contains);
  }

  @Override
  public Predicate startWith(String name, String valueName) {
    Path<String> attribute = path().get(name);
    String startWith = parameter.mapOptional(valueName, JpaConditionUtils::startWith);
    return like(attribute, startWith);
  }

  @Override
  public Predicate endWith(String name, String valueName) {
    Path<String> attribute = path().get(name);
    String endWith = parameter.mapOptional(valueName, JpaConditionUtils::endWith);
    return like(attribute, endWith);
  }

  @Override
  public Predicate in(String name) {
    return in(name, name);
  }

  @Override
  public Predicate in(String name, String valueName) {
    Expression<?> expression = path().get(name);
    Object value = parameter.getOptional(valueName).orElse(null);
    if (Objects.isNull(value)) {
      return null;
    }
    Class<?> valueClass = value.getClass();
    if (valueClass.isArray() && Array.getLength(value) > 0) {
      return expression.in((Object[]) value);
    }
    if (value instanceof Collection) {
      Collection<?> collection = (Collection<?>) value;
      if (collection.isEmpty()) {
        return null;
      }
      return expression.in(collection);
    }
    return expression.in(value);
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
   * 模糊搜索
   *
   * @param attribute 实体属性
   * @param s 查询表达式
   * @return 条件断言
   */
  private Predicate like(Expression<String> attribute, String s) {
    return innerBuilder().mapPredicate(attribute, s, builder()::like);
  }

  /**
   * 内置条件构建器
   */
  public class InnerBuilder {

    public <T, V> Predicate namePredicate(String name,
        BiFunction<Expression<T>, V, Predicate> function) {
      return namePredicate(name, name, function);
    }

    public <T, V> Predicate namePredicate(String name, String valueName,
        BiFunction<Expression<T>, V, Predicate> function) {
      Expression<T> expression = path().get(name);
      Optional<V> optional = parameter.getOptional(valueName);
      return referencePredicate(expression, optional, function);
    }

    public <T, V> Predicate mapPredicate(String name, V value,
        BiFunction<Expression<T>, V, Predicate> function) {
      Expression<T> expression = path().get(name);
      return mapPredicate(expression, value, function);
    }

    public <T, V> Predicate mapPredicate(Expression<T> expression, V value,
        BiFunction<Expression<T>, V, Predicate> function) {
      return JpaConditionUtils.predicate(expression, value, function);
    }

    public <T, V> Predicate referencePredicate(Expression<T> expression, Optional<V> valueOptional,
        BiFunction<Expression<T>, V, Predicate> function) {
      return parameter
          .mapOptional(valueOptional, o -> JpaConditionUtils.predicate(expression, o, function));
    }

  }

}


