package site.morn.boot.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.StringUtils;

/**
 * 分页工具类
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/22
 */
@UtilityClass
public class RestPageUtils {

  /**
   * 升序
   */
  private static final String ASC = "asc";

  /**
   * 降序
   */
  private static final String DESC = "desc";

  /**
   * 判断是否升序
   */
  public static boolean isAscending(String direction) {
    if (StringUtils.isEmpty(direction)) {
      return true;
    }
    return Objects.equals(direction.toLowerCase(), ASC);
  }

  /**
   * 判断是否降序
   */
  public static boolean isDescending(String direction) {
    if (StringUtils.isEmpty(direction)) {
      return false;
    }
    return Objects.equals(direction.toLowerCase(), DESC);
  }


  /**
   * 生成排序信息
   *
   * @param sort 排序字符串, 例如：id desc, date asc
   * @return 排序信息
   */
  public static Sort generateSort(String sort) {
    List<Order> orders = new ArrayList<>();
    String[] pairs = sort.split(",");
    for (String pair : pairs) {
      String[] split = pair.trim().split("\\s+");
      String property = split[0];
      String dir = split.length == 1 ? "" : split[1];
      Direction direction = Direction.fromStringOrNull(dir);
      Order order = new Order(direction, property);
      orders.add(order);
    }
    return new Sort(orders);
  }
}
