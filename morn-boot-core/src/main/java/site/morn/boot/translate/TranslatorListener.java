package site.morn.boot.translate;

import java.util.Map;
import java.util.Objects;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;
import site.morn.boot.core.annotation.Name;
import site.timely.translate.TranslateHolder;
import site.timely.translate.Translator;

/**
 * 翻译监听器
 *
 * @author timely-rain
 * @version 1.0.0, 2018/10/15
 * @since 1.0
 */
public class TranslatorListener {

  @EventListener
  public void ready(ApplicationReadyEvent event) {
    ConfigurableApplicationContext context = event.getApplicationContext();
    Map<String, Translator> translatorMap = context.getBeansOfType(Translator.class);
    for (Translator translator : translatorMap.values()) {
      Class<? extends Translator> translatorClass = translator.getClass();
      Name name = AnnotationUtils.findAnnotation(translatorClass, Name.class);
      if (Objects.isNull(name) || StringUtils.isEmpty(name.value())) {
        TranslateHolder.register(TranslateHolder.DEFAULT, translator);
      } else {
        TranslateHolder.register(name.value(), translator);
      }
    }
  }
}
