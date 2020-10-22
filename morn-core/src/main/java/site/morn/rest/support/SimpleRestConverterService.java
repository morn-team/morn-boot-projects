package site.morn.rest.support;

import site.morn.core.support.AbstractBeanConverterService;
import site.morn.rest.RestConverter;
import site.morn.rest.RestConverterService;

/**
 * 默认REST消息转换服务
 *
 * @author timely-rain
 * @since 1.2.2, 2020/7/24
 */
public class SimpleRestConverterService extends AbstractBeanConverterService implements
    RestConverterService {

  public SimpleRestConverterService() {
    super(RestConverter.class);
  }
}
