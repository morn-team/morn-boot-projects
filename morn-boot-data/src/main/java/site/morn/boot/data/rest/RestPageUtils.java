package site.morn.boot.data.rest;

import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.StringUtils;
import site.morn.core.CriteriaAttributes;
import site.morn.rest.RestPageDefinition;
import site.morn.rest.RestPageableDefinition;

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
    return ASC.equalsIgnoreCase(direction);
  }

  /**
   * 判断是否降序
   */
  public static boolean isDescending(String direction) {
    if (StringUtils.isEmpty(direction)) {
      return false;
    }
    return DESC.equalsIgnoreCase(direction);
  }

  /**
   * 生成JPA分页请求
   *
   * @return JPA分页请求
   */
  public static <P extends RestPageableDefinition, M, A extends CriteriaAttributes> PageRequest generatePageRequest(
      RestPageDefinition<P, M, A> restPage) {
    P pageable = restPage.getPageable();
    return generatePageRequest(pageable);
  }

  /**
   * 生成JPA分页请求
   *
   * @return JPA分页请求
   */
  public static PageRequest generatePageRequest(RestPageableDefinition pageable) {
    String sortString = pageable.getSort();
    if (StringUtils.isEmpty(sortString)) {
      return PageRequest.of(pageable.getPage(), pageable.getSize());
    }
    Sort sort = generateSort(sortString);
    return PageRequest.of(pageable.getPage(), pageable.getSize(), sort);
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
      Direction direction = Direction.fromOptionalString(dir).orElse(Direction.DESC);
      Order order = new Order(direction, property);
      orders.add(order);
    }
    return Sort.by(orders);
  }
}
