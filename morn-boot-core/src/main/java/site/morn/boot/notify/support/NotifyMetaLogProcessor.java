package site.morn.boot.notify.support;

import lombok.extern.slf4j.Slf4j;
import site.morn.notify.NotifyMeta;
import site.morn.notify.NotifyProcessor;
import site.morn.notify.annotation.NotifyType;

/**
 * 通知日志处理者
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
@Slf4j
@NotifyType
public class NotifyMetaLogProcessor implements NotifyProcessor {

  @Override
  public void handle(NotifyMeta meta) {
    log.info("Notify|{}", meta);
  }
}
