package site.morn.core;

/**
 * 实例验证器
 *
 * @author timely-rain
 * @since 1.2.1, 2019/9/12
 */
@FunctionalInterface
public interface BeanValidator<T> {

  /**
   * 校验
   *
   * @param source 源数据
   * @return 检验是否通过
   */
  boolean validate(T source);
}
