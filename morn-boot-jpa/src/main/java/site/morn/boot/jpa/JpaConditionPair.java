package site.morn.boot.jpa;

import java.util.Map;
import java.util.Optional;
import site.morn.util.OptionalCollection;

/**
 * site.morn.boot.jpa
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public interface JpaConditionPair<M> {

  JpaConditionPair withModel(M model);

  JpaConditionPair withAttach(Map<String, Object> attach);

  JpaConditionPair withNamePair(String pathName, String model);

  <V> JpaConditionPair withValuePair(String pathName, V value);

  <V> Optional<V> getOptional(String name);

  <V> OptionalCollection<V> getCollectionOptional(String name);
}
