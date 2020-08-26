package site.morn.boot.data.jpa;

import org.springframework.stereotype.Repository;
import site.morn.boot.data.jpa.crud.SpecificationRepository;
import site.morn.test.TestCommodity;

/**
 * 测试商品访问对象
 *
 * @author timely-rain
 * @since 1.2.2, 2020/8/20
 */
@Repository
public interface TestCommodityRepository extends SpecificationRepository<TestCommodity, Long> {

}
