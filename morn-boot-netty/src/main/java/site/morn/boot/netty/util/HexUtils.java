package site.morn.boot.netty.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import site.morn.exception.ApplicationMessages;

/**
 * 十六进制工具类
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/13
 */
@Slf4j
public class HexUtils extends Hex {

  /**
   * Converts a String representing hexadecimal values into an array of bytes of those same values.
   * The returned array will be half the length of the passed String, as it takes two characters to
   * represent any given byte. An exception is thrown if the passed String has an odd number of
   * elements.
   *
   * @param data A String containing hexadecimal digits
   * @return A byte array containing binary data decoded from the supplied char array.
   * @since 1.11
   */
  public static byte[] decodeHex(final String data) {
    try {
      return decodeHex(data.toCharArray());
    } catch (DecoderException e) {
      log.error(e.getMessage(), e);
      throw ApplicationMessages.translateException("hex.decode-failure", data);
    }
  }

  /**
   * Converts an array of bytes into an array of characters representing the hexadecimal values of
   * each byte in order. The returned array will be double the length of the passed array, as it
   * takes two characters to represent any given byte.
   *
   * @param data a byte[] to convert to Hex characters
   * @return A char[] containing lower-case hexadecimal characters
   */
  public static char[] encodeHex(final byte[] data) {
    return encodeHex(data, false);
  }

  /**
   * Converts an array of bytes into a String representing the hexadecimal values of each byte in
   * order. The returned String will be double the length of the passed array, as it takes two
   * characters to represent any given byte.
   *
   * @param data a byte[] to convert to Hex characters
   * @return A String containing lower-case hexadecimal characters
   * @since 1.4
   */
  public static String encodeHexString(final byte[] data) {
    return new String(encodeHex(data));
  }
}
