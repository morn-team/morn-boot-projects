package site.morn.template;

import lombok.Data;
import site.morn.core.CriteriaMap;

/**
 * 模板元数据
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
@Data
public class TemplateMeta {

  /**
   * 模板类型
   */
  private String type;

  /**
   * 模板名称
   */
  private String name;

  /**
   * 模板参数
   */
  private CriteriaMap args;
}
