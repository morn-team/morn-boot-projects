package site.morn.boot.notify;

import static site.morn.constant.ApplicationConstants.TemplateTypes.RESOURCE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.morn.core.CriteriaMap;
import site.morn.notify.annotation.Notify;
import site.morn.notify.annotation.NotifyReceiver;
import site.morn.notify.support.NotifyArgs;
import site.morn.notify.support.NotifyArguments;
import site.morn.template.annotation.Template;

/**
 * 测试通知
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
@Slf4j
@Component
public class TestNotifyBean {

  /**
   * 向角色发送邮箱验证码
   */
  @Notify(type = "email", name = "captcha")
  @NotifyReceiver(type = "role", value = {"r1", "r2"})
  public void captchaToRoleNoTemplate() {
    log.info("Do some thing and Notify to role.");
  }

  /**
   * 向角色发送邮箱验证码
   */
  @Template(type = RESOURCE)
  @Notify(type = "email", name = "captcha")
  @NotifyReceiver(type = "role", value = {"r1", "r2"})
  public void captchaToRole() {
    // 设置模板参数
    CriteriaMap templateArgs = NotifyArguments.getTemplateArgs();
    templateArgs.put("role", "管理员");
    templateArgs.put("captcha", "a1b2c3");
  }

  /**
   * 向用户发送短信验证码
   */
  @Template(type = RESOURCE)
  @Notify(type = "sms", name = "captcha")
  @NotifyReceiver(type = "user")
  public void captchaToUser() {
    // 设置通知参数
    NotifyArgs notifyArgs = NotifyArguments.getNotifyArgs();
    notifyArgs.addReceiverValue("u1");
    notifyArgs.addReceiverValue("u2");
    // 设置模板参数
    CriteriaMap templateArgs = NotifyArguments.getTemplateArgs();
    templateArgs.put("phone", "0041");
    templateArgs.put("captcha", "a3b2c1");
  }
}
