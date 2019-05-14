package site.morn.translate;

import lombok.Getter;
import lombok.Setter;

/**
 * 国际化配置项
 *
 * @author TianGanLin
 * @since 0.0.1-SNAPSHOT, 2019/3/8
 */
@Getter
@Setter
public class TranslateProperties {

  /**
   * 语言
   */
  private String language;

  /**
   * 国家
   */
  private String country;
}
