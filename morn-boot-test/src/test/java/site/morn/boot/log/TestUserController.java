package site.morn.boot.log;

import java.util.Map;
import org.springframework.stereotype.Component;
import site.morn.log.OperateAction;
import site.morn.log.OperateArguments;
import site.morn.log.OperateGroup;

/**
 * 测试用户控制器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Component
@OperateGroup("user")
class TestUserController {

  @OperateAction("add")
  Map<String, Object> addUser(Map<String, Object> user) {
    OperateArguments.add(user.get("username"));
    return user;
  }

  @OperateAction("update")
  Map<String, Object> updateUser(Map<String, Object> user) {
    OperateArguments.add(user.get("username"));
    OperateArguments.add(user.get("password"));
    throw new RuntimeException("异常测试");
  }
}
