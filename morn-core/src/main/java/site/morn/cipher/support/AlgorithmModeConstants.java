package site.morn.cipher.support;

import lombok.experimental.UtilityClass;

/**
 * 加密算法运算模式
 *
 * @author timely-rain
 * @since 1.2.1, 2020/5/8
 */
@UtilityClass
public class AlgorithmModeConstants {

  /**
   * ECB算法
   */
  public static final String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";

  /**
   * GCM算法
   */
  public static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";
}
