package site.morn.boot.jpa;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import site.morn.core.CriteriaMap;
import site.morn.util.OptionalCollection;
import site.morn.util.TypeUtils;

/**
 * JPA查询参数实现
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/14
 */
public class JpaParameterSupport<M> implements JpaParameter<M> {

  /**
   * 实体数据
   */
  private M model;

  /**
   * 附加数据
   */
  private CriteriaMap attach = new CriteriaMap();

  @Override
  public JpaParameter model(M model) {
    this.model = model;
    return this;
  }

  @Override
  public JpaParameter attach(Map<String, Object> attach) {
    this.attach.putAll(attach);
    return this;
  }

  @Override
  public JpaParameter withNamePair(String pathName, String model) {
    return this;
  }

  @Override
  public <V> JpaParameter withValuePair(String pathName, V value) {
    attach.put(pathName, value);
    return this;
  }

  @Override
  public <V> Optional<V> getOptional(String name) {
    PropertyDescriptor descriptor = propertyDescriptor(name);
    Object value = JpaConditionUtils.getPropertyValue(model, descriptor);
    Optional<V> modelValue = Optional.ofNullable(TypeUtils.as(value)); // 优先从model中获取值
    V attachValue = modelValue.orElse(attach.getExpect(name)); // 否则从attach中获取值
    return Optional.ofNullable(attachValue);
  }

  @Override
  public <V> OptionalCollection<V> getCollectionOptional(String name) {
    return null;
  }

  /**
   * 获取类型
   *
   * @return 类型
   */
  private Class<M> javaType() {
    return TypeUtils.as(model.getClass());
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
