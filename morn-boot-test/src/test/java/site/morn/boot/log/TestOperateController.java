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
@OperateGroup("user")
public class TestOperateController {

  @OperateAction("add")
  public RestMessage addUser(Map<String, Object> user) {
    OperateArguments.add(user.get("username"));
    return RestBuilders.successMessage();
  }

  @OperateAction("update")
  public Map<String, Object> updateUser(Map<String, Object> user) {
    OperateArguments.add(user.get("username"));
    OperateArguments.add(user.get("password"));
    throw new RuntimeException("异常测试");
  }
}
