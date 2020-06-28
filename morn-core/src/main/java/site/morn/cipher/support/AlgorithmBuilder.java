package site.morn.cipher.support;

import site.morn.cipher.Algorithm;

public class AlgorithmBuilder {

  /**
   * 算法名称
   */
  private String name;

  /**
   * 计算模式
   */
  private String mode;

  /**
   * 渲染风格
   */
  private String style;

  private AlgorithmBuilder() {
  }

  public AlgorithmBuilder name(String name) {
    this.name = name;
    return this;
  }

  public AlgorithmBuilder mode(String mode) {
    this.mode = mode;
    return this;
  }

  public AlgorithmBuilder style(String style) {
    this.style = style;
    return this;
  }

  public Algorithm build() {
    return new SimpleAlgorithm(name, mode, style);
  }

  public String toString() {
    return "Algorithm.AlgorithmBuilder(name=" + this.name + ", mode=" + this.mode + ", style="
        + this.style + ")";
  }

  public static AlgorithmBuilder withName(String name) {
    return new AlgorithmBuilder().name(name);
  }
}
