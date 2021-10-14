package site.morn.boot.rest;

import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import site.morn.bean.BeanPool;
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
  private final BeanPool beanPool;

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
    RestBuilder.initialize(beanPool, translator, restProperties);
  }
}
