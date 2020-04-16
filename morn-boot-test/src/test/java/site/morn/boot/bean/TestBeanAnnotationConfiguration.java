package site.morn.boot.bean;

import org.springframework.context.annotation.Configuration;
import site.morn.bean.AnnotationField;
import site.morn.bean.AnnotationFieldRegistry;
import site.morn.bean.BeanConfigurer;
import site.morn.test.TestAnnotationBeans.Animal;
import site.morn.test.TestAnnotationBeans.Color;

/**
 * 测试实例注解配置
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/18
 */
@Configuration
public class TestBeanAnnotationConfiguration implements BeanConfigurer {

  @Override
  public void addBeanAnnotations(AnnotationFieldRegistry registry) {
    AnnotationField animal = new AnnotationField(Animal.class);
    animal.setValueName("value");
    animal.setAnnotationName("animal");
    registry.add(animal);

    registry.add(Color.class);
  }
}
