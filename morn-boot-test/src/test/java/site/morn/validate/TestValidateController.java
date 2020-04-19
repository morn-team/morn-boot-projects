package site.morn.validate;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.morn.data.group.Add;
import site.morn.data.group.Update;
import site.morn.rest.RestBuilders;
import site.morn.rest.RestMessage;
import site.morn.rest.RestModel;
import site.morn.test.TestUser;

/**
 * 数据校验测试控制器
 *
 * @author timely-rain
 * @since 1.0.2, 2019/5/12
 */
@RestController
@RequestMapping("test/validate")
public class TestValidateController {

  /**
   * 新增用户
   *
   * @param restModel REST模型
   * @return REST消息
   */
  @PostMapping
  public RestMessage add(@Validated(Add.class) @RequestBody RestModel<TestUser> restModel) {
    return RestBuilders.successMessage();
  }

  /**
   * 更新用户
   *
   * @param restModel REST模型
   * @return REST消息
   */
  @PutMapping
  public RestMessage update(@Validated(Update.class) @RequestBody RestModel<TestUser> restModel) {
    return RestBuilders.successMessage();
  }
}
