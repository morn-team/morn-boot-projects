package site.morn.validate.persistent;

import java.util.List;
import lombok.experimental.UtilityClass;
import site.morn.bean.BeanCaches;

/**
 * 持久化校验工具类
 *
 * @author timely-rain
 * @since 1.0.0, 2019/4/19
 */
@UtilityClass
public class PersistValidateUtils {

  /**
   * 数据新增校验
   *
   * @param data 校验数据
   * @param <T> 数据类型
   */
  @SuppressWarnings("unchecked")
  public static <T> void validateAdd(T data) {
    validate(AddValidation.class, data);
  }

  /**
   * 数据删除校验
   *
   * @param data 校验数据
   * @param <T> 数据类型
   */
  @SuppressWarnings("unchecked")
  public static <T> void validateDelete(T data) {
    validate(DeleteValidation.class, data);
  }

  /**
   * 数据修改校验
   *
   * @param data 校验数据
   * @param <T> 数据类型
   */
  @SuppressWarnings("unchecked")
  public static <T> void validateUpdate(T data) {
    validate(UpdateValidation.class, data);
  }

  /**
   * 持久化校验
   *
   * @param validateClass 校验类
   * @param data 校验数据
   * @param <V> 校验类型
   * @param <T> 数据类型
   */
  public static <V extends PersistValidation<T>, T> void validate(Class<V> validateClass, T data) {
    List<V> validations = BeanCaches.targetBeans(validateClass, data.getClass());
    for (V validation : validations) {
      validation.validate(data);
    }
  }

}
