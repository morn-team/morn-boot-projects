package site.morn.boot.translate;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import site.morn.translate.Translator;
import site.morn.translate.Translators;

/**
 * 翻译初始化
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/10
 */
@RequiredArgsConstructor
public class TranslateInitializer {

  /**
   * 翻译器
   */
  private final Translator translator;

  @PostConstruct
  public void initialize() {
    Translators.initialize(translator);
  }
}
