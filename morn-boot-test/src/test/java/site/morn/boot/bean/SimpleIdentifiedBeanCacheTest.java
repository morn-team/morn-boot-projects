package site.morn.boot.bean;


import java.util.List;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.bean.AnnotationIdentifyCase;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.bean.annotation.Function;
import site.morn.bean.annotation.Name;
import site.morn.bean.annotation.Tag;
import site.morn.bean.annotation.Target;
import site.morn.util.ArrayUtils;

/**
 * 实例缓存单元测试
 *
 * @author timely-rain
 * @since 1.0.0, 2018/11/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleIdentifiedBeanCacheTest {

  /**
   * 标识实例缓存
   */
  @Autowired
  private IdentifiedBeanCache identifiedBeanCache;

  @Autowired
  private IdentifiedBeanPostProcessor beanPostProcessor;

  @Test
  public void cache() {
    Assert.assertNotNull(identifiedBeanCache);
    // 实例化Bean
    TestBeanA testBeanA = new TestBeanA();
    TestBeanB testBeanB = new TestBeanB();
    TestBeanC testBeanC = new TestBeanC();
    // 注册Bean
    identifiedBeanCache.cache(beanPostProcessor.generateBeanHolder(testBeanA));
    identifiedBeanCache.cache(beanPostProcessor.generateBeanHolder(testBeanB));
    identifiedBeanCache.cache(beanPostProcessor.generateBeanHolder(testBeanC));
  }

  @Test
  public void searchByName() {
    // 测试Name
    Object name = identifiedBeanCache.nameBean(Object.class, "testBeanA");
    Assert.assertTrue(name instanceof TestBeanA);
  }

  @Test
  public void searchByTags() {
    // 测试Tags
    List<Object> odd = identifiedBeanCache.tagBeans(Object.class, "odd");
    Assert.assertEquals(2, odd.size());
  }

  @Test
  public void searchByTarget() {
    // 测试Target
    List<Object> target = identifiedBeanCache.targetBeans(Object.class, TestBeanB.class);
    Assert.assertEquals(2, target.size());
  }

  @Test
  public void search() {
    // 测试BeanIdentify
    AnnotationIdentifyCase annotationIdentifyCase = AnnotationIdentifyCase.builder()
        .tags(ArrayUtils.merge("odd"))
        .target(TestBeanB.class)
        .build();
    List<Object> search = identifiedBeanCache.beans(Object.class, annotationIdentifyCase);
    Assert.assertEquals(2, search.size());
  }

  @Name("testBeanA")
  @Tag("odd")
  @Target(TestBeanB.class)
  private class TestBeanA {

    @Function
    public void doSomething() {

    }

    @Function("return")
    public Object returnSomething(String s, Object o) {
      return new Object();
    }

    @Function("return")
    public Object returnAnthorThing() {
      return new Object();
    }
  }

  @Name("testBeanB")
  @Tag("even")
  private class TestBeanB {

  }

  @Name("testBeanC")
  @Tag("odd")
  @Target(TestBeanB.class)
  private class TestBeanC {

  }
}