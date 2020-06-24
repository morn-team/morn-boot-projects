package site.morn.notify.support;

import lombok.Getter;
import site.morn.notify.template.TemplateNotifyProcessor;
import site.morn.template.TemplateMeta;


/**
 * 抽象模板通知处理者
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/15
 */
@Getter
public abstract class AbstractTemplateNotifyProcessor<T> implements TemplateNotifyProcessor<T> {

  private TemplateMeta templateMeta;

  private T templateContent;

  @Override
  public void setTemplateMeta(TemplateMeta templateMeta) {
    this.templateMeta = templateMeta;
  }

  @Override
  public void setTemplateContent(T content) {
    this.templateContent = content;
  }
}
