/*
 * Copyright 2011-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package site.morn.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * 备用加密工具类
 *
 * <p>备用类不推荐在生产项目中使用，生产项目中推荐使用spring-security相关工具包
 *
 * @author Keith Donald
 * @author timely-rain
 * @since 1.2.1, 2020/4/29
 */
public class SpareCipherUtils {

  /**
   * Constructs a new Cipher.
   */
  public static Cipher newCipher(String algorithm) {
    try {
      return Cipher.getInstance(algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalArgumentException("Not a valid encryption algorithm", e);
    } catch (NoSuchPaddingException e) {
      throw new IllegalStateException("Should not happen", e);
    }
  }

  /**
   * Initializes the Cipher for use.
   */
  public static void initCipher(Cipher cipher, int mode, SecretKey secretKey,
      AlgorithmParameterSpec parameterSpec) {
    try {
      if (parameterSpec != null) {
        cipher.init(mode, secretKey, parameterSpec);
      } else {
        cipher.init(mode, secretKey);
      }
    } catch (InvalidKeyException e) {
      throw new IllegalArgumentException(
          "Unable to initialize due to invalid secret key", e);
    } catch (InvalidAlgorithmParameterException e) {
      throw new IllegalStateException(
          "Unable to initialize due to invalid decryption parameter spec", e);
    }
  }

  /**
   * Invokes the Cipher to perform encryption or decryption (depending on the initialized mode).
   */
  public static byte[] doFinal(Cipher cipher, byte[] input) {
    try {
      return cipher.doFinal(input);
    } catch (IllegalBlockSizeException e) {
      throw new IllegalStateException(
          "Unable to invoke Cipher due to illegal block size", e);
    } catch (BadPaddingException e) {
      throw new IllegalStateException("Unable to invoke Cipher due to bad padding",
          e);
    }
  }

  private SpareCipherUtils() {
  }

}
