package site.morn.boot.translate;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import site.morn.translate.Translator;

/**
 * 默认Spring翻译器
 *
 * @author timely-rain
 * @version 1.0.0, 2018/8/19
 * @since 1.0
 */
public class DefaultSpringTranslator implements Translator {

  private final MessageSource messageSource;

  @Autowired
  public DefaultSpringTranslator(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public String translate(String code, Object... args) {
    return messageSource.getMessage(code, args, code, LocaleContextHolder.getLocale());
  }

  @Override
  public String translate(Locale locale, String code, Object... args) {
    return messageSource.getMessage(code, args, code, locale);
  }
}
