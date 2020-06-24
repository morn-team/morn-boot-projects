package site.morn.notify.support;

import static site.morn.constant.ApplicationConstant.Notify.NOTIFY_RECEIVER_VALUE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;
import site.morn.core.CriteriaMap;

/**
 * 通知参数
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
public class NotifyArgs extends CriteriaMap {

  public NotifyArgs() {
  }

  public NotifyArgs(Map<String, Object> map) {
    super(map);
  }

  /**
   * 新增接收人标识
   *
   * @param receiverValue 接收人标识
   */
  public void addReceiverValue(String receiverValue) {
    List<String> list = getReceiverValues();
    list.add(receiverValue);
  }

  /**
   * 获取接收人标识集合
   *
   * @return 接收人标识集合
   */
  public List<String> getReceiverValues() {
    List<String> list = getExpect(NOTIFY_RECEIVER_VALUE);
    if (CollectionUtils.isEmpty(list)) {
      list = new ArrayList<>();
      set(NOTIFY_RECEIVER_VALUE, list);
    }
    return list;
  }
}
