package site.morn.boot.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

/**
 * 响应工具类
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/27
 */
@Slf4j
public final class Responses {

  private Responses() {
  }

  public static HttpServletResponse standard() {
    HttpServletResponse response = Servlets.response();
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    return response;
  }

  public static void write(Object o) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      String s = objectMapper.writeValueAsString(o);
      write(s);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    }
  }

  public static void write(String json) {
    try {
      standard().getWriter().write(json);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }
}
