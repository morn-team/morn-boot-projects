package site.morn.boot.translate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 国际化配置项
 *
 * @author timely-rain
 * @since 0.0.1, 2019/3/8
 */
@Getter
@Setter
@ConfigurationProperties("morn.translator")
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
