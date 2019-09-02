package site.morn.boot.log;

import java.util.Map;
import org.springframework.stereotype.Component;
import site.morn.log.OperateAction;
import site.morn.log.OperateArguments;
import site.morn.log.OperateGroup;
import site.morn.rest.RestBuilders;
import site.morn.rest.RestMessage;

/**
 * 测试用户控制器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Component
@OperateGroup(value = "user", args = "1")
public class TestOperateController {

  @OperateAction(value = "add", args = "2")
  public RestMessage addUser(Map<String, Object> user) {
    OperateArguments.add(user.get("username"));
    return RestBuilders.successMessage();
  }

  @OperateAction(value = "update", args = "3")
  public Map<String, Object> updateUser(Map<String, Object> user) {
    OperateArguments.add(user.get("username"));
    OperateArguments.add(user.get("password"));
    throw new RuntimeException("异常测试");
  }
}
