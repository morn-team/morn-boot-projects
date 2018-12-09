package site.morn.boot.translate;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.translate.Transfer;
import site.morn.translate.TranslateChanger;
import site.morn.translate.Translator;

/**
 * 默认Spring翻译器
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/19
 * @since 1.0
 */
public class DefaultSpringTranslator implements Translator {

  /**
   * Spring国际化
   */
  private final MessageSource messageSource;

  /**
   * 实例缓存
   */
  private final IdentifiedBeanCache beanCache;

  public DefaultSpringTranslator(MessageSource messageSource,
      IdentifiedBeanCache beanCache) {
    this.messageSource = messageSource;
    this.beanCache = beanCache;
  }

  @Override
  public String translate(String code, Object... args) {
    return messageSource.getMessage(code, args, code, LocaleContextHolder.getLocale());
  }

  @Override
  public String translate(Locale locale, String code, Object... args) {
    return messageSource.getMessage(code, args, code, locale);
  }

  @Override
  public <T> T translate(Transfer transfer, Class<T> cls) {
    TranslateChanger<T> translateChanger = beanCache.bean(TranslateChanger.class, cls);
    return translateChanger.change(transfer);
  }
}
