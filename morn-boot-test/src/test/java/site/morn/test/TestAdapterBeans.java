package site.morn.test;

import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Tag;
import site.morn.bean.annotation.Target;
import site.morn.core.BeanAdapter;

/**
 * 适配器测试类
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/16
 */
@Component
public class TestAdapterBeans {

  /**
   * 算术
   */
  public static final String ARITHMETIC = "arithmetic";

  /**
   * 加法
   */
  public static final String ADD = "add";

  /**
   * 减法
   */
  public interface Sub {

  }

  /**
   * 测试适配器接口
   */
  public interface TestAdapter extends BeanAdapter<Integer> {

    @Override
    Integer adaption(Integer source);
  }

  /**
   * 加法适配器
   *
   * @apiNote 加1
   */
  @Tag({ARITHMETIC, ADD})
  @Component
  public static class AdditionOneAdapter implements TestAdapter {

    @Override
    public Integer adaption(Integer source) {
      return source + 1;
    }
  }

  /**
   * 减法适配器
   *
   * @apiNote 减2
   */
  @Tag(ARITHMETIC)
  @Target(Sub.class)
  @Component
  public static class SubtractionTwoAdapter implements TestAdapter {

    @Override
    public Integer adaption(Integer source) {
      return source - 2;
    }
  }
}
