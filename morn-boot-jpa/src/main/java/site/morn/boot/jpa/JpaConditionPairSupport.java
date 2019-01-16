package site.morn.boot.jpa;

import java.util.Map;
import java.util.Optional;
import site.morn.util.OptionalCollection;

/**
 * site.morn.boot.jpa
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/14
 */
public class JpaConditionPairSupport<M> implements JpaConditionPair<M> {

  @Override
  public JpaConditionPair model(M model) {
    return null;
  }

  @Override
  public JpaConditionPair attach(Map<String, Object> attach) {
    return null;
  }

  @Override
  public JpaConditionPair withNamePair(String pathName, String model) {
    return null;
  }

  @Override
  public <V> JpaConditionPair withValuePair(String pathName, V value) {
    return null;
  }

  @Override
  public <V> Optional<V> getOptional(String name) {
    return Optional.empty();
  }

  @Override
  public <V> OptionalCollection<V> getCollectionOptional(String name) {
    return null;
  }

//  private <V> getValue(String name){
//
//  }
}
