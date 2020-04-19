package site.morn.test;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Tag;
import site.morn.bean.annotation.Target;
import site.morn.core.BeanValidator;

/**
 * 验证器测试类
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/16
 */
@Component
public class TestValidatorBeans {

  public static final String BEAN_VALIDATOR = "beanProducer";

  public static final String VALIDATOR_ONE = "one";

  @Order(1)
  @Component
  @Tag({BEAN_VALIDATOR, VALIDATOR_ONE})
  public static class OneValidator implements BeanValidator<String> {

    @Override
    public boolean validate(String source) {
      return source.length() == 1;
    }
  }

  @Order(2)
  @Component
  @Tag(BEAN_VALIDATOR)
  @Target(String.class)
  public static class TwoValidator implements BeanValidator<String> {

    @Override
    public boolean validate(String source) {
      return source.length() == 2;
    }
  }
}
