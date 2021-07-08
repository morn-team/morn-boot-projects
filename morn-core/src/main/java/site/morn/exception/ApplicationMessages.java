package site.morn.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import site.morn.translate.Transfer;
import site.morn.translate.Translators;

/**
 * 应用消息构建器
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/10
 */
@RequiredArgsConstructor
public class ApplicationMessages {

  /**
   * 默认异常编码
   */
  public static final String FAILURE = "failure";

  /**
   * 翻译载体
   */
  private final Transfer transfer;

  /**
   * 消息内容
   */
  private String message;

  /**
   * 解决方案
   */
  private String solution;

  /**
   * 构建应用消息
   *
   * @param code    消息编码
   * @param message 消息内容
   * @return 应用消息
   * @deprecated {@link #buildMessage(String, String)}
   */
  @Deprecated
  public static ApplicationMessage build(String code, String message) {
    return buildMessage(code, message, null);
  }

  /**
   * 构建应用消息
   *
   * @param code     消息编码
   * @param message  消息内容
   * @param solution 解决方案
   * @return 应用消息
   * @deprecated {@link #buildMessage(String, String, String)}
   */
  @Deprecated
  public static ApplicationMessage build(String code, String message, String solution) {
    return buildMessage(code, message, solution);
  }

  /**
   * 构建应用异常
   *
   * @param message 消息内容
   * @return 应用消息
   */
  public static ApplicationException buildException(String message) {
    return buildMessage(null, message, null).exception();
  }

  /**
   * 构建应用异常
   *
   * @param code    消息编码
   * @param message 消息内容
   * @return 应用消息
   */
  public static ApplicationException buildException(String code, String message) {
    return buildMessage(code, message, null).exception();
  }

  /**
   * 构建应用异常
   *
   * @param code     消息编码
   * @param message  消息内容
   * @param solution 解决方案
   * @return 应用消息
   */
  public static ApplicationException buildException(String code, String message, String solution) {
    return buildMessage(code, message, solution).exception();
  }

  /**
   * 构建应用消息
   *
   * @param message 消息内容
   * @return 应用消息
   */
  public static ApplicationMessage buildMessage(String message) {
    return buildMessage(null, message, null);
  }

  /**
   * 构建应用消息
   *
   * @param code    消息编码
   * @param message 消息内容
   * @return 应用消息
   */
  public static ApplicationMessage buildMessage(String code, String message) {
    return buildMessage(code, message, null);
  }

  /**
   * 构建应用消息
   *
   * @param code     消息编码
   * @param message  消息内容
   * @param solution 解决方案
   * @return 应用消息
   */
  public static ApplicationMessage buildMessage(String code, String message, String solution) {
    return withMessage(code, message).solution(solution).build();
  }

  /**
   * 翻译应用异常
   *
   * @param code 消息编码
   * @param args 消息参数
   */
  public static ApplicationException translateException(String code, Object... args) {
    return translateMessage(code, args).exception();
  }

  /**
   * 翻译应用消息
   *
   * @param code 消息编码
   * @param args 消息参数
   * @return 应用消息
   */
  public static ApplicationMessage translateMessage(String code, Object... args) {
    return withTransfer(code, args).build();
  }

  /**
   * 翻译应用异常，支持缺省值
   *
   * @param code           消息编码
   * @param defaultExpress 默认消息表达式
   * @param args           消息参数
   * @return 应用异常
   */
  public static ApplicationException translateDefaultException(String code, String defaultExpress,
      Object... args) {
    return translateDefaultMessage(code, defaultExpress, args).exception();
  }

  /**
   * 翻译应用消息，支持缺省值
   *
   * @param code           消息编码
   * @param defaultExpress 默认消息表达式
   * @param args           消息参数
   * @return 应用消息
   */
  public static ApplicationMessage translateDefaultMessage(String code, String defaultExpress,
      Object... args) {
    String defaultMessage = String.format(defaultExpress, args);
    return withTransfer(code, args).defaultMessage(defaultMessage).build();
  }

  /**
   * 生成构建器，用于翻译应用消息
   *
   * @param code 消息编码
   * @param args 消息参数
   * @return 应用消息构建器
   */
  public static ApplicationMessages withTransfer(String code, Object... args) {
    String c = StringUtils.isEmpty(code) ? FAILURE : code;
    return builder().code(c).arguments(args);
  }

  /**
   * 生成构建器，用于直接生成应用消息
   *
   * @param code    消息编码
   * @param message 消息内容
   * @return 应用消息构建器
   */
  public static ApplicationMessages withMessage(String code, String message) {
    String c = StringUtils.isEmpty(code) ? FAILURE : code;
    String m = StringUtils.isEmpty(message) ? code : message;
    return builder().code(c).message(m);
  }

  /**
   * 获取应用消息构建器
   *
   * @return 应用消息构建器
   */
  private static ApplicationMessages builder() {
    return new ApplicationMessages(Transfer.builder().build());
  }

  /**
   * 设置消息编码
   *
   * @param code 消息编码
   * @return 应用消息构建器
   */
  public ApplicationMessages code(String code) {
    transfer.setCode(code);
    return this;
  }

  /**
   * 设置消息参数
   *
   * @param args 消息参数
   * @return 应用消息构建器
   */
  private ApplicationMessages arguments(Object... args) {
    transfer.setArgs(args);
    return this;
  }

  /**
   * 设置消息内容
   *
   * @param message 消息内容
   * @return 应用消息构建器
   */
  public ApplicationMessages message(String message) {
    this.message = message;
    return this;
  }

  /**
   * 设置缺省消息内容
   *
   * @param defaultMessage 缺省消息内容
   * @return 应用消息构建器
   */
  public ApplicationMessages defaultMessage(String defaultMessage) {
    transfer.setDefaultMessage(defaultMessage);
    return this;
  }

  /**
   * 设置解决方案
   *
   * @param solution 解决方案
   * @return 应用消息构建器
   */
  public ApplicationMessages solution(String solution) {
    this.solution = solution;
    return this;
  }

  /**
   * 从外部构建应用消息
   *
   * @return 应用消息
   */
  public ApplicationMessage generate() {
    return build();
  }

  /**
   * 构建应用消息
   *
   * @return 应用消息
   */
  private ApplicationMessage build() {
    if (StringUtils.isEmpty(message) && StringUtils.isEmpty(solution)) {
      // 当未设置message/solution时，通过Translator构建应用消息
      ApplicationMessage translate = Translators.defaultTranslator()
          .translate(transfer, ApplicationMessage.class);
      return new ApplicationMessage().setCode(translate.getCode())
          .setMessage(translate.getMessage()).setSolution(translate.getSolution());
    }
    // 当设置了message/solution时，直接构建应用消息
    return new ApplicationMessage().setCode(transfer.getCode()).setMessage(message)
        .setSolution(solution);
  }
}
