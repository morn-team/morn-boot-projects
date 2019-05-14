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
   * 数据删除校验
   *
   * @param data 数据
   * @param <T> 数据类型
   */
  @SuppressWarnings("unchecked")
  public static <T> void validateDelete(T data) {
    List<DeleteValidation> validations = BeanCaches
        .targetBeans(DeleteValidation.class, data.getClass());
    for (DeleteValidation validation : validations) {
      validation.validate(data);
    }
  }

}
