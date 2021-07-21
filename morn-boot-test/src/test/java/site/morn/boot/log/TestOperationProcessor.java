package site.morn.boot.log;

import static site.morn.boot.log.OperateAspectTest.DONE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Objective;
import site.morn.boot.web.Servlets;
import site.morn.log.OperateMeta;
import site.morn.log.Operation;
import site.morn.log.OperationProcessor;

/**
 * 测试操作处理器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/5
 */
@Slf4j
@Component
@Objective
public class TestOperationProcessor implements OperationProcessor {

  @Override
  public void handle(OperateMeta operateMeta, Operation operation) {
    log.info("Operate|元数据：{}", operateMeta.toString());
    log.info("Operate|操作日志：{}", operation.toString());
    HttpSession session = Servlets.session();
    HttpServletRequest request = Servlets.request();
    HttpServletResponse response = Servlets.response();
    log.info("Operate|Session：{}", session);
    log.info("Operate|Request：{}", request);
    log.info("Operate|Response：{}", response);
    DONE.set(true);
  }
}

