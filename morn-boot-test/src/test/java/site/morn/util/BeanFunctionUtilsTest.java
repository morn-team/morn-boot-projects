package site.morn.util;

import static site.morn.test.TestAdapterBeans.ADD;
import static site.morn.test.TestAdapterBeans.ARITHMETIC;
import static site.morn.test.TestConverterBeans.CONVERTER;
import static site.morn.test.TestConverterBeans.LOWER_CASE;
import static site.morn.test.TestProcessorBeans.BEAN_PROCESSOR;
import static site.morn.test.TestProducerBeans.PRODUCT_ONE;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.core.BeanProducer;
import site.morn.core.BeanValidator;
import site.morn.test.TestAdapterBeans.Sub;
import site.morn.test.TestAdapterBeans.TestAdapter;
import site.morn.test.TestConverterBeans.TestConverter;
import site.morn.test.TestProcessorBeans.ProcessorTwo;
import site.morn.test.TestProcessorBeans.TestProcessor;


/**
 * 函数工具测试类
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/17
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BeanFunctionUtilsTest {

  @Test
  public void adaption() {
    Integer result = BeanFunctionUtils.adaption(TestAdapter.class, 1, ADD);
    Assert.assertEquals(2, result.intValue());
  }

  @Test
  public void testAdaption() {
    Integer result = BeanFunctionUtils.adaption(TestAdapter.class, 2, Sub.class);
    Assert.assertEquals(0, result.intValue());
  }

  @Test
  public void adaptions() {
    Integer result = BeanFunctionUtils.adaptions(TestAdapter.class, 1, ARITHMETIC);
    Assert.assertEquals(0, result.intValue());
  }

  @Test
  public void convert() {
    String result = BeanFunctionUtils.convert(TestConverter.class, "Hello", LOWER_CASE);
    Assert.assertEquals("hello", result);
  }

  @Test
  public void testConvert() {
    String result = BeanFunctionUtils.convert(TestConverter.class, "Hello", String.class);
    Assert.assertEquals("HELLO", result);
  }

  @Test
  public void converts() {
    List<String> result = BeanFunctionUtils.converts(TestConverter.class, "Hello", CONVERTER);
    Assert.assertArrayEquals(new String[]{"hello", "HELLO"}, result.toArray());
  }

  @Test
  public void process() {
    StringBuilder stringBuilder = new StringBuilder();
    BeanFunctionUtils.process(TestProcessor.class, stringBuilder, ProcessorTwo.class);
    Assert.assertEquals("2", stringBuilder.toString());
  }

  @Test
  public void processes() {
    StringBuilder stringBuilder = new StringBuilder();
    BeanFunctionUtils.processes(TestProcessor.class, stringBuilder, ProcessorTwo.class);
    Assert.assertEquals("2", stringBuilder.toString());
  }

  @Test
  public void testProcesses() {
    StringBuilder stringBuilder = new StringBuilder();
    BeanFunctionUtils.processes(TestProcessor.class, stringBuilder, BEAN_PROCESSOR);
    Assert.assertEquals("12", stringBuilder.toString());
  }

  @Test
  public void product() {
    String result = BeanFunctionUtils.product(BeanProducer.class, PRODUCT_ONE);
    Assert.assertEquals("1", result);
  }

  @Test
  public void testProduct() {
    String result = BeanFunctionUtils.product(BeanProducer.class, String.class);
    Assert.assertEquals("2", result);
  }

  @Test
  public void validates() {
    boolean result = BeanFunctionUtils.validates(BeanValidator.class, "ab");
    Assert.assertTrue(result);
  }
}