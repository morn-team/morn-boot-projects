package site.morn.boot.message;

import java.util.Map;
import java.util.UUID;
import org.springframework.messaging.MessageHeaders;

/**
 * 广播消息头
 *
 * <p>消息头一旦生成无法修改，可使用{@link BroadcastMessageHeaderAccessor}重新生成
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/9
 */
public class BroadcastMessageHeaders extends MessageHeaders {

  /**
   * 主题
   */
  public static final String TOPIC = "topic";

  /**
   * 标签
   */
  public static final String TAG = "tag";

  /**
   * 类型
   */
  public static final String TYPE = "type";

  public BroadcastMessageHeaders(Map<String, Object> headers) {
    super(headers);
  }

  protected BroadcastMessageHeaders(Map<String, Object> headers, UUID id, Long timestamp) {
    super(headers, id, timestamp);
  }

  /**
   * 获取主题
   *
   * <p>通常描述发送的目标
   *
   * @return 主题名称
   */
  public String getTopic() {
    return get(TOPIC, String.class);
  }

  /**
   * 获取标签
   *
   * <p>通常描述消息的特征
   *
   * @return 标签数组
   */
  public String[] getTag() {
    return get(TAG, String[].class);
  }

  /**
   * 获取类型
   *
   * <p>通常描述消息的作用
   *
   * @return 类型名称
   */
  public String getType() {
    return get(TYPE, String.class);
  }
}
