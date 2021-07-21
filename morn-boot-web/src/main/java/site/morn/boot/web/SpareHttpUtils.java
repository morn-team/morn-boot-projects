package site.morn.boot.web;


import javax.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

/**
 * 备用Http工具类
 *
 * <p>备用类不推荐在生产项目中使用，生产项目中推荐使用hutool相关工具包</p>
 *
 * @author timely-rain
 * @since 1.2.2, 2021/7/14
 */
@UtilityClass
public class SpareHttpUtils {

  /**
   * 获取客户端IP
   *
   * <p>
   * 默认检测的Header:
   *
   * <pre>
   * 1、X-Forwarded-For
   * 2、X-Real-IP
   * 3、Proxy-Client-IP
   * 4、WL-Proxy-Client-IP
   * </pre>
   *
   * @param request 请求对象{@link HttpServletRequest}
   * @return IP地址
   */
  public static String getClientIP(HttpServletRequest request) {
    String[] headers = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP",
        "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};
    String ip;
    for (String header : headers) {
      ip = request.getHeader(header);
      if (!isUnknown(ip)) {
        return getMultistageReverseProxyIp(ip);
      }
    }
    ip = request.getRemoteAddr();
    return getMultistageReverseProxyIp(ip);
  }

  /**
   * 检测给定字符串是否为未知，多用于检测HTTP请求相关<br>
   *
   * @param checkString 被检测的字符串
   * @return 是否未知
   */
  public static boolean isUnknown(String checkString) {
    return StringUtils.isEmpty(checkString) || "unknown".equalsIgnoreCase(checkString);
  }

  /**
   * 从多级反向代理中获得第一个非unknown IP地址
   *
   * @param ip 获得的IP地址
   * @return 第一个非unknown IP地址
   */
  public static String getMultistageReverseProxyIp(String ip) {
    String s = ip;
    if (s != null && s.contains(",")) {
      final String[] ips = s.trim().split(",");
      for (String subIp : ips) {
        if (isUnknown(subIp)) {
          s = subIp;
          break;
        }
      }
    }
    return s;
  }
}
