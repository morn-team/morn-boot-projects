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
public class PersistFunctionUtils {

  /**
   * 数据新增处理
   *
   * @param data 数据
   * @param <T> 数据类型
   */
  @SuppressWarnings("unchecked")
  public static <T> void processAdd(T data) {
    process(AddProcessor.class, data);
  }

  /**
   * 数据删除处理
   *
   * @param data 数据
   * @param <T> 数据类型
   */
  @SuppressWarnings("unchecked")
  public static <T> void processDelete(T data) {
    process(AddProcessor.class, data);
  }

  /**
   * 数据更新处理
   *
   * @param data 数据
   * @param <T> 数据类型
   */
  @SuppressWarnings("unchecked")
  public static <T> void processUpdate(T data) {
    process(AddProcessor.class, data);
  }

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
   * 持久化处理
   *
   * @param functionClass 处理类
   * @param data 数据
   * @param <V> 处理类型
   * @param <T> 数据类型
   */
  public static <V extends PersistProcessor<T>, T> void process(Class<V> functionClass, T data) {
    List<V> processes = BeanCaches.targetBeans(functionClass, data.getClass());
    for (V process : processes) {
      process.handle(data);
    }
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
