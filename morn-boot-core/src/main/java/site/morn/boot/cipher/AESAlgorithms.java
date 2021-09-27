package site.morn.boot.cipher;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import site.morn.bean.annotation.Tag;
import site.morn.bean.support.BeanCaches;
import site.morn.cipher.AlgorithmDecryption;
import site.morn.cipher.AlgorithmEncryption;
import site.morn.cipher.AlgorithmMatcher;
import site.morn.cipher.annotation.AlgorithmName;
import site.morn.cipher.support.AlgorithmNameConstants;
import site.morn.cipher.support.SimpleAlgorithmHolder;
import site.morn.cipher.util.Ciphers;
import site.morn.util.SpareCipherUtils;

/**
 * AES算法
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/24
 */
@Slf4j
public class AESAlgorithms {

  public static final String AES = "AES";

  public static final String AES_ECB = "AES/ECB";

  public static final String AES_GCM = "AES/GCM";

  /**
   * 向量最大长度
   */
  public static final int IV_LENGTH_MAX = 16;

  /**
   * 向量最小长度
   */
  public static final int IV_LENGTH_MIN = 12;

  private AESAlgorithms() {
  }

  /**
   * AES算法
   */
  @Slf4j
  @AlgorithmName(AlgorithmNameConstants.AES)
  public static class AESAlgorithm extends SimpleAlgorithmHolder implements AlgorithmDecryption,
      AlgorithmEncryption, AlgorithmMatcher {

    @Override
    public String decrypt(CharSequence text) {
      String subAlgorithm = getSubAlgorithm();
      AlgorithmDecryption decryption = BeanCaches.tagBean(AlgorithmDecryption.class, subAlgorithm);
      Assert.notNull(decryption, "无法获取解密算法：" + algorithm.getMode());
      decryption.setAlgorithm(algorithm);
      return decryption.decrypt(text);
    }

    @Override
    public String encrypt(CharSequence text) {
      String subAlgorithm = getSubAlgorithm();
      AlgorithmEncryption encryption = BeanCaches.tagBean(AlgorithmEncryption.class, subAlgorithm);
      Assert.notNull(encryption, "无法获取加密算法：" + algorithm.getMode());
      encryption.setAlgorithm(algorithm);
      return encryption.encrypt(text);
    }

    @Override
    public boolean matches(CharSequence rawText, CharSequence encodedText) {
      String subAlgorithm = getSubAlgorithm();
      AlgorithmMatcher matcher = BeanCaches.tagBean(AlgorithmMatcher.class, subAlgorithm);
      Assert.notNull(matcher, "无法获取算法实现：" + algorithm.getMode());
      matcher.setAlgorithm(algorithm);
      return matcher.matches(rawText, encodedText);
    }

    /**
     * 获取子算法名称
     *
     * @return 子算法名称
     */
    private String getSubAlgorithm() {
      String mode = algorithm.getMode();
      Assert.hasLength(mode, "加密模式不能为空");
      int index = mode.lastIndexOf('/');
      return mode.substring(0, index);
    }
  }

  /**
   * 抽象AES算法
   */
  public abstract static class AbstractAESAlgorithm extends SimpleAlgorithmHolder implements
      AlgorithmDecryption, AlgorithmEncryption, AlgorithmMatcher {

    protected final CipherProperties properties;

    /**
     * 随机数
     */
    protected final SecureRandom secureRandom;

    protected AbstractAESAlgorithm(CipherProperties properties) {
      this.properties = properties;
      this.secureRandom = new SecureRandom();
    }

    @Override
    public String decrypt(CharSequence text) {
      // Base64转字节
      byte[] cipherMessage = Ciphers.decodeBase64(text);
      // 解密消息，密文字节转明文字节
      byte[] plainBytes = decryptBytes(cipherMessage);
      // 字节转文本
      return Ciphers.parseString(plainBytes);
    }

    @Override
    public String encrypt(CharSequence text) {
      // 文本转字节
      byte[] plainBytes = Ciphers.parseBytes(text);
      // 加密消息
      byte[] cipherMessage = encryptBytes(plainBytes);
      // 字节转Base64
      return Ciphers.encodeBase64(cipherMessage);
    }

    @Override
    public boolean matches(CharSequence rawText, CharSequence encodedText) {
      if (Objects.equals(rawText, encodedText)) {
        return true;
      }
      String decrypt = decrypt(encodedText);
      return Objects.equals(rawText, decrypt);
    }

    /**
     * 字节解密
     *
     * @param cipherMessage 密文字节
     * @return 明文字节
     */
    protected abstract byte[] decryptBytes(byte[] cipherMessage);

    /**
     * 字节加密
     *
     * @param plainBytes 明文字节
     * @return 密文字节
     */
    protected abstract byte[] encryptBytes(byte[] plainBytes);

    /**
     * 获取随机字节数组
     *
     * @param length 数字长度
     * @return 随机字节数组
     */
    protected byte[] getRandomBytes(int length) {
      byte[] randomBytes = new byte[length];
      secureRandom.nextBytes(randomBytes);
      return randomBytes;
    }

    /**
     * 获取盐
     *
     * @return 盐
     */
    protected String getPasswordString() {
      Assert.hasText(properties.getPassword(), "密码不能为空");
      return properties.getPassword();
    }

    /**
     * 获取盐
     *
     * @return 盐
     */
    protected byte[] getPasswordBytes() {
      return getPasswordString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 创建加密/解密算法对象
     *
     * @param mode               模式
     * @param algorithmParameter 算法参数
     * @return 加密对象
     */
    protected Cipher newCipher(int mode, AlgorithmParameterSpec algorithmParameter) {
      Cipher cipher = SpareCipherUtils.newCipher(algorithm.getMode());
      SecretKey secretKey = newSecretKey();
      SpareCipherUtils.initCipher(cipher, mode, secretKey, algorithmParameter);
      return cipher;
    }

    /**
     * 创建加密算法对象
     *
     * @param algorithmParameter 算法参数
     * @return 加密对象
     */
    protected Cipher newEncryptCipher(AlgorithmParameterSpec algorithmParameter) {
      return newCipher(Cipher.ENCRYPT_MODE, algorithmParameter);
    }

    /**
     * 创建解密算法对象
     *
     * @param algorithmParameter 算法参数
     * @return 加密对象
     */
    protected Cipher newDecryptCipher(AlgorithmParameterSpec algorithmParameter) {
      return newCipher(Cipher.DECRYPT_MODE, algorithmParameter);
    }

    /**
     * 创建密钥
     *
     * @return 密钥
     */
    protected SecretKey newSecretKey() {
      return new SecretKeySpec(getPasswordBytes(), AES);
    }
  }

  /**
   * ECB算法
   */
  @Tag(AES_ECB)
  public static class ECBAlgorithm extends AbstractAESAlgorithm {

    public ECBAlgorithm(CipherProperties properties) {
      super(properties);
    }

    @Override
    protected byte[] decryptBytes(byte[] cipherMessage) {
      Cipher cipher = newDecryptCipher(null);
      return SpareCipherUtils.doFinal(cipher, cipherMessage);
    }

    @Override
    protected byte[] encryptBytes(byte[] plainBytes) {
      Cipher cipher = newEncryptCipher(null);
      return SpareCipherUtils.doFinal(cipher, plainBytes);
    }
  }

  /**
   * GCM算法
   */
  @Tag(AES_GCM)
  public static class GCMAlgorithm extends AbstractAESAlgorithm {

    public GCMAlgorithm(CipherProperties properties) {
      super(properties);
    }

    @Override
    protected byte[] decryptBytes(byte[] cipherMessage) {
      // 进行消息解构
      ByteBuffer byteBuffer = ByteBuffer.wrap(cipherMessage);
      int ivLength = byteBuffer.getInt();
      if (ivLength < IV_LENGTH_MIN || ivLength >= IV_LENGTH_MAX) { // check input parameter
        throw new IllegalArgumentException("Invalid iv length.");
      }
      // 获取向量
      byte[] iv = new byte[ivLength];
      byteBuffer.get(iv);
      byte[] cipherText = new byte[byteBuffer.remaining()];
      // 获取密文
      byteBuffer.get(cipherText);
      // 解密消息
      GCMParameterSpec parameterSpec = getGcmParameterSpec(iv);
      Cipher cipher = newDecryptCipher(parameterSpec);
      return SpareCipherUtils.doFinal(cipher, cipherText);
    }

    @Override
    protected byte[] encryptBytes(byte[] plainBytes) {
      byte[] iv = getRandomBytes(properties.getAes().getIvLength());
      GCMParameterSpec parameterSpec = getGcmParameterSpec(iv);
      Cipher cipher = newEncryptCipher(parameterSpec);
      byte[] cipherBytes = SpareCipherUtils.doFinal(cipher, plainBytes);
      // 构建完整消息
      ByteBuffer byteBuffer = ByteBuffer.allocate(IV_LENGTH_MAX + cipherBytes.length);
      byteBuffer.putInt(iv.length);
      byteBuffer.put(iv);
      byteBuffer.put(cipherBytes);
      return byteBuffer.array();
    }

    private GCMParameterSpec getGcmParameterSpec(byte[] iv) {
      return new GCMParameterSpec(properties.getAes().getSecretKeyLength(), iv);
    }
  }
}
