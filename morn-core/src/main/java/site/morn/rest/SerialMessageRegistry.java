package site.morn.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * 序列消息注册表
 *
 * @author timely-rain
 * @see SerialMessage
 * @since 1.2.2, 2020/7/25
 */
public class SerialMessageRegistry {

  /**
   * 序列消息类集合
   */
  private final List<Class<?>> serialMessageClasses = new ArrayList<>();

  /**
   * 注册序列消息类
   *
   * @param messageClass 序列消息类
   */
  public void register(Class<?> messageClass) {
    serialMessageClasses.add(messageClass);
  }

  /**
   * 获取序列消息类集合
   *
   * @return 序列消息类集合
   */
  public List<Class<?>> getSerialMessageClasses() {
    return serialMessageClasses;
  }
}
