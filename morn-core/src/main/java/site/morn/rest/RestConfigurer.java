package site.morn.rest;

/**
 * REST配置
 *
 * @author timely-rain
 * @since 1.2.2, 2020/7/25
 */
public interface RestConfigurer {

  /**
   * 添加序列消息类
   *
   * @param registry 序列消息注册表
   */
  default void addSerialMessageClass(SerialMessageRegistry registry) {
  }
}
