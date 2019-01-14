package site.morn.boot.jpa;

/**
 * site.morn.boot.jpa
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public interface JpaConditionPair<D> {

  JpaConditionPair withModel(D model);

  JpaConditionPair withNamePair(String pathName, String model);

  <V> JpaConditionPair withValuePair(String pathName, V value);
}
