package site.morn.core;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import site.morn.util.TypeUtils;

/**
 * 任意类型变量
 *
 * @author timely-rain
 * @since 1.2.1, 2019/9/27
 */
@Getter
@Setter
@Accessors(chain = true, fluent = true)
public class X {

  /**
   * 值
   */
  private final Object value;

  public X(Object value) {
    this.value = value;
  }

  /**
   * 获取集合
   *
   * @return 集合
   */
  @SuppressWarnings("unchecked")
  public <T> Collection<T> asCollection() {
    return value(Collection.class);
  }

  /**
   * 获取长整型值
   *
   * @return 长整型值
   */
  public Long asLong() {
    return value(Long.class);
  }

  /**
   * 获取字符值
   *
   * @return 字符值
   */
  public String asString() {
    return value(String.class);
  }

  /**
   * 获取值
   *
   * @param cls 目标类
   * @param <T> 目标类型
   * @return 值
   */
  public <T> T value(Class<T> cls) {
    return TypeUtils.cast(value(), cls);
  }
}
