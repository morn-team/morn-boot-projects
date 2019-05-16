package site.morn.boot.support;

import org.springframework.stereotype.Service;
import site.morn.boot.jpa.TestUser;
import site.morn.boot.jpa.TestUserRepository;

/**
 * 用户测试服务实现
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/14
 */
@Service
public class TestUserServiceImpl extends
    CrudServiceSupport<TestUser, Long, TestUserRepository> implements CrudService<TestUser, Long> {

}
