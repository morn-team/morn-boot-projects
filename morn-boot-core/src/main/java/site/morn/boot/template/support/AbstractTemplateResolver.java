package site.morn.boot.template.support;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;
import site.morn.boot.template.TemplateProperties;
import site.morn.template.TemplateMeta;
import site.morn.template.TemplateResolver;

/**
 * 抽象模板解析器
 *
 * @param <T> 模板内容类型
 * @author timely-rain
 * @since 1.2.1, 2020/6/15
 */
@Getter
@RequiredArgsConstructor
public abstract class AbstractTemplateResolver<T> implements TemplateResolver<T> {

  /**
   * 模板配置
   */
  private final TemplateProperties templateProperties;

  /**
   * 模板元数据
   */
  private TemplateMeta templateMeta;

  @Override
  public void setTemplateMeta(TemplateMeta templateMeta) {
    this.templateMeta = templateMeta;
  }

  /**
   * 获取模板路径
   *
   * @return 模板路径
   */
  public String getTemplateCode() {
    Assert.notNull(templateMeta, "无法获取模板元数据");
    return templateProperties.getPrefix() + "." + templateMeta.getName();
  }
}
