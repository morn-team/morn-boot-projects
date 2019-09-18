package site.morn.boot.support;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.morn.boot.data.CrudControllerSupport;
import site.morn.boot.jpa.TestUser;

/**
 * 用户测试控制器
 *
 * @author timely-rain
 * @since 1.0.0, 2019/5/14
 */
@RequestMapping("test/user")
@RestController
public class TestUserController extends CrudControllerSupport<TestUser, Long, TestUserService> {

}
