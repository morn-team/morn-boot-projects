package site.morn.boot.web;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

/**
 * 请求工具类
 *
 * @author timely-rain
 * @since 1.2.2, 2021/7/12
 */
public class Requests {

  /**
   * 原生请求对象
   */
  private final HttpServletRequest request;

  private Requests(HttpServletRequest request) {
    this.request = request;
  }

  /**
   * 构建Requests对象
   *
   * @param request 请求对象
   * @return 请求工具类
   */
  public static Requests from(HttpServletRequest request) {
    return new Requests(Objects.requireNonNull(request));
  }

  /**
   * 获取请求路径
   */
  public String getUrl() {
    return request.getRequestURI();
  }

  /**
   * 获取请求方法
   */
  public String getMethod() {
    return request.getMethod();
  }

  /**
   * 获取内容类型
   */
  public String getContentType() {
    return request.getContentType();
  }

  /**
   * 获取请求IP
   */
  public String getIp() {
    return SpareHttpUtils.getClientIP(request);
  }

  /**
   * 获取客户端信息
   */
  public String getUserAgent() {
    return request.getHeader(HttpHeaders.USER_AGENT);
  }
}
