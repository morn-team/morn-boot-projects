package site.morn.cipher.support;

import lombok.experimental.UtilityClass;

/**
 * 数据加密算法
 */
@UtilityClass
public class AlgorithmNames {

  /**
   * AES算法
   */
  public final String AES = "aes";

  /**
   * Base64算法
   */
  public final String BASE64 = "base64";

  /**
   * MD5算法
   */
  public final String MD5 = "md5";

  /**
   * BCrypt算法
   */
  public final String SPRING_B_CRYPT = "spring.b.crypt";
}
