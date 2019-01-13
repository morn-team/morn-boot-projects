package site.morn.boot.jpa;

/**
 * site.morn.boot.jpa
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public class JpaConditionBuilder {

  public static <T, M> JpaModelCondition<T, M> modelCondition() {
    return new JpaModelCondition<>();
  }
}
