package site.morn.boot.jpa;

import org.springframework.stereotype.Repository;
import site.morn.boot.support.JpaRepository;

/**
 * 测试用户访问对象
 *
 * @author timely-rain
 * @since 1.0.0, 2019/4/22
 */
@Repository
public interface TestUserRepository extends JpaRepository<TestUser, Long> {

}
