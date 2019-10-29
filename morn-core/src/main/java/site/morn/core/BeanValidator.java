package site.morn.core;

/**
 * 实例校验者
 *
 * @author timely-rain
 * @since 1.2.1, 2019/9/12
 */
public interface BeanValidator<T> {

  /**
   * 校验
   *
   * @param data 数据
   * @return 检验是否通过
   */
  boolean validate(T data);
}
