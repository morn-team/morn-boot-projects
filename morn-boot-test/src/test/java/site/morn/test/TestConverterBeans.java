package site.morn.test;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Tag;
import site.morn.bean.annotation.Target;
import site.morn.core.BeanConverter;

/**
 * 转换器测试类
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/16
 */
@Component
public class TestConverterBeans {

  public static final String CONVERTER = "converter";

  public static final String LOWER_CASE = "lowerCase";


  @Order(1)
  @Component
  @Tag({CONVERTER, LOWER_CASE})
  public static class LowerCaseConverter implements BeanConverter<String, String> {

    @Override
    public String convert(String source) {
      return source.toLowerCase();
    }
  }

  @Order(2)
  @Component
  @Tag(CONVERTER)
  @Target(String.class)
  public static class UpperCaseConverter implements BeanConverter<String, String> {

    @Override
    public String convert(String source) {
      return source.toUpperCase();
    }
  }
}
