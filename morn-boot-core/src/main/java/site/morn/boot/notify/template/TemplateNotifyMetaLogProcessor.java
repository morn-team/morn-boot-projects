package site.morn.boot.notify.template;

import lombok.extern.slf4j.Slf4j;
import site.morn.notify.NotifyMeta;
import site.morn.notify.annotation.NotifyType;
import site.morn.notify.support.AbstractTemplateNotifyProcessor;

/**
 * 通知日志处理者
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
@Slf4j
@NotifyType
public class TemplateNotifyMetaLogProcessor extends AbstractTemplateNotifyProcessor<String> {

  @Override
  public void handle(NotifyMeta meta) {
    log.info("Notify|{}, Content:{}", meta, getTemplateContent());
  }
}
