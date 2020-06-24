package site.morn.boot.notify.template;

import lombok.Getter;
import site.morn.boot.template.TemplateProperties;
import site.morn.boot.template.support.AbstractTemplateResolver;
import site.morn.notify.NotifyMeta;
import site.morn.notify.template.NotifyTemplateResolver;

/**
 * 抽象通知模板解析器
 *
 * @param <T> 模板内容类型
 * @author timely-rain
 * @since 1.2.1, 2020/6/15
 */
@Getter
public abstract class AbstractNotifyTemplateResolver<T> extends
    AbstractTemplateResolver<T> implements NotifyTemplateResolver<T> {

  /**
   * 通知元数据
   */
  private NotifyMeta notifyMeta;

  public AbstractNotifyTemplateResolver(TemplateProperties templateProperties) {
    super(templateProperties);
  }

  @Override
  public void setNotifyMeta(NotifyMeta notifyMeta) {
    this.notifyMeta = notifyMeta;
  }
}
