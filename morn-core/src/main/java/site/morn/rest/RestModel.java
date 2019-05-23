package site.morn.rest;

import javax.validation.Valid;
import site.morn.core.CriteriaMap;
import site.morn.util.TypeUtils;

/**
 * REST数据模型
 *
 * @author TianGanLin
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
public class RestModel<M> implements RestModelDefinition<M, CriteriaMap> {


  /**
   * 数据模型
   */
  @Valid
  private M model;

  /**
   * 附加数据
   */
  private CriteriaMap attach;

  public RestModel() {
    this.attach = new CriteriaMap();
  }

  @Override
  public M getModel() {
    return model;
  }

  @Override
  public <T extends RestModelDefinition<M, CriteriaMap>> T setModel(M model) {
    this.model = model;
    return TypeUtils.as(this);
  }

  @Override
  public CriteriaMap getAttach() {
    return attach;
  }

  @Override
  public <T extends RestModelDefinition<M, CriteriaMap>> T setAttach(CriteriaMap attach) {
    this.attach = attach;
    return TypeUtils.as(this);
  }
}
