package site.morn.boot.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import site.morn.rest.RestMessage;

/**
 * Web异常解释器
 *
 * @author timely-rain
 * @since 1.2.1, 2019/9/19
 */
public interface WebExceptionResolver {

  /**
   * 解释Web异常
   *
   * @param request 请求
   * @param response 响应
   * @param e 异常
   * @return REST消息
   */
  RestMessage resolveException(HttpServletRequest request, HttpServletResponse response,
      Exception e);
}
