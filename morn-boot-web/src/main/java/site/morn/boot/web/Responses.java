package site.morn.boot.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

/**
 * 响应工具类
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/27
 */
@Slf4j
@RequiredArgsConstructor
public final class Responses {

  /**
   * 响应对象
   */
  private final ServletResponse response;

  /**
   * 获取标准响应对象
   *
   * @return 标准响应对象
   */
  public static Responses standard() {
    HttpServletResponse response = Servlets.response();
    if (Objects.isNull(response)) {
      return null;
    }
    return standard(response);
  }

  /**
   * 获取标准响应对象
   *
   * @param response 响应对象
   * @return 标准响应对象
   */
  public static Responses standard(ServletResponse response) {
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    return new Responses(response);
  }

  /**
   * 应答
   *
   * @param object 报文
   */
  public void respond(Object object) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String s = objectMapper.writeValueAsString(object);
      respond(s);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    }
  }

  /**
   * 应答
   *
   * @param json 报文
   */
  public void respond(String json) {
    try {
      response.getWriter().write(json);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }
}
