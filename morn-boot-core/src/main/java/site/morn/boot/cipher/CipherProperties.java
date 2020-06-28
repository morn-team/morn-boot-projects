package site.morn.boot.cipher;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 加密配置项
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/28
 */
@Getter
@Setter
@ConfigurationProperties("morn.cipher")
public class CipherProperties {

  /**
   * 全局密码
   */
  private String password;

  /**
   * AES配置项
   */
  private AES aes = new AES();

  @Getter
  @Setter
  public static class AES {

    /**
     * 向量长度
     */
    private int ivLength = 12;

    /**
     * 密钥长度
     */
    private int secretKeyLength = 128;
  }
}
