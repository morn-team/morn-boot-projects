package site.morn.template;

/**
 * 模板解析器
 *
 * @param <T> 模板数据类型
 * @author timely-rain
 * @since 1.2.1, 2020/6/12
 */
public interface TemplateResolver<T> {

  /**
   * 设置模板元数据
   *
   * @param templateMeta 模板元数据
   */
  void setTemplateMeta(TemplateMeta templateMeta);

  /**
   * 解析模板
   *
   * @return 模板内容
   */
  T resolve();
}
