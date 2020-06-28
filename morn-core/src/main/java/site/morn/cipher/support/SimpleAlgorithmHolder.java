package site.morn.cipher.support;

import site.morn.cipher.Algorithm;
import site.morn.cipher.AlgorithmHolder;

/**
 * 算法持有者实现
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/29
 */
public class SimpleAlgorithmHolder implements AlgorithmHolder {

  /**
   * 算法
   */
  protected Algorithm algorithm;

  public Algorithm algorithm() {
    return algorithm;
  }

  @Override
  public void setAlgorithm(Algorithm algorithm) {
    this.algorithm = algorithm;
  }
}
