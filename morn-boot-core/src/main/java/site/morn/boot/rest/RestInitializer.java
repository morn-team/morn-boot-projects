package site.morn.boot.rest;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import site.morn.bean.BeanCache;
import site.morn.rest.RestBuilder;
import site.morn.rest.RestProperties;
import site.morn.translate.Translator;

/**
 * REST初始化
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/29
 */
@RequiredArgsConstructor
public class RestInitializer {

  /**
   * 实例缓存
   */
  private final BeanCache beanCache;

  /**
   * 翻译器
   */
  private final Translator translator;

  /**
   * REST配置项
   */
  private final RestProperties restProperties;

  @PostConstruct
  public void initRest() {
    RestBuilder.initialize(beanCache, translator, restProperties);
  }
}
