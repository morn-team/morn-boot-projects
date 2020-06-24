package site.morn.notify.support;

import lombok.Getter;
import site.morn.notify.AnnotationNotifyMetaAdapter;
import site.morn.notify.NotifyMeta;
import site.morn.notify.annotation.Notify;
import site.morn.notify.annotation.NotifyReceiver;
import site.morn.template.TemplateMeta;
import site.morn.template.annotation.Template;

/**
 * 注解通知适配器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
@Getter
public abstract class AbstractAnnotationNotifyMetaAdapter implements AnnotationNotifyMetaAdapter {

  /**
   * 通知
   */
  private Notify notify;

  /**
   * 通知接收人
   */
  private NotifyReceiver receiver;

  /**
   * 通知模板
   */
  private Template template;

  /**
   * 通知元数据
   */
  private NotifyMeta notifyMeta;

  /**
   * 模板元数据
   */
  private TemplateMeta templateMeta;

  @Override
  public void setNotify(Notify notify) {
    this.notify = notify;
  }

  @Override
  public void setReceiver(NotifyReceiver receiver) {
    this.receiver = receiver;
  }

  @Override
  public void setTemplate(Template template) {
    this.template = template;
  }

  @Override
  public void setNotifyMeta(NotifyMeta notifyMeta) {
    this.notifyMeta = notifyMeta;
  }

  @Override
  public void setTemplateMeta(TemplateMeta templateMeta) {
    this.templateMeta = templateMeta;
  }
}
