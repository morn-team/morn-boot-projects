package site.morn.test;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Tag;
import site.morn.bean.annotation.Target;
import site.morn.core.BeanProcessor;

/**
 * 处理者测试类
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/16
 */
@Component
public class TestProcessorBeans {

  public static final String BEAN_PROCESSOR = "beanProcessor";

  public static final String HANDLE_ONE = "one";

  public interface ProcessorTwo {

  }

  /**
   * 测试处理者接口
   */
  public interface TestProcessor extends BeanProcessor<StringBuilder> {

    @Override
    void handle(StringBuilder source);
  }

  @Order(1)
  @Component
  @Tag({BEAN_PROCESSOR, HANDLE_ONE})
  public static class OneProcessor implements TestProcessor {

    @Override
    public void handle(StringBuilder source) {
      source.append(1);
    }
  }

  @Order(2)
  @Component
  @Tag(BEAN_PROCESSOR)
  @Target(ProcessorTwo.class)
  public static class TwoProcessor implements TestProcessor {

    @Override
    public void handle(StringBuilder source) {
      source.append(2);
    }
  }
}
