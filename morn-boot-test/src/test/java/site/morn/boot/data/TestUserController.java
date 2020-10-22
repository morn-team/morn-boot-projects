package site.morn.boot.data;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.morn.test.TestUser;

/**
 * 用户测试控制器
 *
 * @author timely-rain
 * @since 1.0.0, 2019/5/14
 */
@RestController
@RequestMapping("test/user")
public class TestUserController extends DisplayableController<TestUser, Long, TestUserService> {

}
