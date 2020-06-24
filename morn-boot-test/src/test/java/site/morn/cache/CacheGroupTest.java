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
    cacheGroup.put("iconUrl", "1", 1);
    cacheGroup.put("iconUrl", "2", 2);
    cacheGroup.put("iconUrl", "3", 3);

    Object name1 = cacheGroup.get("name", "1");
    Object name2 = cacheGroup.get("name", "2");
    Object name3 = cacheGroup.get("name", "3");
    Object iconUrl1 = cacheGroup.get("iconUrl", "1");
    Object iconUrl2 = cacheGroup.get("iconUrl", "2");
    Object iconUrl3 = cacheGroup.get("iconUrl", "3");

    Assert.assertNotNull(name1);
    Assert.assertNotNull(name2);
    Assert.assertNotNull(name3);
    Assert.assertNotNull(iconUrl1);
    Assert.assertNotNull(iconUrl2);
    Assert.assertNotNull(iconUrl3);
  }

  @Test
  public void bGet() {
    String name1 = cacheGroup.get("name", "1", String.class);
    Assert.assertEquals("Timely", name1);

    Integer iconUrl1 = cacheGroup.get("iconUrl", "1", Integer.class);
    Assert.assertNotNull(iconUrl1);
    Assert.assertEquals(1, iconUrl1.intValue());
  }

  @Test
  public void cPut() {
    cacheGroup.putIfAbsent("name", "1", "Timely Again");
    String name1 = cacheGroup.get("name", "1", String.class);
    Assert.assertEquals("Timely", name1);

    cacheGroup.put("iconUrl", "2", 200);
    Integer iconUrl2 = cacheGroup.get("iconUrl", "2", Integer.class);
    Assert.assertNotNull(iconUrl2);
    Assert.assertEquals(200, iconUrl2.intValue());
  }

  @Test
  public void dClear() {
    cacheGroup.clear("name", "1");
    Object name1 = cacheGroup.get("name", "1");
    Assert.assertNull(name1);

    cacheGroup.clearKey("2");
    Object name2 = cacheGroup.get("name", "2");
    Object iconUrl2 = cacheGroup.get("iconUrl", "2");
    Assert.assertNull(name2);
    Assert.assertNull(iconUrl2);

    cacheGroup.clearGroup("iconUrl");
    Object iconUrl1 = cacheGroup.get("iconUrl", "1");
    Object iconUrl3 = cacheGroup.get("iconUrl", "3");
    Assert.assertNull(iconUrl1);
    Assert.assertNull(iconUrl3);

    cacheGroup.clearAll();
    Object name3 = cacheGroup.get("name", "3");
    Assert.assertNull(name3);
  }
}