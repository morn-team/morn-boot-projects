package site.morn.boot.data.jpa;

import java.util.Arrays;
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
import site.morn.util.SpareArrayUtils;

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
    Assert.assertEquals("HoneyCake", testCommodity.getName());
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
  public void contains() {
    commodity.setType(1);
    attach.put("keywords", "PorkFloss");
    SpecificationFunction specificationFunction = (reference, restrain, condition) -> {
      // WHERE type = 1
      // id为空，所以忽略
      Predicate[] equals = condition.eqs("id", "type");
      // AND name LIKE '%肉松%'
      Predicate[] contains = condition.contains(SpareArrayUtils.toArray("name"), "keywords");
      restrain.appendAnd(restrain.mergeAnd(equals), restrain.mergeAnd(contains));
    };
    List<TestCommodity> commodities = findAll(specificationFunction);
    Assert.assertEquals(1, commodities.size());
    TestCommodity testCommodity = commodities.get(0);
    Assert.assertNotNull(testCommodity);
    Assert.assertEquals("PorkFlossCake", testCommodity.getName());
  }

  @Test
  public void containNames() {
    commodity.setName("Cake");
    commodity.setIngredients("Pork");
    SpecificationFunction specificationFunction = (reference, restrain, condition) -> {
      // WHERE name LIKE '%蛋糕%' AND ingredients LIKE '%肉%'
      Predicate[] contains = condition.contains("name", "ingredients");
      restrain.appendAnd(contains);
    };
    List<TestCommodity> commodities = findAll(specificationFunction);
    Assert.assertEquals(1, commodities.size());
    TestCommodity testCommodity = commodities.get(0);
    Assert.assertNotNull(testCommodity);
    Assert.assertEquals("PorkFlossCake", testCommodity.getName());
  }

  @Test
  public void startWithes() {
    attach.put("keywords", "Honey");
    // 构建查询条件
    List<TestCommodity> commodities = findAll((reference, restrain, condition) -> {
      // WHERE name LIKE '蜂蜜%'
      Predicate[] startWithes = condition.startWithes(SpareArrayUtils.toArray("name"), "keywords");
      restrain.appendAnd(startWithes);
    });
    Assert.assertEquals(2, commodities.size());
    Assert.assertTrue(commodities.get(0).getName().startsWith("Honey"));
    Assert.assertTrue(commodities.get(1).getName().startsWith("Honey"));
  }

  @Test
  public void startWithNames() {
    commodity.setName("Honey");
    commodity.setIngredients("Hon");
    // 构建查询条件
    List<TestCommodity> commodities = findAll((reference, restrain, condition) -> {
      // WHERE name LIKE '蜂蜜%' AND ingredients LIKE '蜂%'
      Predicate[] startWithes = condition.startWithes("name", "ingredients");
      restrain.appendAnd(startWithes);
    });
    Assert.assertEquals(2, commodities.size());
    Assert.assertTrue(commodities.get(0).getName().startsWith("Honey"));
    Assert.assertTrue(commodities.get(0).getIngredients().startsWith("Hon"));
    Assert.assertTrue(commodities.get(1).getName().startsWith("Honey"));
    Assert.assertTrue(commodities.get(1).getIngredients().startsWith("Hon"));
  }

  @Test
  public void endWithes() {
    attach.put("keywords", "EggTart");
    // 构建查询条件
    List<TestCommodity> commodities = findAll((reference, restrain, condition) -> {
      // WHERE name LIKE '%蛋挞'
      Predicate[] endWithes = condition.endWithes(SpareArrayUtils.toArray("name"), "keywords");
      restrain.appendAnd(endWithes);
    });
    Assert.assertEquals(2, commodities.size());
    Assert.assertEquals(Integer.valueOf(2), commodities.get(0).getType());
    Assert.assertEquals(Integer.valueOf(2), commodities.get(1).getType());
  }

  @Test
  public void endWithNames() {
    commodity.setName("Cake");
    commodity.setIngredients("ney");
    // 构建查询条件
    List<TestCommodity> commodities = findAll((reference, restrain, condition) -> {
      // WHERE name LIKE '%蛋挞'
      Predicate[] endWithes = condition.endWithes("name", "ingredients");
      restrain.appendAnd(endWithes);
    });
    Assert.assertEquals(1, commodities.size());
    Assert.assertTrue(commodities.get(0).getName().endsWith("Cake"));
    Assert.assertTrue(commodities.get(0).getIngredients().endsWith("ney"));
  }

  @Test
  public void inArray() {
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

  @Test
  public void inList() {
    List<Long> ids = Arrays.asList(1L, 2L, 3L);
    attach.put("ids", ids);
    // 构建查询条件
    List<TestCommodity> commodities = findAll((reference, restrain, condition) -> {
      // WHERE id in (1, 2, 3)
      Predicate in = condition.in("id", "ids");
      restrain.appendAnd(in);
    });
    Assert.assertEquals(3, commodities.size());
    for (int i = 0; i < commodities.size(); i++) {
      Assert.assertEquals(ids.get(i), commodities.get(i).getId());
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
        .withParameter(commodity, attach).specification(specificationFunction);
    return repository.findAll(specification);
  }
}