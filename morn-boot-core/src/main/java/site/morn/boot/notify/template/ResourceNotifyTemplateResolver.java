package site.morn.boot.notify.template;

import static site.morn.constant.ApplicationConstants.TemplateTypes.RESOURCE;

import site.morn.bean.annotation.Target;
import site.morn.boot.template.TemplateProperties;
import site.morn.boot.template.support.AbstractResourceTemplateResolver;
import site.morn.notify.NotifyMeta;
import site.morn.notify.template.NotifyTemplateResolver;
import site.morn.template.annotation.TemplateType;
import site.morn.translate.Translator;

/**
 * 资源模板解析器
 * <p>模板数据源为{@link org.springframework.context.MessageSource}</p>
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/15
 */
@Target(String.class)
@TemplateType(RESOURCE)
public class ResourceNotifyTemplateResolver extends AbstractResourceTemplateResolver implements
    NotifyTemplateResolver<String> {

  /**
   * 通知元数据
   */
  private NotifyMeta notifyMeta;

  public ResourceNotifyTemplateResolver(TemplateProperties templateProperties,
      Translator translator) {
    super(templateProperties, translator);
  }

  @Override
  public String getTemplateCode() {
    return getTemplateProperties().getPrefix() + "." + notifyMeta.getNotifyType() + "."
        + getTemplateMeta().getName();
  }

  @Override
  public void setNotifyMeta(NotifyMeta notifyMeta) {
    this.notifyMeta = notifyMeta;
  }
}
