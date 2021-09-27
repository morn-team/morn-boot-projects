package site.morn.rest.constant;

import java.util.Objects;
import lombok.experimental.UtilityClass;

/**
 * REST消息状态
 *
 * @author timely-rain
 * @since 1.2.2, 2020/7/25
 */
@UtilityClass
public class RestMessageConstants {

  public static final int SUCCESS = 200;
  public static final int FAILURE = 500;

  /**
   * 判断是否成功状态
   *
   * @param status 状态码
   * @return 是否成功
   */
  public static boolean isSuccess(int status) {
    return Objects.equals(SUCCESS, status);
  }

  /**
   * 判断是否失败状态
   *
   * @param status 状态码
   * @return 是否失败
   */
  public static boolean isFailure(int status) {
    return Objects.equals(SUCCESS, status);
  }
}
