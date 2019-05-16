package site.morn.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 标识的实例持有者
 *
 * @param <T> 实例类型
 * @author timely-rain
 * @since 1.0.0, 2018/11/26
 */
@Getter
@Setter
public class IdentifiedBeanHolder<T> {

  /**
   * 实例标识
   */
  private BeanIdentify identify;

  /**
   * 实例
   */
  private T bean;
}
