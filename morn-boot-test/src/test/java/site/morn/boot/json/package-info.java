/**
 * JSON单元测试
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/25
 */
package site.morn.boot.json;

import java.util.ArrayList;
import java.util.List;
import site.morn.test.TestUser;

/**
 * JSON测试常量类
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/25
 */
class Constant {

  /**
   * 用户集合
   */
  protected static final List<TestUser> USERS = new ArrayList<>();

  //language=JSON
  protected static final String USERS_STRING = "[\n"
      + "  {\n"
      + "    \"id\": 1,\n"
      + "    \"username\": \"Caramel\"\n"
      + "  },\n"
      + "  {\n"
      + "    \"id\": 2,\n"
      + "    \"username\": \"Mocha\"\n"
      + "  }\n"
      + "]";

  /**
   * 用户1
   */
  protected static final TestUser USER1 = new TestUser(1L, "Caramel");

  //language=JSON
  protected static final String USER1_STRING = "{\n"
      + "  \"id\": 1,\n"
      + "  \"username\": \"Caramel\"\n"
      + "}";

  /**
   * 用户2
   */
  protected static final TestUser USER2 = new TestUser(2L, "Mocha");

  //language=JSON
  protected static final String USER2_STRING = "{\n"
      + "  \"id\": 2,\n"
      + "  \"username\": \"Mocha\"\n"
      + "}";

  static {
    USERS.add(USER1);
    USERS.add(USER2);
  }
}