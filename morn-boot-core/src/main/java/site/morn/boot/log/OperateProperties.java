package site.morn.boot.log;

import static site.morn.log.OperateModes.SIMPLE;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import site.morn.boot.translate.TranslateProperties;
import site.morn.log.OperateModes;
import site.morn.translate.Translator;

/**
 * 操作日志配置项
 *
 * @author timely-rain
 * @since 1.2.2, 2021/7/2
 */
@Getter
@Setter
@ConfigurationProperties("morn.operate")
public class OperateProperties {

  /**
   * 设置异步执行操作日志，否则为同步执行
   */
  private boolean async = true;

  /**
   * 操作日志记录模式
   *
   * @see OperateModes
   */
  private String mode = SIMPLE;

  /**
   * 简易模式配置项
   */
  private Simple simple = new Simple();

  /**
   * 国际化配置项
   *
   * <p>使用国际化日志时，将沿用{@link Translator}相关组件和配置</p>
   *
   * @see site.morn.translate.Translator
   * @see TranslateProperties
   */
  private I18n i18n = new I18n();

  /**
   * 简易模式配置项
   */
  @Getter
  @Setter
  public static class Simple {

    /**
     * 变量占位符，前缀
     */
    private String placeholderPrefix = "{";

    /**
     * 变量占位符，后缀
     */
    private String placeholderSuffix = "}";
  }

  /**
   * 国际化配置项
   */
  @Getter
  @Setter
  public static class I18n {

    /**
     * 国际化前缀
     */
    private String prefix = "log";

    /**
     * 分隔符
     */
    private String delimiter = ".";
  }
}
