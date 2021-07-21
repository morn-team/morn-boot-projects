package site.morn.log;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 操作实例
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/4
 */
@Getter
@Setter
@ToString
public class Operation {

  /**
   * 操作成功标识
   */
  private boolean success;

  /**
   * 所属系统
   */
  private String system;

  /**
   * 所属模块
   */
  private String module;

  /**
   * 操作名称
   */
  private String name;

  /**
   * 操作内容
   */
  private String content;

  /**
   * 操作时间
   */
  private Date date;

  /**
   * 消耗时间
   */
  private int duration;

  /**
   * 请求地址
   */
  private String requestUrl;

  /**
   * 请求方法
   */
  private String requestMethod;

  /**
   * 请求内容类型
   */
  private String requestContentType;

  /**
   * 操作人唯一标识
   */
  private String operatorName;

  /**
   * 操作人IP
   */
  private String operatorIp;

  /**
   * 操作人客户端
   */
  private String operatorAgent;
}
