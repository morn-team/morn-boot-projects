package site.morn.boot.web.log;

import static site.morn.util.AnnotationFeatureUtils.WILDCARD;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import site.morn.bean.annotation.Tag;
import site.morn.boot.web.Requests;
import site.morn.boot.web.Servlets;
import site.morn.core.X;
import site.morn.log.OperateMeta;
import site.morn.log.Operation;
import site.morn.log.OperationAdapter;

/**
 * Web操作适配器
 *
 * @author timely-rain
 * @since 1.2.2, 2021/7/12
 */
@Order
@Tag(WILDCARD)
public class WebOperationAdapter implements OperationAdapter {

  @Override
  public void adaption(OperateMeta meta, Operation operation) {
    // 记录请求和响应对象
    HttpServletRequest request = Servlets.request();
    meta.setRequest(X.from(request));
    HttpServletResponse response = Servlets.response();
    meta.setResponse(X.from(response));
    // 记录请求信息
    Requests requests = Requests.from(request);
    operation.setRequestUrl(requests.getUrl()); // 请求地址
    operation.setRequestMethod(requests.getMethod()); // 请求方法
    operation.setRequestContentType(requests.getContentType()); // 请求内容类型
    operation.setOperatorIp(requests.getIp()); // 访问者IP
    operation.setOperatorAgent(requests.getUserAgent()); // 客户端信息
  }
}
