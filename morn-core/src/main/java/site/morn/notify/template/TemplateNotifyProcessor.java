package site.morn.notify.template;

import site.morn.core.BeanProcessor;
import site.morn.notify.NotifyMeta;
import site.morn.template.TemplateMeta;

/**
 * 模板通知处理者
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/12
 */
public interface TemplateNotifyProcessor<T> extends BeanProcessor<NotifyMeta> {

  /**
   * 设置模板元数据
   *
   * @param templateMeta 模板元数据
   */
  void setTemplateMeta(TemplateMeta templateMeta);

  /***
   * 设置模板内容
   * @param content 模板内容
   */
  void setTemplateContent(T content);
}
