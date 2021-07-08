package site.morn.validate;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.morn.data.group.Add;
import site.morn.data.group.Update;
import site.morn.rest.RestModel;
import site.morn.rest.annotation.RestResponse;
import site.morn.test.TestUser;

/**
 * 数据校验测试控制器
 *
 * @author timely-rain
 * @since 1.0.2, 2019/5/12
 */
@RestResponse
@RestController
@RequestMapping("test/validate")
public class TestValidateController {

  /**
   * 新增用户
   *
   * @param restModel REST模型
   */
  @PostMapping
  public void add(@Validated(Add.class) @RequestBody RestModel<TestUser> restModel) {
  }

  /**
   * 更新用户
   *
   * @param restModel REST模型
   */
  @PutMapping
  public void update(@Validated(Update.class) @RequestBody RestModel<TestUser> restModel) {
  }
}
