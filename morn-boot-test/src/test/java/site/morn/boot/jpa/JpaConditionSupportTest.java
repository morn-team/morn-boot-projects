package site.morn.boot.jpa;

import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import site.morn.boot.jpa.SpecificationBuilder.SpecificationFunction;
import site.morn.core.CriteriaMap;

/**
 * @author timely-rain
 * @since 1.0.0, 2019/4/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaConditionSupportTest {

  @Autowired
  private TestUserRepository repository;

  private TestUser user;

  private CriteriaMap attach;

  @Before
  public void setUp() {
    user = new TestUser();
    user.setId(1L);
    user.setUsername("timely-rain");
    user.setPassword(null);

    attach = new CriteriaMap();
    attach.put("keywords", "timely");
  }

  @Test
  public void equals() {
  }

  @Test
  public void contains() {
    contains(user, attach);
  }

  @Test
  public void contains1() {
  }

  @Test
  public void startWithes() {
  }

  @Test
  public void endWithes() {
  }

  @Test
  public void ins() {
    // 构建查询条件
    SpecificationFunction specificationFunction = (reference, restrain, condition) -> {

      Root root = reference.root();
      CriteriaBuilder builder = reference.builder();

      // WHERE id = 1
      if (Objects.nonNull(user.getId())) {
        Predicate id = builder.equal(root.get("id"), user.getId());
        restrain.appendAnd(id);
      }

      // AND username = 'timely-rain'
      if (!StringUtils.isEmpty(user.getUsername())) {
        Predicate username = builder.equal(root.get("username"), user.getUsername());
        restrain.appendAnd(username);
      }
    };
    Specification<TestUser> specification = SpecificationBuilder.withParameter(user)
        .specification(specificationFunction);
    repository.findAll(specification);
  }

  @Test
  public void equalAll() {
    equalAll(user);
  }

  private List<TestUser> equalAll(TestUser user) {
    // 构建查询条件
    SpecificationFunction specificationFunction = (reference, restrain, condition) -> {

      // WHERE id = 1 AND username = 'timely-rain'
      // password为空，所以忽略
      Predicate[] equalAll = condition.equalAll();

      restrain.appendAnd(equalAll);
    };
    Specification<TestUser> specification = SpecificationBuilder.withParameter(user)
        .specification(specificationFunction);
    return repository.findAll(specification);
  }

  private List<TestUser> contains(TestUser user, CriteriaMap attach) {
    SpecificationFunction specificationFunction = (reference, restrain, condition) -> {

      // WHERE id = 1
      // password为空，所以忽略
      Predicate[] equals = condition.eqs("id", "password");

      // AND username LIKE '%timely%'
      Predicate keywords = condition.contain("username", "keywords");

      restrain.appendAnd(restrain.mergeAnd(equals), keywords);
    };
    Specification<TestUser> specification = SpecificationBuilder.withParameter(user, attach)
        .specification(specificationFunction);
    return repository.findAll(specification);
  }
}