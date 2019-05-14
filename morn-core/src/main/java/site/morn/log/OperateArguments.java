package site.morn.log;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作日志参数
 *
 * @author timely-rain
 * @since 1.0.0, 2018/12/5
 */
public class OperateArguments {

  /**
   * 操作日志线程变量
   */
  private static final ThreadLocal<List<Object>> arguments = ThreadLocal
      .withInitial(ArrayList::new);

  private OperateArguments() {
  }

  /**
   * 新增线程变量
   *
   * @param value 变量值
   * @param <T> 变量类型
   */
  public static <T> void add(T value) {
    List<Object> objects = arguments.get();
    objects.add(value);
  }

  /**
   * 移除线程变量
   *
   * @param value 变量值
   * @param <T> 变量类型
   * @return 是否成功
   */
  public static <T> boolean remove(T value) {
    return arguments.get().remove(value);
  }

  /**
   * 清空线程变量
   */
  public static void clear() {
    arguments.get().clear();
  }

  /**
   * 获取全部线程变量
   *
   * @return 线程变量集合
   */
  public static List<Object> getAll() {
    return arguments.get();
  }

  /**
   * 移除线程变量
   *
   * @param index 索引
   * @return 变量值
   */
  public static Object remove(int index) {
    return arguments.get().remove(index);
  }
}
