package site.morn.core;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 基础属性类
 *
 * @author timely-rain
 * @see Map
 * @since 1.0.0, 2018/7/10
 */
public interface Attributes<K, V> extends Map<K, V> {

  /**
   * 设置属性
   *
   * @param key 属性名称
   * @param value 属性
   * @return 属性基类
   */
  @SuppressWarnings("unchecked")
  default <T extends Attributes<K, V>> T set(K key, V value) {
    put(key, value);
    return (T) this;
  }

  /**
   * 获取"预期"属性
   *
   * @param name 属性名称
   * @return "预期"属性
   */
  @SuppressWarnings("unchecked")
  default <T> T getExpect(String name) {
    return (T) get(name);
  }

  /**
   * 获取"预期"属性
   *
   * @param name 属性名称
   * @return "预期"属性
   */
  @SuppressWarnings("unchecked")
  default <T> T getExpect(String name, V defaultValue) {
    return (T) getOrDefault(name, defaultValue);
  }

  /**
   * 获取字符属性
   *
   * @param name 属性名称
   * @return 字符属性
   */
  default String getString(String name) {
    V v = get(name);
    if (Objects.isNull(v)) {
      return null;
    }
    return v.toString();
  }

  /**
   * 获取布尔属性
   *
   * @param name 属性名称
   * @return 布尔属性
   */
  default Boolean getBoolean(String name) {
    V value = get(name);
    return Optional.ofNullable(value).map(Objects::toString).map(Boolean::valueOf).orElse(null);
  }

  /**
   * 获取整型属性
   *
   * @param name 属性名称
   * @return 整型属性
   */
  default Integer getInteger(String name) {
    V value = get(name);
    return Optional.ofNullable(value).map(Objects::toString).map(Integer::valueOf).orElse(null);
  }
}
