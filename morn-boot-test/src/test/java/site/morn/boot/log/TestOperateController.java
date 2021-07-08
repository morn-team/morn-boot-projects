package site.morn.boot.log;

import java.util.Map;
import org.springframework.stereotype.Component;
import site.morn.boot.rest.RestBuilders;
import site.morn.log.OperateAction;
import site.morn.log.OperateArguments;
import site.morn.log.OperateGroup;
import site.morn.rest.RestMessage;

/**
 * 测试用户控制器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Component
@OperateGroup(value = "用户管理", args = "1")
public class TestOperateController {

  @OperateAction(value = "新增用户：{0}", args = "2")
  public RestMessage addUser(Map<String, Object> user) {
    OperateArguments.add(user.get("username"));
    return RestBuilders.successMessage();
  }

  @OperateAction(value = "更新用户：{1}，年龄：{0}", args = "3")
  public Map<String, Object> updateUser(Map<String, Object> user) {
    OperateArguments.add(user.get("age"));
    OperateArguments.add(user.get("username"));
    throw new RuntimeException("异常测试");
  }
}
