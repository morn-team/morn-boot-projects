package site.morn.test;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Tag;
import site.morn.bean.annotation.Target;
import site.morn.core.BeanProducer;

/**
 * 生产者测试类
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/16
 */
@Component
public class TestProducerBeans {

  public static final String BEAN_PRODUCER = "beanProducer";

  public static final String PRODUCT_ONE = "one";

  @Order(1)
  @Component
  @Tag({BEAN_PRODUCER, PRODUCT_ONE})
  public static class OneProducer implements BeanProducer<String> {

    @Override
    public String product() {
      return "1";
    }
  }

  @Order(2)
  @Component
  @Tag(BEAN_PRODUCER)
  @Target(String.class)
  public static class TwoProducer implements BeanProducer<String> {

    @Override
    public String product() {
      return "2";
    }
  }
}
