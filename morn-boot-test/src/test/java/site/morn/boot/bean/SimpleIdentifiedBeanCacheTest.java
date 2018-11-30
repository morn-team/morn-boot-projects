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
import site.morn.bean.BeanIdentify;
import site.morn.bean.IdentifiedBeanCache;
import site.morn.bean.annotation.Name;
import site.morn.bean.annotation.Tag;
import site.morn.bean.annotation.Target;
import site.morn.util.ArrayUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleIdentifiedBeanCacheTest {

  /**
   * 标识实例缓存
   */
  @Autowired
  private IdentifiedBeanCache identifiedBeanCache;

  @Test
  public void register() {
    Assert.assertNotNull(identifiedBeanCache);
    // 实例化Bean
    TestBeanA testBeanA = new TestBeanA();
    TestBeanB testBeanB = new TestBeanB();
    TestBeanC testBeanC = new TestBeanC();
    // 注册Bean
    identifiedBeanCache.register(IdentifiedBeanPostProcessor.generateBeanHolder(testBeanA));
    identifiedBeanCache.register(IdentifiedBeanPostProcessor.generateBeanHolder(testBeanB));
    identifiedBeanCache.register(IdentifiedBeanPostProcessor.generateBeanHolder(testBeanC));
  }

  @Test
  public void searchByName() {
    // 测试Name
    List<Object> name = identifiedBeanCache.searchByName(Object.class, "testBeanA");
    Assert.assertEquals(1, name.size());
  }

  @Test
  public void searchByTags() {
    // 测试Tags
    List<Object> odd = identifiedBeanCache.searchByTags(Object.class, "odd");
    Assert.assertEquals(2, odd.size());
  }

  @Test
  public void searchByTarget() {
    // 测试Target
    List<Object> target = identifiedBeanCache.searchByTarget(Object.class, TestBeanB.class);
    Assert.assertEquals(2, target.size());
  }

  @Test
  public void search() {
    // 测试BeanIdentify
    BeanIdentify beanIdentify = BeanIdentify.builder().tags(ArrayUtils.merge("odd"))
        .target(TestBeanB.class)
        .build();
    List<Object> search = identifiedBeanCache.search(Object.class, beanIdentify);
    Assert.assertEquals(2, search.size());
  }

  @Name("testBeanA")
  @Tag("odd")
  @Target(TestBeanB.class)
  private class TestBeanA {

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