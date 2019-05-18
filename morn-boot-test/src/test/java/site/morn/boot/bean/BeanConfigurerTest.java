package site.morn.boot.bean;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.bean.BeanCaches;

/**
 * 实例配置测试
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BeanConfigurerTest {

  @Test
  public void dog() {
    Object dog = BeanCaches.tagBean(Object.class, "animal:dog");
    Assert.assertNotNull(dog);
  }

  @Test
  public void whiteDog() {
    Object whiteDog = BeanCaches.tagBean(Object.class, "animal:dog", "white");
    Assert.assertNull(whiteDog);
  }

  @Test
  public void whiteCat() {
    Object whiteCat = BeanCaches.tagBean(Object.class, "animal:cat", "white");
    Assert.assertNotNull(whiteCat);
  }

  @Test
  public void blackCat() {
    Object blackCat = BeanCaches.tagBean(Object.class, "animal:cat", "black");
    Assert.assertNull(blackCat);
  }

}
