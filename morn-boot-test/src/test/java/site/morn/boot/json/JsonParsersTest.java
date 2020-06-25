package site.morn.boot.json;

import static site.morn.boot.json.Constant.USER1;
import static site.morn.boot.json.Constant.USER1_STRING;
import static site.morn.boot.json.Constant.USER2;
import static site.morn.boot.json.Constant.USER2_STRING;
import static site.morn.boot.json.Constant.USERS_STRING;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;
import site.morn.boot.json.util.JsonParsers;
import site.morn.core.CriteriaMap;
import site.morn.test.TestUser;

/**
 * JSON解析工具类单元测试
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/25
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class JsonParsersTest {

  @Test
  public void deserializeToMap() {
    CriteriaMap user = JsonParsers.parseMap(USER1_STRING);
    Assert.assertNotNull(user);
    Assert.assertEquals(USER1.getId(), user.getLong("id"));
    Assert.assertEquals(USER1.getUsername(), user.getString("username"));
  }

  @Test
  public void deserializeToObject1() {
    TestUser user = JsonParsers.parseObject(USER2_STRING, TestUser.class);
    Assert.assertNotNull(user);
    Assert.assertEquals(USER2.getId(), user.getId());
    Assert.assertEquals(USER2.getUsername(), user.getUsername());
  }

  @Test
  public void deserializeToObject2() {
    List<TestUser> users = JsonParsers
        .parseObject(USERS_STRING, new ParameterizedTypeReference<List<TestUser>>() {
        });
    Assert.assertFalse(CollectionUtils.isEmpty(users));
    TestUser user1 = users.get(0);
    Assert.assertEquals(Long.valueOf(1), user1.getId());
    Assert.assertEquals("Caramel", user1.getUsername());
    TestUser user2 = users.get(1);
    Assert.assertEquals(Long.valueOf(2), user2.getId());
    Assert.assertEquals("Mocha", user2.getUsername());
  }

  @Test
  public void serializeToString() {
    String userString = JsonParsers.parseString(USER1);
    TestUser user1 = JsonParsers.parseObject(userString, TestUser.class);
    Assert.assertEquals(Long.valueOf(1), user1.getId());
    Assert.assertEquals("Caramel", user1.getUsername());
  }
}