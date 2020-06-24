package site.morn.boot.template;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 模板配置项
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/12
 */
@Getter
@Setter
@ConfigurationProperties("morn.template")
public class TemplateProperties {

  private String prefix;

  public TemplateProperties() {
    this.prefix = "tpl";
  }
}
