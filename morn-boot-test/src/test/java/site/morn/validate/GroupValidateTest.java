package site.morn.validate;

import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import site.morn.core.CriteriaMap;

/**
 * 分组校验单元测试
 *
 * @author timely-rain
 * @since 1.0.2, 2019/5/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GroupValidateTest {

  /**
   * 测试URL
   */
  private static final String BASE_URL = "/test/validate";

  @Autowired
  private MockMvc mvc;

  private CriteriaMap noModel;

  private CriteriaMap noPassword;

  @Before
  public void setUp() throws Exception {
    noModel = new CriteriaMap();

    noPassword = new CriteriaMap();
    TestUser testUser = new TestUser();
    testUser.setUsername("Timely");
    noPassword.put("model", testUser);
  }

  @Test
  public void add1() throws Exception {
    String content = JSONObject.toJSONString(noModel);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
        .content(content).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mvc.perform(requestBuilder)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void add2() throws Exception {
    String content = JSONObject.toJSONString(noPassword);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
        .content(content).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mvc.perform(requestBuilder)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void update() throws Exception {
    String content = JSONObject.toJSONString(noPassword);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(BASE_URL)
        .content(content).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mvc.perform(requestBuilder)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
