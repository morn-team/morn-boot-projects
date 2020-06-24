package site.morn.notify;

import site.morn.notify.annotation.Notify;
import site.morn.notify.annotation.NotifyReceiver;
import site.morn.template.annotation.Template;

/**
 * 注解式通知元数据适配器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
public interface AnnotationNotifyMetaAdapter extends NotifyMetaAdapter {

  /**
   * 设置通知
   *
   * @param notify 通知
   */
  void setNotify(Notify notify);

  /**
   * 设置通知接收人
   *
   * @param receiver 通知接收人
   */
  void setReceiver(NotifyReceiver receiver);

  /**
   * 设置通知模板
   *
   * @param template 通知模板
   */
  void setTemplate(Template template);
}
