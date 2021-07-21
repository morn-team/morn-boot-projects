package site.morn.boot.web;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Servlet工具类
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/27
 */
public class Servlets {

  private Servlets() {
  }

  /**
   * 获取会话
   *
   * @return 会话
   */
  public static HttpSession session() {
    HttpServletRequest request = request();
    return Objects.isNull(request) ? null : request.getSession();
  }

  /**
   * 获取请求对象
   *
   * @return 请求对象
   */
  public static HttpServletRequest request() {
    ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes());
    if (Objects.isNull(attributes)) {
      return null;
    }
    return attributes.getRequest();
  }

  /**
   * 获取请求工具类
   */
  public static Requests requests() {
    return Requests.from(request());
  }

  /**
   * 获取响应对象
   *
   * @return 响应对象
   */
  public static HttpServletResponse response() {
    ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes());
    if (Objects.isNull(attributes)) {
      return null;
    }
    return attributes.getResponse();
  }

  /**
   * 获取响应工具类
   */
  public static Responses responses() {
    return Responses.from(response());
  }
}
