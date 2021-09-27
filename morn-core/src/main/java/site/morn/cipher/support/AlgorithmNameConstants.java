package site.morn.cipher.support;

import lombok.experimental.UtilityClass;

/**
 * 数据加密算法
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/24
 */
@UtilityClass
public class AlgorithmNameConstants {

  /**
   * AES算法
   */
  public static final String AES = "aes";

  /**
   * Base64算法
   */
  public static final String BASE64 = "base64";

  /**
   * MD5算法
   */
  public static final String MD5 = "md5";

  /**
   * BCrypt算法
   */
  public static final String SPRING_B_CRYPT = "spring.b.crypt";
}
