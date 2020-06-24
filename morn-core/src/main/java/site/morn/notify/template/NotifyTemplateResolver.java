package site.morn.notify.template;

import site.morn.notify.NotifyMeta;
import site.morn.template.TemplateResolver;

/**
 * 通知模板解析器
 *
 * @param <T> 模板数据类型
 * @author timely-rain
 * @since 1.2.1, 2020/6/22
 */
public interface NotifyTemplateResolver<T> extends TemplateResolver<T> {

  /**
   * 设置通知元数据
   *
   * @param notifyMeta 通知元数据
   */
  void setNotifyMeta(NotifyMeta notifyMeta);
}
