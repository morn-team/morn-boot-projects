package site.morn.boot.jpa;

import java.util.Map;
import site.morn.core.CriteriaMap;

/**
 * site.morn.boot.jpa
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public class JpaMapCondition<T> extends JpaConditionSupport<T, Map<String, Object>> {

  private CriteriaMap model;

  @Override
  public JpaConditionPair withModel(Map<String, Object> model) {
    this.model = new CriteriaMap(model);
    return this;
  }

  @Override
  public Object getValue(String name) {
    return model.get(name);
  }
}
