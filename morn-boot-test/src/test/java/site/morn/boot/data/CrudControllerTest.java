package site.morn.boot.data;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static site.morn.rest.constant.RestMessageConstants.SUCCESS;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import site.morn.core.CriteriaMap;
import site.morn.test.TestUser;

/**
 * 业务支持测试
 *
 * @author timely-rain
 * @since 1.0.0, 2019/5/14
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrudControllerTest {

  /**
   * 测试URL
   */
  private static final String BASE_URL = "/test/user";

  @Autowired
  private MockMvc mvc;

  /**
   * 测试用户
   */
  private TestUser testUser;

  @Before
  public void setUp() {
    testUser = new TestUser();
    testUser.setId(2L);
    testUser.setUsername("timely");
    testUser.setPassword("123456");
  }

  /**
   * 新增测试
   */
  @Test
  @WithMockUser
  public void test1() throws Exception {
    CriteriaMap restModel = new CriteriaMap().set("model", testUser);
    String content = JSONObject.toJSONString(restModel);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL)
        .content(content).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    MvcResult mvcResult = mvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value(SUCCESS))
        .andExpect(jsonPath("data.username").value("timely"))
        .andReturn();
    MockHttpServletResponse response = mvcResult.getResponse();
    String string = response.getContentAsString();
    log.info("新增测试用户：{}", string);
  }

  /**
   * 更新测试
   */
  @Test
  @WithMockUser
  public void test2() throws Exception {
    testUser.setUsername("ct-mika");
    CriteriaMap restModel = new CriteriaMap().set("model", testUser);
    String content = JSONObject.toJSONString(restModel);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(BASE_URL)
        .content(content).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value(SUCCESS))
        .andExpect(jsonPath("data.username").value("ct-mika"));
  }

  /**
   * 搜索测试
   */
  @Test
  @WithMockUser
  public void test3() throws Exception {
    testUser.setUsername("ct-mika");
    CriteriaMap restModel = new CriteriaMap().set("model", testUser);
    String content = JSONObject.toJSONString(restModel);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_URL + "/search")
        .content(content).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value(SUCCESS));
  }

  /**
   * 获取测试
   */
  @Test
  @WithMockUser
  public void test4() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "/2");
    mvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value(SUCCESS))
        .andExpect(jsonPath("data.id").value(2));
  }


  /**
   * 删除测试
   */
  @Test
  @WithMockUser
  public void test5() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(BASE_URL + "/2")
        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value(SUCCESS));
  }
}