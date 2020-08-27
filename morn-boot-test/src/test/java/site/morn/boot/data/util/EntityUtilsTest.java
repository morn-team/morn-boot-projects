package site.morn.boot.data.util;

import java.lang.reflect.Field;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import site.morn.test.TestUser;

/**
 * 实体工具类单元测试
 *
 * @author timely-rain
 * @since 1.2.2, 2020/8/26
 */
@RunWith(JUnit4.class)
public class EntityUtilsTest {

  @Test
  public void getId() {
    TestTeacher teacher = new TestTeacher();
    teacher.setId(3L);
    Long id = EntityUtils.getId(teacher);
    Assert.assertEquals(Long.valueOf(3L), id);
  }


  @Test
  public void getIdNull() {
    Field idField = EntityUtils.getIdField(new Object());
    Assert.assertNull(idField);
  }

  private static class TestTeacher extends TestUser {

  }
}