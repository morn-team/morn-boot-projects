package site.morn.boot.web;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import site.morn.boot.json.util.JsonParsers;

/**
 * 响应工具类
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/27
 */
@Slf4j
public class Responses {

  /**
   * 响应对象
   */
  private final ServletResponse response;

  private Responses(ServletResponse response) {
    this.response = response;
  }

  /**
   * 构建响应工具对象
   *
   * @param response 响应对象
   * @return 响应工具对象
   */
  public static Responses from(ServletResponse response) {
    return new Responses(Objects.requireNonNull(response));
  }

  /**
   * 设置编码和内容格式为JSON
   */
  public Responses json() {
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    return this;
  }

  /**
   * 应答
   *
   * @param object 报文
   */
  public void respond(Object object) {
    String s = JsonParsers.parseString(object);
    respond(s);
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
