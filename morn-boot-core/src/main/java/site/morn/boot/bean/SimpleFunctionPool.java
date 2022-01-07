package site.morn.boot.bean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import site.morn.bean.AnnotationFeature;
import site.morn.bean.BeanCache;
import site.morn.bean.BeanHolder;
import site.morn.bean.BeanPool;
import site.morn.bean.FunctionHolder;
import site.morn.bean.FunctionPool;
import site.morn.util.AnnotationFeatureUtils;

/**
 * 默认函数池
 * <p>
 * 从{@link BeanCache}获取特定函数对象，提供简洁的检索API
 *
 * @author timely-rain
 * @since 1.2.2, 2021/9/27
 */
@RequiredArgsConstructor
public class SimpleFunctionPool implements FunctionPool {

  /**
   * 实例池
   */
  private final BeanPool beanPool;

  @Override
  public List<FunctionHolder> functions(AnnotationFeature beanFeature,
      AnnotationFeature functionFeature) {
    return functionHolderStream(beanFeature, functionFeature).collect(Collectors.toList());
  }

  @Override
  public <T> List<FunctionHolder> functions(List<BeanHolder<T>> holders,
      AnnotationFeature functionFeature) {
    return functionHolderStream(holders.stream(), functionFeature).collect(Collectors.toList());
  }

  /**
   * 获取函数持有流
   *
   * @param beanFeature     实例特征
   * @param functionFeature 函数特征
   * @return 函数持有流
   */
  private Stream<FunctionHolder> functionHolderStream(AnnotationFeature beanFeature,
      AnnotationFeature functionFeature) {
    return functionHolderStream(beanPool.beanHolderStream(null, beanFeature), functionFeature);
  }

  /**
   * 获取函数持有流
   *
   * @param stream  实例持有者流
   * @param feature 函数特征
   * @return 函数持有流
   */
  private <T> Stream<FunctionHolder> functionHolderStream(Stream<BeanHolder<T>> stream,
      AnnotationFeature feature) {
    return stream.flatMap(holder -> holder.getFunctionHolders().stream())
        .filter(holder -> AnnotationFeatureUtils.isSuitable(holder, feature));
  }
}
