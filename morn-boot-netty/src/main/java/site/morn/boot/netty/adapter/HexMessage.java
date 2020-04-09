package site.morn.boot.netty.adapter;

import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import site.morn.boot.netty.util.HexUtils;

/**
 * 十六进制消息
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/13
 */
@Getter
@Setter
public class HexMessage {

  /**
   * 十六进制数组
   */
  private byte[] array;

  /**
   * 十六进制字符串
   */
  private String message;

  public HexMessage() {
    this.array = new byte[0];
    this.message = "";
  }

  public HexMessage(byte[] array) {
    this.array = array;
    this.message = HexUtils.encodeHexString(this.array);
  }

  public HexMessage(byte[] array, int start) {
    this(array, start, array.length - start);
  }

  public HexMessage(byte[] array, int start, int length) {
    this.array = Arrays.copyOfRange(array, start, start + length);
    this.message = HexUtils.encodeHexString(this.array);
  }

  public HexMessage(String message) {
    this.message = message;
    this.array = HexUtils.decodeHex(message);
  }

  public HexMessage(HexMessage hexMessage) {
    this.array = hexMessage.getArray();
    this.message = hexMessage.getMessage();
  }

  public static HexMessage afterData(byte[] array, int start, int length, int dataStart,
      HexMessage dataLength) {
    Integer dataLengthValue = dataLength.getInteger();
    int lengthEnd = dataStart + dataLengthValue;
    return new HexMessage(array, lengthEnd + start, length);
  }

  @Override
  public String toString() {
    return message;
  }

  /**
   * 添加
   *
   * @param value 十进制整数
   * @return 十六进制消息
   */
  public HexMessage addByte(int value) {
    return addNumber(value, 2);
  }

  /**
   * 添加
   *
   * @param value 十进制整数
   * @return 十六进制消息
   */
  public HexMessage addInt(int value) {
    return addNumber(value, 4);
  }

  /**
   * 添加
   *
   * @param msg 十六进制字符串
   * @return 十六进制消息
   */
  public HexMessage addHexString(String msg) {
    return addHexMessage(new HexMessage(msg));
  }

  /**
   * 添加
   *
   * @param hexMessage 十六进制消息
   * @return 十六进制消息
   */
  public HexMessage addHexMessage(HexMessage hexMessage) {
    this.array = ArrayUtils.addAll(this.array, hexMessage.array);
    this.message += hexMessage.message;
    return this;
  }

  /**
   * 获取十六进制消息
   *
   * @param start 起始位
   * @return 十六进制消息
   */
  public HexMessage getHexMessage(int start) {
    return new HexMessage(this.array, start);
  }

  /**
   * 获取十六进制消息
   *
   * @param start 起始位
   * @param length 长度
   * @return 十六进制消息
   */
  public HexMessage getHexMessage(int start, int length) {
    byte[] bytes = Arrays.copyOfRange(this.array, start, start + length);
    return new HexMessage(bytes);
  }

  /**
   * 获取十六进制消息
   *
   * @param start 起始位
   * @param length 长度
   * @param dataStart 数据位
   * @param dataLength 数据长度
   * @return 十六进制消息
   */
  public HexMessage getHexMessageAfterData(int start, int length, int dataStart, int dataLength) {
    int lengthEnd = dataStart + dataLength;
    return new HexMessage(array, lengthEnd + start, length);
  }

  /**
   * 获取十六进制消息
   *
   * @param start 起始位
   * @param length 长度
   * @param dataStart 数据位
   * @param dataLength 数据长度
   * @return 十六进制消息
   */
  public HexMessage getHexMessageAfterData(int start, int length, int dataStart,
      HexMessage dataLength) {
    Integer dataLengthValue = dataLength.getInteger();
    return getHexMessageAfterData(start, length, dataStart, dataLengthValue);
  }

  /**
   * 反转
   *
   * @return 十六进制消息
   */
  public HexMessage reverse() {
    ArrayUtils.reverse(this.array);
    this.message = HexUtils.encodeHexString(this.array);
    return this;
  }

  /**
   * 获取十进制整数
   *
   * @return 十进制整数
   */
  public Integer getInteger() {
    return Integer.valueOf(getMessage(), 16);
  }

  /**
   * 添加
   *
   * @param value 十进制整数
   * @param length 长度
   * @return 十六进制消息
   */
  private HexMessage addNumber(int value, int length) {
    String template = "%0" + length + "x";
    String hexString = String.format(template, value).toUpperCase(); // 16进制补0
    HexMessage hexMessage = new HexMessage(hexString);
    return this.addHexMessage(hexMessage);
  }
}
