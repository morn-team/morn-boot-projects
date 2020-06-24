package site.morn.notify;

import site.morn.template.TemplateMeta;

/**
 * 通知元数据适配器
 * <p>由于大部分通知依赖模板实现，因此通知适配器也同时适配了模板</p>
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
public interface NotifyMetaAdapter {

  /**
   * 设置通知元数据
   *
   * @param notifyMeta 通知元数据
   */
  void setNotifyMeta(NotifyMeta notifyMeta);

  /**
   * 适配通知元数据
   *
   * @return 适配后的通知元数据
   */
  NotifyMeta adaptionNotifyMeta();

  /**
   * 设置模板元数据
   *
   * @param templateMeta 模板元数据
   */
  void setTemplateMeta(TemplateMeta templateMeta);

  /**
   * 适配模板元数据
   *
   * @return 适配后的模板元数据
   */
  TemplateMeta adaptionTemplateMeta();
}
