package site.morn.boot.bean;


import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class SimpleIdentifiedBeanCacheTest {

  /**
   * 标识实例缓存
   */
  @Autowired
  private IdentifiedBeanCache identifiedBeanCache;

  @Before
  public void setUp() throws Exception {
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

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void register() {
    Assert.assertNotNull(identifiedBeanCache);
  }

  @Test
  public void searchByName() {
    // 测试Name
    List<Object> name = identifiedBeanCache.searchByName(Object.class, "testBeanA");
    Assert.assertEquals(name.size(), 1);
  }

  @Test
  public void searchByTags() {
    // 测试Tags
    List<Object> odd = identifiedBeanCache.searchByTags(Object.class, "odd");
    Assert.assertEquals(odd.size(), 2);
  }

  @Test
  public void searchByTarget() {
    // 测试Target
    List<Object> target = identifiedBeanCache.searchByTarget(Object.class, TestBeanB.class);
    Assert.assertEquals(target.size(), 2);
  }

  @Test
  public void search() {
    // 测试BeanIdentify
    BeanIdentify beanIdentify = BeanIdentify.builder().tags(ArrayUtils.merge("odd"))
        .target(TestBeanB.class)
        .build();
    List<Object> search = identifiedBeanCache.search(Object.class, beanIdentify);
    Assert.assertEquals(search.size(), 2);
  }

  @Name("testBeanA")
  @Tag("odd")
  @Target(TestBeanB.class)
  public class TestBeanA {

  }

  @Name("testBeanB")
  @Tag("even")
  public class TestBeanB {

  }

  @Name("testBeanC")
  @Tag("odd")
  @Target(TestBeanB.class)
  public class TestBeanC {

  }
}