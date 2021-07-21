package site.morn.notify.support;

import static site.morn.constant.ApplicationConstants.BUILTIN;
import static site.morn.constant.ApplicationConstants.Notifies.BUILTIN_NOTIFY_KEY;

import java.util.Objects;
import site.morn.core.CriteriaMap;
import site.morn.notify.annotation.Notify;

/**
 * 系统通知线程变量
 *
 * @author timely-rain
 * @since 1.2.1, 2020/6/11
 */
public class NotifyArguments {

  /**
   * 系统通知线程变量
   * <p>当key为：</p>
   * <pre>
   * “_builtin” 内建参数
   * "ntf.type.name" 通知参数
   * "tpl.type.name" 模板参数
   * </pre>
   */
  private static final ThreadLocal<CriteriaMap> LOCAL = ThreadLocal.withInitial(CriteriaMap::new);

  /**
   * 模板参数前缀
   */
  public static final String TPL = "tpl.";

  /**
   * 通知参数前缀
   */
  public static final String NTF = "ntf.";

  private NotifyArguments() {
  }

  /**
   * 清空线程变量
   */
  public static void clear() {
    getLocalMap().clear();
    LOCAL.remove();
  }

  /**
   * 获取内建参数集
   *
   * @return 内建参数集
   */
  public static CriteriaMap getBuiltinArgs() {
    return getArgs(BUILTIN);
  }

  /**
   * 获取通知参数集
   * <p>该方法只适用于当前线程中仅存在一条通知{@link Notify}的情况</p>
   *
   * @return 通知参数集
   */
  public static NotifyArgs getNotifyArgs() {
    String key = NTF + getBuiltinArgs().getString(BUILTIN_NOTIFY_KEY);
    return getNotifyArgs(key);
  }

  /**
   * 获取通知参数集
   *
   * @param notifyType 通知类型
   * @param notifyName 通知名称
   * @return 通知参数集
   */
  public static NotifyArgs getNotifyArgs(String notifyType, String notifyName) {
    String key = NTF + notifyType + "." + notifyName;
    return getNotifyArgs(key);
  }

  /**
   * 获取模板参数集
   *
   * @return 模板参数集
   */
  public static CriteriaMap getTemplateArgs() {
    String key = TPL + getBuiltinArgs().getString(BUILTIN_NOTIFY_KEY);
    return getArgs(key);
  }

  /**
   * 获取模板参数集
   *
   * @param notifyType 通知类型
   * @param notifyName 通知名称
   * @return 模板参数集
   */
  public static CriteriaMap getTemplateArgs(String notifyType, String notifyName) {
    String key = TPL + notifyType + "." + notifyName;
    return getArgs(key);
  }

  /**
   * 获取线程变量集
   *
   * @param key 键
   * @return 线程变量集
   */
  private static CriteriaMap getArgs(String key) {
    CriteriaMap localMap = getLocalMap();
    CriteriaMap arguments = localMap.getCriteriaMap(key);
    if (Objects.isNull(arguments)) {
      arguments = new CriteriaMap();
      localMap.put(key, arguments);
    }
    return arguments;
  }

  /**
   * 获取通知参数集
   *
   * @param key 键
   * @return 通知参数集
   */
  private static NotifyArgs getNotifyArgs(String key) {
    CriteriaMap localMap = getLocalMap();
    NotifyArgs arguments = localMap.getExpect(key);
    if (Objects.isNull(arguments)) {
      arguments = new NotifyArgs();
      localMap.put(key, arguments);
    }
    return arguments;
  }

  /**
   * 获取当前线程变量
   *
   * @return 当前线程变量
   */
  private static CriteriaMap getLocalMap() {
    return LOCAL.get();
  }
}
