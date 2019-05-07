package site.morn.boot.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * 测试用户访问对象
 *
 * @author timely-rain
 * @since 1.0.0, 2019/4/22
 */
public interface TestUserRepository extends CrudRepository<TestUser, Long>,
    JpaSpecificationExecutor<TestUser> {

}
