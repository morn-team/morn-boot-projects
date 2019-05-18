package site.morn.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 注解标识工具类
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/18
 */
@Slf4j
@UtilityClass
public class AnnotationIdentifyUtils {

  /**
   * 间隔符
   */
  public static final String SPACER = ":";

  /**
   * 通配符
   */
  public static final String WILDCARD = "*";

  /**
   * 判断是否适用
   *
   * @param suits 适用条件
   * @param limits 限制条件
   * @return 是否适用
   */
  public static boolean isSuitable(String[] suits, String[] limits) {
    for (String limit : limits) {
      if (isSuitable(suits, limit)) {
        continue;
      }
      return false;
    }
    return true;
  }

  /**
   * 判断是否适用
   *
   * @param suits 适用条件
   * @param limit 限制条件
   * @return 是否适用
   */
  private static boolean isSuitable(String[] suits, String limit) {
    for (String suit : suits) {
      if (isSuitable(suit, limit)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 判断是否适用
   *
   * @param suit 适用条件
   * @param limit 限制条件
   * @return 是否适用
   */
  public static boolean isSuitable(String suit, String limit) {
    if (StringUtils.isEmpty(limit)) { // 无限制条件
      return true;
    }
    if (StringUtils.isEmpty(suit)) { // 无适用条件
      return false;
    }
    if (isWildcard(suit, limit)) { // 通配成功
      return true;
    }
    if (!suit.contains(SPACER)) { // 无间隔符
      return false;
    }
    // 自定义Tag格式为 foo:bar
    Pattern suitCompile = Pattern.compile("(\\S+):(\\S+)");
    Matcher suitMatcher = suitCompile.matcher(suit);
    Pattern limitCompile = Pattern.compile("(\\S+):(\\S+)");
    Matcher limitMatcher = limitCompile.matcher(limit);
    if (!suitMatcher.find()) {
      log.warn("非法适用条件：{}", suit);
      return false;
    }
    if (!limitMatcher.find()) {
      return suit.startsWith(limit) || suit.startsWith(WILDCARD + SPACER);
    }
    return (isWildcard(suitMatcher.group(1), limitMatcher.group(1)))
        && isWildcard(suitMatcher.group(2), limitMatcher.group(2));
  }

  /**
   * 判断是否通配
   *
   * @param suit 适用条件
   * @param limit 限制条件
   * @return 是否通配
   */
  private static boolean isWildcard(String suit, String limit) {
    if (Objects.equals(suit, limit)) { // 完全匹配限制条件
      return true;
    }
    return Objects.equals(suit, WILDCARD); // 适用任何条件
  }
}
