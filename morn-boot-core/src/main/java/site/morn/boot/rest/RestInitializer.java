package site.morn.boot.rest;

import javax.annotation.PostConstruct;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.rest.Rests;
import site.morn.translate.Translator;

/**
 * REST初始化
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/29
 */
public class RestInitializer {

  private final IdentifiedBeanCache identifiedBeanCache;

  private final Translator translator;

  public RestInitializer(IdentifiedBeanCache identifiedBeanCache,
      Translator translator) {
    this.identifiedBeanCache = identifiedBeanCache;
    this.translator = translator;
  }

  @PostConstruct
  public void initRest() {
    Rests.initialize(identifiedBeanCache, translator);
  }
}
