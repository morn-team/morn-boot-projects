package site.morn.rest;

/**
 * REST数据模型
 *
 * @author TianGanLin
 * @since 0.0.1-SNAPSHOT, 2019/1/14
 */
public interface RestModelDefinition<M, A> {

  /**
   * 获取数据模型
   *
   * @return 数据模型
   */
  M getModel();

  /**
   * 设置数据模型
   *
   * @param model 数据模型
   * @return REST分页请求
   */
  <T extends RestModelDefinition<M, A>> T setModel(M model);

  /**
   * 获取附加数据
   *
   * @return 附加数据
   */
  A getAttach();

  /**
   * 设置附加数据
   *
   * @param attach 附加数据
   * @return REST分页请求
   */
  <T extends RestModelDefinition<M, A>> T setAttach(A attach);
}
