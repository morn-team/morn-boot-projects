package site.morn.validate;

import javax.validation.constraints.NotNull;
import lombok.Data;
import site.morn.validate.group.Add;

/**
 * 测试实体
 *
 * @author timely-rain
 * @since 1.0.2, 2019/5/12
 */
@Data
public class TestUser {

  /**
   * 用户名
   */
  @NotNull
  private String username;

  /**
   * 密码
   */
  @NotNull(groups = Add.class)
  private String password;
}
