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
   * 访问者ip
   */
  private String ip;

  /**
   * 访问url
   */
  private String url;

  /**
   * 操作系统
   */
  private String system;

  /**
   * 操作模块
   */
  private String module;

  /**
   * 操作名称
   */
  private String name;

  /**
   * 操作描述
   */
  private String description;

  /**
   * 操作内容
   */
  private String content;

  /**
   * 操作人
   */
  private String operator;

  /**
   * 操作时间
   */
  private Date date;
}
