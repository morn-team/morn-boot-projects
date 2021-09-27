package site.morn.boot.data;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static site.morn.rest.constant.RestMessageConstants.SUCCESS;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import site.morn.boot.json.util.JsonParsers;

/**
 * 显示/隐藏控制器单元测试
 *
 * @author timely-rain
 * @since 1.2.2, 2020/8/26
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class DisplayableControllerTest {

  /**
   * 测试URL
   */
  private static final String BASE_URL = "/test/user";

  private static final Long[] IDS = {1L};

  @Autowired
  private MockMvc mvc;

  @Test
  @WithMockUser
  public void display() throws Exception {
    String content = JsonParsers.parseString(IDS);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(BASE_URL + "/display")
        .content(content).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value(SUCCESS));
  }

  @Test
  @WithMockUser
  public void hide() throws Exception {
    String content = JsonParsers.parseString(IDS);
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(BASE_URL + "/hide")
        .content(content).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mvc.perform(requestBuilder)
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("status").value(SUCCESS));
  }
}