package site.morn.boot.template.support;

import static site.morn.constant.ApplicationConstants.TemplateTypes.RESOURCE;

import site.morn.bean.annotation.Target;
import site.morn.boot.template.TemplateProperties;
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
public class ResourceTemplateResolver extends AbstractResourceTemplateResolver {

  public ResourceTemplateResolver(TemplateProperties templateProperties, Translator translator) {
    super(templateProperties, translator);
  }
}
