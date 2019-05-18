package site.morn.boot.bean;

import org.springframework.context.annotation.Configuration;
import site.morn.bean.BeanAnnotation;
import site.morn.bean.BeanAnnotationRegistry;
import site.morn.bean.BeanConfigurer;
import site.morn.test.TestAnnotationBeans.Animal;

/**
 * 测试实例注解配置
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/18
 */
@Configuration
public class TestBeanAnnotationConfiguration implements BeanConfigurer {

  @Override
  public void addBeanAnnotations(BeanAnnotationRegistry registry) {
    BeanAnnotation beanAnnotation = new BeanAnnotation();
    beanAnnotation.setAnnotationType(Animal.class);
    beanAnnotation.setAttributeName("value");
    beanAnnotation.setTagName("animal");
    registry.add(beanAnnotation);
  }
}
