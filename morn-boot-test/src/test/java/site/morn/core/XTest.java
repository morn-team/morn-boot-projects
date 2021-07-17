package site.morn.core;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import site.morn.test.TestUser;

/**
 * 任意类型单元测试
 */
@RunWith(JUnit4.class)
public class XTest {

  @Test
  public void asCollection() {
    ArrayList<String> list = new ArrayList<>();
    list.add("foo");
    list.add("bar");
    X x = X.from(list);
    Collection<String> objects = x.asCollection();
    Assert.assertEquals("Test x, cast success.", list, objects);
    Assert.assertTrue("Test x, asCollection success.", objects.containsAll(list));
  }

  @Test
  public void asLong() {
    X x = X.from(1L);
    Long longNumber = x.asLong();
    Assert.assertEquals(Long.valueOf(1L), longNumber);
  }

  @Test
  public void asString() {
    X x = X.from("foo");
    String s = x.asString();
    Assert.assertEquals("foo", s);
  }

  @Test
  public void value() {
    TestUser testUser = new TestUser();
    testUser.setUsername("timely-rain");
    X x = X.from(testUser);
    TestUser value = x.value(TestUser.class);
    Assert.assertEquals(testUser, value);
    Assert.assertEquals("timely-rain", value.getUsername());
  }
}