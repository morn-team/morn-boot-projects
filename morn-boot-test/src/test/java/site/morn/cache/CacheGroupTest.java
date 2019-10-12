package site.morn.cache;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 缓存组单元测试
 *
 * @author timely-rain
 * @since 1.2.1, 2019/9/5
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CacheGroupTest {

  private CacheGroup cacheGroup;

  @Before
  public void setUp() throws Exception {
    this.cacheGroup = CacheGroups.cacheGroup("test");
  }

  @Test
  public void aPut() {
    cacheGroup.put("name", "1", "Timely");
    cacheGroup.put("name", "2", "Rain");
    cacheGroup.put("name", "3", "Milk");
    cacheGroup.put("number", "1", 1);
    cacheGroup.put("number", "2", 2);
    cacheGroup.put("number", "3", 3);

    Object name1 = cacheGroup.get("name", "1");
    Object name2 = cacheGroup.get("name", "2");
    Object name3 = cacheGroup.get("name", "3");
    Object number1 = cacheGroup.get("number", "1");
    Object number2 = cacheGroup.get("number", "2");
    Object number3 = cacheGroup.get("number", "3");

    Assert.assertNotNull(name1);
    Assert.assertNotNull(name2);
    Assert.assertNotNull(name3);
    Assert.assertNotNull(number1);
    Assert.assertNotNull(number2);
    Assert.assertNotNull(number3);
  }

  @Test
  public void bGet() {
    String name1 = cacheGroup.get("name", "1", String.class);
    Assert.assertEquals("Timely", name1);

    Integer number1 = cacheGroup.get("number", "1", Integer.class);
    Assert.assertNotNull(number1);
    int value = number1.intValue();
    Assert.assertEquals(1, value);
  }

  @Test
  public void cPut() {
    cacheGroup.putIfAbsent("name", "1", "Timely Again");
    String name1 = cacheGroup.get("name", "1", String.class);
    Assert.assertEquals("Timely", name1);

    cacheGroup.put("number", "2", 200);
    Integer number2 = cacheGroup.get("number", "2", Integer.class);
    Assert.assertNotNull(number2);
    int value = number2.intValue();
    Assert.assertEquals(200, value);
  }

  @Test
  public void dClear() {
    cacheGroup.clear("name", "1");
    Object name1 = cacheGroup.get("name", "1");
    Assert.assertNull(name1);

    cacheGroup.clearKey("2");
    Object name2 = cacheGroup.get("name", "2");
    Object number2 = cacheGroup.get("number", "2");
    Assert.assertNull(name2);
    Assert.assertNull(number2);

    cacheGroup.clearGroup("number");
    Object number1 = cacheGroup.get("number", "1");
    Object number3 = cacheGroup.get("number", "3");
    Assert.assertNull(number1);
    Assert.assertNull(number3);

    cacheGroup.clearAll();
    Object name3 = cacheGroup.get("name", "3");
    Assert.assertNull(name3);
  }
}