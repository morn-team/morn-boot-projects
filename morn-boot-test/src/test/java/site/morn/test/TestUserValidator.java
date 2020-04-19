package site.morn.test;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Source;
import site.morn.data.persistent.DeleteValidator;

/**
 * 测试用户验证器
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/19
 */
@Slf4j
@Component
public class TestUserValidator {

  private final static TestUser USER = new TestUser();

  static {
    USER.setDepartmentId(1L);
  }

  /**
   * 部门删除验证器
   */
  @Component
  @Source(TestDepartment.class)
  public static class DepartmentDeleteValidator implements DeleteValidator<TestDepartment> {

    @Override
    public boolean validate(TestDepartment source) {
      // dependency UserService for getting user by departmentId, here is a mock
      boolean isEquals = Objects.equals(source.getId(), USER.getDepartmentId());
      if (isEquals) {
        // u can also throw an ApplicationException to rollback the transaction
        log.warn("无法删除部门，仍有用户关联。");
      }
      return !isEquals;
    }
  }
}
