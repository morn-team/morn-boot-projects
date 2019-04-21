package site.morn.boot.bean;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.bean.IdentifiedBeanHolder;

/**
 * site.morn.boot.bean
 *
 * @author timely-rain
 * @since 1.0.0, 2019/4/21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IdentifiedBeanPostProcessorTest {

  /**
   * 标识实例缓存
   */
  @Autowired
  private IdentifiedBeanCache identifiedBeanCache;

  private IdentifiedBeanPostProcessor identifiedBeanPostProcessor;

  @Before
  public void setUp() throws Exception {
    identifiedBeanPostProcessor = new IdentifiedBeanPostProcessor(identifiedBeanCache);
  }

  @Test
  public void generateBeanHolder() {
    IdentifiedBeanHolder<Object> beanHolder = IdentifiedBeanPostProcessor
        .generateBeanHolder(new Object());
    Assert.assertNull("generateBeanHolder", beanHolder);
  }

  @Test
  public void postProcessBeforeInitialization() {
    identifiedBeanPostProcessor.postProcessBeforeInitialization(new Object(), "test1");
  }

  @Test
  public void postProcessAfterInitialization() {
    identifiedBeanPostProcessor.postProcessAfterInitialization(new Object(), "test1");
  }
}