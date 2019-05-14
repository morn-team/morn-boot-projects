package site.morn.validate.persistent;

/**
 * 数据删除校验
 *
 * @author timely-rain
 * @since 1.0.0, 2019/4/19
 */
public interface DeleteValidation<T> {


  /**
   * 校验
   *
   * @param target 目标数据
   */
  void validate(T target);
}
