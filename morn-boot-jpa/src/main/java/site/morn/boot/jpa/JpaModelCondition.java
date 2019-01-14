package site.morn.boot.jpa;

/**
 * site.morn.boot.jpa
 *
 * @author timely-rain
 * @since 1.0.0, 2019/1/13
 */
public class JpaModelCondition<T, M> extends JpaConditionSupport<T, M> {

  private M model;

  @Override
  public JpaConditionPair withModel(M model) {
    this.model = model;
    return this;
  }

  @Override
  public Object getValue(String name) {
    return null;
  }
}
