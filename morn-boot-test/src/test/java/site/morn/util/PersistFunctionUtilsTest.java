package site.morn.util;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestContextManager;
import site.morn.test.TestDepartment;

/**
 * 持久化函数单元测试
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/19
 */
@Slf4j
@SpringBootTest
@AllArgsConstructor
@RunWith(Parameterized.class)
public class PersistFunctionUtilsTest {

  /**
   * 测试部门
   */
  private final TestDepartment department;

  /**
   * 预期结果
   */
  private final boolean expected;

  @Before
  public void setUp() throws Exception {
    TestContextManager testContextManager = new TestContextManager(getClass());
    testContextManager.prepareTestInstance(this);
  }

  @Parameters
  public static Iterable<Object[]> testData() {
    return Arrays.asList(new Object[][]{
        {new TestDepartment(1L, -1), false},
        {new TestDepartment(2L, -1), true}
    });
  }

  @Test
  public void handleAdd() {
    // do add department...
    PersistFunctionUtils.handleAdd(department);
    Assert.assertEquals(1, department.getState());
  }

  @Test
  public void handleDelete() {
    // do delete department...
    PersistFunctionUtils.handleDelete(department);
    Assert.assertEquals(0, department.getState());
  }

  @Test
  public void handleUpdate() {
    // do update department...
    PersistFunctionUtils.handleUpdate(department);
    Assert.assertEquals(2, department.getState());
  }

  @Test
  public void validateAdd() {
    boolean valid = PersistFunctionUtils.validateAdd(department);
    // if valid, do add department...
    Assert.assertEquals(expected, valid);
  }

  @Test
  public void validateDelete() {
    boolean valid = PersistFunctionUtils.validateDelete(department);
    // if valid, do delete department...
    Assert.assertEquals(expected, valid);
  }

  @Test
  public void validateUpdate() {
    boolean valid = PersistFunctionUtils.validateUpdate(department);
    // if valid, do update department...
    Assert.assertEquals(expected, valid);
  }
}