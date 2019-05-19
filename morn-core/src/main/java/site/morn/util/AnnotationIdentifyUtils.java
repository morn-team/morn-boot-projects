package site.morn.util;

import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import site.morn.bean.AnnotationIdentify;

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
   * 获取标签
   *
   * @param annotation 注解类
   * @return 标签
   */
  public static String getTag(Class<? extends Annotation> annotation) {
    return getTag(annotation, "");
  }

  /**
   * 获取标签
   *
   * @param annotation 注解类
   * @param value 标签值
   * @return 标签
   */
  public static String getTag(Class<? extends Annotation> annotation, Object value) {
    Assert.notNull(annotation, "注解类不能为空");
    String tagName = StringUtils.uncapitalize(annotation.getSimpleName());
    return getTag(tagName, value);
  }

  /**
   * 获取标签
   *
   * @param tagName 标签名称
   * @param value 标签值
   * @return 标签
   */
  public static String getTag(String tagName, Object value) {
    String s = Optional.ofNullable(value).orElse("").toString();
    return String.format("%s%s%s", tagName, SPACER, s);
  }

  /**
   * 判断是否适用
   *
   * @param suit 适用条件
   * @param limit 限制条件
   * @return 是否适用
   */
  public static <T extends AnnotationIdentify> boolean isSuitable(T suit, T limit) {
    Boolean rule = baseRule(suit, limit);
    if (Objects.nonNull(rule)) {
      return rule;
    }
    boolean name = isSuitable(suit.getName(), limit.getName()); // 按名称通配
    boolean tags = isSuitable(suit.getTags(), limit.getTags()); // 按标签通配
    boolean target = isSuper(suit.getTarget(), limit.getTarget()); // 按目标通配
    return name && tags && target;
  }

  /**
   * 判断是否为子类
   *
   * @param suit 适用条件
   * @param limit 限制条件
   * @return 是否适用
   */
  public static boolean isInstance(Class<?> suit, Class<?> limit) {
    Boolean rule = baseRule(suit, limit);
    if (Objects.nonNull(rule)) {
      return rule;
    }
    return limit.isAssignableFrom(suit);
  }

  /**
   * 判断是否为超类
   *
   * @param suit 适用条件
   * @param limit 限制条件
   * @return 是否适用
   */
  public static boolean isSuper(Class<?> suit, Class<?> limit) {
    Boolean rule = baseRule(suit, limit);
    if (Objects.nonNull(rule)) {
      return rule;
    }
    return suit.isAssignableFrom(limit);
  }

  /**
   * 判断是否适用
   *
   * @param suits 适用条件
   * @param limits 限制条件
   * @return 是否适用
   */
  public static boolean isSuitable(String[] suits, String[] limits) {
    Boolean rule = baseRule(suits, limits);
    if (Objects.nonNull(rule)) {
      return rule;
    }
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
    Boolean rule = baseRule(suit, limit);
    if (Objects.nonNull(rule)) {
      return rule;
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
   * 基本规则
   *
   * @param suit 适用条件
   * @param limit 限制条件
   * @return 是否适用
   */
  private Boolean baseRule(Object suit, Object limit) {
    if (Objects.isNull(limit)) { // 无限制条件
      return true;
    }
    if (Objects.isNull(suit)) { // 无适用条件
      return false;
    }
    return null;
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
