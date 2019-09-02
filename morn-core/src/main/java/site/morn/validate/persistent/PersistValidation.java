package site.morn.validate.persistent;

/**
 * 持久化数据校验
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/15
 */
@FunctionalInterface
public interface PersistValidation<T> {

  /**
   * 校验
   *
   * @param target 目标数据
   */
  void validate(T target);
}
