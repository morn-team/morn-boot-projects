package site.morn.data.persistent;

import site.morn.core.BeanValidator;

/**
 * 持久化验证器
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/15
 */
@FunctionalInterface
public interface PersistValidator<T> extends BeanValidator<T> {

  /**
   * 验证持久化操作
   *
   * @param source 源数据
   * @return 操作是否允许
   */
  @Override
  boolean validate(T source);
}
