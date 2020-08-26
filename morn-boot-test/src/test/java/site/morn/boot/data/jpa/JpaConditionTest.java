package site.morn.boot.data.jpa;

import java.util.List;
import javax.persistence.criteria.Predicate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.boot.data.jpa.support.SpecificationBuilder;
import site.morn.core.CriteriaMap;
import site.morn.test.TestCommodity;

/**
 * JPA构建条件单元测试
 *
 * @author timely-rain
 * @since 1.0.0, 2019/4/22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JpaConditionTest {

  @Autowired
  private TestCommodityRepository repository;

  private TestCommodity commodity;

  private CriteriaMap attach;

  @Before
  public void setUp() throws Exception {
    commodity = new TestCommodity();
    attach = new CriteriaMap();
  }

  @Test
  public void contains() {
    commodity.setType(1);
    attach.put("keywords", "肉松");
    SpecificationFunction specificationFunction = (reference, restrain, condition) -> {
      // WHERE type = 1
      // id为空，所以忽略
      Predicate[] equals = condition.eqs("id", "type");
      // AND name LIKE '%肉松%'
      Predicate keywords = condition.contain("name", "keywords");
      restrain.appendAnd(restrain.mergeAnd(equals), keywords);
    };
    List<TestCommodity> commodities = findAll(specificationFunction);
    Assert.assertEquals(1, commodities.size());
    TestCommodity testCommodity = commodities.get(0);
    Assert.assertNotNull(testCommodity);
    Assert.assertEquals("肉松蛋糕", testCommodity.getName());
  }

  @Test
  public void equalAll() {
    commodity.setType(1);
    attach.put("price", 5f);
    // 构建查询条件
    List<TestCommodity> commodities = findAll((reference, restrain, condition) -> {
      // WHERE type = 1 AND price = 5
      Predicate[] equalAll = condition.equalAll();
      restrain.appendAnd(equalAll);
    });
    Assert.assertEquals(1, commodities.size());
    TestCommodity testCommodity = commodities.get(0);
    Assert.assertNotNull(testCommodity);
    Assert.assertEquals("蜂蜜蛋糕", testCommodity.getName());
  }

  @Test
  public void equalAllExcludes() {
    commodity.setType(1);
    attach.put("price", 5f);
    // 构建查询条件
    List<TestCommodity> commodities = findAll((reference, restrain, condition) -> {
      // WHERE type = 1
      // 排除price
      Predicate[] predicates = condition.equalAllExcludes("price");
      restrain.appendAnd(predicates);
    });
    Assert.assertEquals(2, commodities.size());
    Assert.assertEquals(Integer.valueOf(1), commodities.get(0).getType());
    Assert.assertEquals(Integer.valueOf(1), commodities.get(1).getType());
  }

  @Test
  public void startWithes() {
    attach.put("keywords", "蜂蜜");
    // 构建查询条件
    List<TestCommodity> commodities = findAll((reference, restrain, condition) -> {
      // WHERE name LIKE '蜂蜜%'
      Predicate startWith = condition.startWith("name", "keywords");
      restrain.appendAnd(startWith);
    });
    Assert.assertEquals(2, commodities.size());
    Assert.assertTrue(commodities.get(0).getName().startsWith("蜂蜜"));
    Assert.assertTrue(commodities.get(1).getName().startsWith("蜂蜜"));
  }

  @Test
  public void endWithes() {
    attach.put("keywords", "蛋挞");
    // 构建查询条件
    List<TestCommodity> commodities = findAll((reference, restrain, condition) -> {
      // WHERE name LIKE '%蛋挞'
      Predicate endWith = condition.endWith("name", "keywords");
      restrain.appendAnd(endWith);
    });
    Assert.assertEquals(2, commodities.size());
    Assert.assertEquals(Integer.valueOf(2), commodities.get(0).getType());
    Assert.assertEquals(Integer.valueOf(2), commodities.get(1).getType());
  }

  @Test
  public void ins() {
    Long[] ids = {1L, 2L, 3L};
    attach.put("ids", ids);
    // 构建查询条件
    List<TestCommodity> commodities = findAll((reference, restrain, condition) -> {
      // WHERE id in (1, 2, 3)
      Predicate in = condition.in("id", "ids");
      restrain.appendAnd(in);
    });
    Assert.assertEquals(3, commodities.size());
    for (int i = 0; i < commodities.size(); i++) {
      Assert.assertEquals(ids[i], commodities.get(i).getId());
    }
  }

  /**
   * 全部查询
   *
   * @param specificationFunction 条件构建器
   * @return 测试用户集合
   */
  private List<TestCommodity> findAll(SpecificationFunction specificationFunction) {
    Specification<TestCommodity> specification = SpecificationBuilder
        .withParameter(commodity, attach)
        .specification(specificationFunction);
    return repository.findAll(specification);
  }
}