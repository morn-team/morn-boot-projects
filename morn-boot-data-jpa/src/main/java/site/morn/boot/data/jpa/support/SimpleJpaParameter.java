package site.morn.boot.data.jpa.support;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.BeanUtils;
import site.morn.boot.data.jpa.JpaParameter;
import site.morn.core.CriteriaMap;
import site.morn.util.GenericUtils;
import site.morn.util.OptionalCollection;

/**
 * JPA查询参数实现
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/14
 */
public class SimpleJpaParameter<M> implements JpaParameter<M> {

  /**
   * 实体数据
   */
  private M model;

  /**
   * 附加数据
   */
  private CriteriaMap attach = new CriteriaMap();

  @Override
  public JpaParameter<M> model(M model) {
    this.model = model;
    return this;
  }

  @Override
  public JpaParameter<M> attach(Map<String, Object> attach) {
    if (Objects.nonNull(attach)) {
      this.attach.putAll(attach);
    }
    return this;
  }

  @Override
  public JpaParameter<M> withNamePair(String pathName, String model) {
    return this;
  }

  @Override
  public <V> JpaParameter<M> withValuePair(String pathName, V value) {
    attach.put(pathName, value);
    return this;
  }

  @Override
  public <V> Optional<V> getOptional(String name) {
    PropertyDescriptor descriptor = propertyDescriptor(name);
    Object value = JpaConditionUtils.getPropertyValue(model, descriptor);
    Optional<V> modelValue = Optional.ofNullable(GenericUtils.castFrom(value)); // 优先从model中获取值
    V attachValue = modelValue.orElse(attach.getExpect(name)); // 否则从attach中获取值
    return Optional.ofNullable(attachValue);
  }

  @Override
  public Optional<String> getStringOptional(String name) {
    return getOptional(name);
  }

  @Override
  public <V> OptionalCollection<V> getCollectionOptional(String name) {
    return null;
  }

  @Override
  public <V, R> R mapOptional(String name, Function<V, R> mapper) {
    Optional<V> optional = getOptional(name);
    return mapOptional(optional, mapper);
  }

  @Override
  public <V, R> R mapOptional(Optional<V> optional, Function<V, R> mapper) {
    return optional.map(mapper).orElse(null);
  }

  /**
   * 获取类型
   *
   * @return 类型
   */
  private Class<M> javaType() {
    return GenericUtils.castFrom(model.getClass());
  }

  /**
   * 获取属性描述
   *
   * @param name 属性名
   * @return 属性描述
   */
  protected PropertyDescriptor propertyDescriptor(String name) {
    return BeanUtils.getPropertyDescriptor(javaType(), name);
  }

}
