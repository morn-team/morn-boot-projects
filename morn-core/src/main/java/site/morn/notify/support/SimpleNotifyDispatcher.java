package site.morn.notify.support;

import java.util.List;
import java.util.Objects;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import site.morn.bean.annotation.Objective;
import site.morn.bean.support.BeanCaches;
import site.morn.bean.support.Tags;
import site.morn.notify.NotifyDispatcher;
import site.morn.notify.NotifyMeta;
import site.morn.notify.NotifyProcessor;
import site.morn.notify.annotation.NotifyType;
import site.morn.notify.template.NotifyTemplateResolver;
import site.morn.notify.template.TemplateNotifyProcessor;
import site.morn.template.TemplateMeta;
import site.morn.template.annotation.TemplateType;
import site.morn.util.BeanFunctionUtils;

/**
 * 默认通知调度者
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/12
 */
@Objective
public class SimpleNotifyDispatcher implements NotifyDispatcher {

  private TemplateMeta templateMeta;

  @Override
  public void setTemplateMeta(TemplateMeta templateMeta) {
    this.templateMeta = templateMeta;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void handle(NotifyMeta notifyMeta) {
    Tags tags = Tags.from(NotifyType.class, notifyMeta.getNotifyType());
    if (Objects.isNull(templateMeta)) {
      // 执行常规通知处理
      BeanFunctionUtils.processes(NotifyProcessor.class, notifyMeta, tags.toArray());
    } else {
      Object content = resolveTemplate(notifyMeta);
      // 执行模板通知处理
      List<TemplateNotifyProcessor> processors = BeanCaches
          .tagBeans(TemplateNotifyProcessor.class, tags.toArray());
      for (TemplateNotifyProcessor processor : processors) {
        processor.setTemplateMeta(templateMeta);
        processor.setTemplateContent(content);
        processor.handle(notifyMeta);
      }
    }
  }

  /**
   * 解析模板
   *
   * @return 模板内容
   */
  @SuppressWarnings("unchecked")
  public <T> T resolveTemplate(NotifyMeta notifyMeta) {
    String[] tags = Tags.from(TemplateType.class, templateMeta.getType()).toArray();
    NotifyTemplateResolver<T> resolver = BeanCaches.tagBean(NotifyTemplateResolver.class, tags);
    Assert.notNull(resolver, "无法获取通知模板解析器:" + StringUtils.arrayToCommaDelimitedString(tags));
    resolver.setNotifyMeta(notifyMeta);
    resolver.setTemplateMeta(templateMeta);
    return resolver.resolve();
  }
}
