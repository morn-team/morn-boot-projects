package site.morn.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Tag;

/**
 * 实例注解测试类
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/18
 */
@Component
public class TestAnnotationBeans {

  /**
   * 动物注解
   */
  @Component
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  public @interface Animal {

    String value() default "";
  }

  @Animal("dog")
  public class Dog {

  }

  @Animal("cat")
  @Tag("white")
  public class Cat {

  }
}
