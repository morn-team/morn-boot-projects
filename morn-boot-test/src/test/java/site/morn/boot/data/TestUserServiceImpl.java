package site.morn.boot.data;

import org.springframework.stereotype.Service;
import site.morn.boot.data.TestUserRepository;
import site.morn.boot.data.jpa.crud.impl.SimpleCrudService;
import site.morn.boot.data.TestUserService;
import site.morn.test.TestUser;

/**
 * 用户测试服务实现
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/14
 */
@Service
public class TestUserServiceImpl extends
    SimpleCrudService<TestUser, Long, TestUserRepository> implements TestUserService {

}
