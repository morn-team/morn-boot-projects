package site.morn.rest;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static site.morn.rest.constant.RestMessageStatus.FAILURE;
import static site.morn.rest.constant.RestMessageStatus.SUCCESS;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * REST响应测试
 *
 * @author timely-rain
 * @since 1.2.2, 2020/7/26
 */
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class RestResponseTest {

  /**
   * 测试URL
   */
  private static final String BASE_URL = "/test/rest/";

  @Autowired
  private MockMvc mvc;

  @Test
  @WithMockUser
  public void returnData() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "data");
    performSuccess(mvc, requestBuilder)
        .andExpect(jsonPath("status").value(SUCCESS))
        .andExpect(jsonPath("data.username").value("Timely"));
  }

  @Test
  @WithMockUser
  public void returnException() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "ex");
    performFailure(mvc, requestBuilder)
        .andExpect(jsonPath("status").value(FAILURE))
        .andExpect(jsonPath("message").value("This is exception."));
  }

  @Test
  @WithMockUser
  public void returnBaiduData() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(BASE_URL + "baidu/data");
    performSuccess(mvc, requestBuilder)
        .andExpect(jsonPath("error").value("0"))
        .andExpect(jsonPath("data.username").value("Baidu"));
  }

  @Test
  @WithMockUser
  public void returnBaiduException() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(BASE_URL + "baidu/ex");
    performFailure(mvc, requestBuilder)
        .andExpect(jsonPath("error").value("-1"))
        .andExpect(jsonPath("msg").value("This is Baidu exception."));
  }

  @Test
  @WithMockUser
  public void returnMornData() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(BASE_URL + "morn/data");
    performSuccess(mvc, requestBuilder)
        .andExpect(jsonPath("status").value(SUCCESS))
        .andExpect(jsonPath("data.username").value("Morn"));
  }

  @Test
  @WithMockUser
  public void returnResolvableException() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(BASE_URL + "resolvable/ex");
    performSuccess(mvc, requestBuilder)
        .andExpect(jsonPath("status").value(FAILURE))
        .andExpect(jsonPath("message").value("username must not be null"));
  }

  /**
   * 发送并验证正确结果
   */
  private ResultActions performSuccess(MockMvc mvc, RequestBuilder requestBuilder)
      throws Exception {
    return mvc.perform(requestBuilder)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  /**
   * 发送并验证异常结果
   */
  private ResultActions performFailure(MockMvc mvc, RequestBuilder requestBuilder)
      throws Exception {
    return mvc.perform(requestBuilder)
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk());
  }
}
