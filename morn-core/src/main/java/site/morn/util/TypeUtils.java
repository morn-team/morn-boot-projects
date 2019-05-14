package site.morn.util;

import lombok.experimental.UtilityClass;
import site.morn.exception.ApplicationMessages;

/**
 * 类型工具类
 *
 * @author TianGanLin
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
@UtilityClass
public class TypeUtils {

  /**
   * 类型转换
   *
   * @param source 原对象
   * @param <T> 目标类型
   * @return 目标对象
   */
  @SuppressWarnings("unchecked")
  public <T> T as(Object source) {
    try {
      return (T) source;
    } catch (Exception e) {
      throw ApplicationMessages
          .translateMessage("util.cast-fail", source.getClass().getSimpleName())
          .exception();
    }
  }
}
