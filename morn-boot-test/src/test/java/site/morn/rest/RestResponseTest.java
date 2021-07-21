package site.morn.rest;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static site.morn.rest.constant.RestMessageConstants.FAILURE;
import static site.morn.rest.constant.RestMessageConstants.SUCCESS;

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
@WithMockUser
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
  public void returnNull() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "null");
    performSuccess(mvc, requestBuilder)
        .andExpect(jsonPath("status").value(SUCCESS));
  }

  @Test
  public void returnData() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "data");
    performSuccess(mvc, requestBuilder)
        .andExpect(jsonPath("status").value(SUCCESS))
        .andExpect(jsonPath("data.username").value("Timely"));
  }

  @Test
  public void returnException() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "ex");
    performFailure(mvc, requestBuilder)
        .andExpect(jsonPath("status").value(FAILURE))
        .andExpect(jsonPath("message").value("This is exception."));
  }

  @Test
  public void returnMissData() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(BASE_URL + "miss/data");
    performSuccess(mvc, requestBuilder)
        .andExpect(jsonPath("status").value(FAILURE))
        .andExpect(jsonPath("code").value("rest.convert-error"));
  }

  @Test
  public void returnMissException() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "miss/ex");
    performFailure(mvc, requestBuilder)
        .andExpect(content().string("This is exception."));
  }

  @Test
  public void returnMornException() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_URL + "morn/ex");
    performFailure(mvc, requestBuilder)
        .andExpect(jsonPath("status").value(FAILURE))
        .andExpect(jsonPath("message").value("Operation failed."));
  }

  @Test
  public void returnBaiduData() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(BASE_URL + "baidu/data");
    performSuccess(mvc, requestBuilder)
        .andExpect(jsonPath("error").value("0"))
        .andExpect(jsonPath("data.username").value("Baidu"));
  }

  @Test
  public void returnBaiduException() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(BASE_URL + "baidu/ex");
    performFailure(mvc, requestBuilder)
        .andExpect(jsonPath("error").value("-1"))
        .andExpect(jsonPath("msg").value("This is Baidu exception."));
  }

  @Test
  public void returnMornData() throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(BASE_URL + "morn/data");
    performSuccess(mvc, requestBuilder)
        .andExpect(jsonPath("status").value(SUCCESS))
        .andExpect(jsonPath("data.username").value("Morn"));
  }

  @Test
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
