package site.morn.boot.data;

import org.springframework.stereotype.Repository;
import site.morn.boot.data.jpa.crud.SpecificationRepository;
import site.morn.test.TestUser;

/**
 * 测试用户访问对象
 *
 * @author timely-rain
 * @since 1.0.0, 2019/4/22
 */
@Repository
public interface TestUserRepository extends SpecificationRepository<TestUser, Long> {

}
