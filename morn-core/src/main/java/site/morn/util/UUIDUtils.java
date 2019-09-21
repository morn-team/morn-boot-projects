package site.morn.util;

import java.util.UUID;
import lombok.experimental.UtilityClass;

/**
 * UUID工具类
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/15
 */
@UtilityClass
public class UUIDUtils {

  /**
   * 32位小写
   *
   * @return UUID字符串
   */
  public static String lowercase() {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
