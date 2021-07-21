package site.morn.boot.rest;

import static site.morn.rest.constant.RestMessageConstants.FAILURE;
import static site.morn.rest.constant.RestMessageConstants.SUCCESS;

import site.morn.rest.RestMessage;
import site.morn.rest.constant.RestMessageLevel;

/**
 * REST构建器工具类
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/17
 */
public class RestBuilders {

  private RestBuilders() {
  }

  /**
   * 获取默认构建器
   *
   * @return 默认构建器
   */
  private static RestBuilder builder() {
    return RestBuilder.builder();
  }

  /**
   * 获取信息构建器
   *
   * <p>success:true, level:info
   *
   * @return 信息构建器
   */
  public static RestBuilder infoBuilder() {
    return builder().status(SUCCESS).level(RestMessageLevel.INFO);
  }

  /**
   * 获取错误构建器
   *
   * <p>success:true, level:error
   *
   * @return 错误构建器
   */
  public static RestBuilder errorBuilder() {
    return builder().status(FAILURE).level(RestMessageLevel.ERROR);
  }

  /**
   * 获取成功消息构建器
   *
   * <p>success:true, level:info, code:rest.success
   *
   * @return 成功消息构建器
   */
  public static RestBuilder successBuilder() {
    String successCode = RestBuilder.properties().getSuccessCode();
    return successBuilder(successCode).transfer(successCode);
  }

  /**
   * 获取成功消息构建器
   *
   * @param code 国际化编号
   * @return 成功消息构建器
   */
  public static RestBuilder successBuilder(String code) {
    return infoBuilder().code(code).transfer(code);
  }

  /**
   * 获取成功消息构建器
   *
   * @param code 国际化编号
   * @param args 国际化参数
   * @return 成功消息构建器
   */
  public static RestBuilder successBuilder(String code, Object... args) {
    return infoBuilder().code(code).transfer(code, args);
  }

  /**
   * 获取成功消息构建器
   *
   * @param data 数据
   * @return 成功消息构建器
   */
  public static RestBuilder successBuilder(Object data) {
    return successBuilder().data(data);
  }

  /**
   * 获取失败消息构建器
   *
   * @return 失败消息构建器
   */
  public static RestBuilder failureBuilder() {
    String failureCode = RestBuilder.properties().getFailureCode();
    return failureBuilder(failureCode);
  }

  /**
   * 获取失败消息构建器
   *
   * @param code 国际化编号
   * @param args 国际化参数
   * @return 失败消息构建器
   */
  public static RestBuilder failureBuilder(String code, Object... args) {
    return errorBuilder().code(code).transfer(code, args);
  }

  /**
   * 构建成功消息
   *
   * @return 成功消息
   */
  public static RestMessage successMessage() {
    return successBuilder().build();
  }

  /**
   * 构建成功消息
   *
   * @param code 国际化编号
   * @return 成功消息
   */
  public static RestMessage successMessage(String code) {
    return successBuilder(code).build();
  }

  /**
   * 构建成功消息
   *
   * @param code 国际化编号
   * @param args 国际化参数
   * @return 成功消息
   */
  public static RestMessage successMessage(String code, Object... args) {
    return successBuilder(code, args).build();
  }

  /**
   * 构建成功消息
   *
   * @return 成功消息
   */
  public static RestMessage successMessage(Object data) {
    return successBuilder(data).build();
  }

  /**
   * 构建失败消息
   *
   * @return 成功消息
   */
  public static RestMessage failureMessage() {
    return failureBuilder().build();
  }

  /**
   * 构建失败消息
   *
   * @param code 国际化编号
   * @param args 国际化参数
   * @return 成功消息
   */
  public static RestMessage failureMessage(String code, Object... args) {
    return failureBuilder(code, args).build();
  }

}
