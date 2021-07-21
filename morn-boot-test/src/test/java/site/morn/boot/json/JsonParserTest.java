package site.morn.boot.json;

import static site.morn.boot.json.Constant.USER1;
import static site.morn.boot.json.Constant.USER1_STRING;
import static site.morn.boot.json.Constant.USER2;
import static site.morn.boot.json.Constant.USER2_STRING;
import static site.morn.boot.json.Constant.USERS_STRING;
import static site.morn.boot.json.constant.JsonParserConstants.FAST_JSON;
import static site.morn.boot.json.constant.JsonParserConstants.JACKSON;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.TestContextManager;
import org.springframework.util.CollectionUtils;
import site.morn.boot.json.util.JsonParsers;
import site.morn.core.CriteriaMap;
import site.morn.test.TestUser;

/**
 * JSON解析器单元测试
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/25
 */
@Slf4j
@SpringBootTest
@RequiredArgsConstructor
@RunWith(Parameterized.class)
public class JsonParserTest {

  /**
   * JSON解析器类型
   */
  private final String parserType;

  /**
   * JSON解析器
   */
  private JsonParser parser;

  @Parameters
  public static Iterable<Object[]> testData() {
    return Arrays.asList(new Object[][]{
        {FAST_JSON}, {JACKSON}
    });
  }

  @Before
  public void setUp() throws Exception {
    TestContextManager testContextManager = new TestContextManager(getClass());
    testContextManager.prepareTestInstance(this);
    this.parser = JsonParsers.getParser(parserType);
  }

  @Test
  public void deserializeToMap() {
    CriteriaMap user = parser.parseMap(USER1_STRING);
    Assert.assertNotNull(user);
    Assert.assertEquals(USER1.getId(), user.getLong("id"));
    Assert.assertEquals(USER1.getUsername(), user.getString("username"));
  }

  @Test
  public void deserializeToObject1() {
    TestUser user = parser.parseObject(USER2_STRING, TestUser.class);
    Assert.assertNotNull(user);
    Assert.assertEquals(USER2.getId(), user.getId());
    Assert.assertEquals(USER2.getUsername(), user.getUsername());
  }

  @Test
  public void deserializeToObject2() {
    List<TestUser> users = parser
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
    String userString = parser.parseString(USER1);
    TestUser user1 = parser.parseObject(userString, TestUser.class);
    Assert.assertEquals(Long.valueOf(1), user1.getId());
    Assert.assertEquals("Caramel", user1.getUsername());
  }
}