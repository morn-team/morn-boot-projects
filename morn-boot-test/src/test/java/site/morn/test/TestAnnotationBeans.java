package site.morn.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Function;
import site.morn.bean.annotation.Name;
import site.morn.bean.annotation.Tag;
import site.morn.bean.annotation.Target;

/**
 * 实例注解测试类
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/18
 */
@Slf4j
@Component
public class TestAnnotationBeans {

  /**
   * 动物注解
   */
  @Retention(RetentionPolicy.RUNTIME)
  @java.lang.annotation.Target(ElementType.TYPE)
  public @interface Animal {

    String value() default "";
  }

  /**
   * 颜色注解
   */
  @Retention(RetentionPolicy.RUNTIME)
  @java.lang.annotation.Target(ElementType.TYPE)
  public @interface Color {

    String value() default "";
  }

  public interface Cat {

    void eat();

    void play();
  }

  public class Food {

  }

  public class Toy {

  }

  @Component
  @Animal("dog")
  @Color("yellow")
  @Tag("big")
  @Target(Toy.class)
  public class Dog {

    @Function
    public void eat() {
      log.info("狗在吃东西...");
    }
  }

  @Component
  @Animal("cat")
  @Name("caramel")
  @Tag("small")
  @Target(Food.class)
  public class Caramel implements Cat {

    @Function
    @Override
    public void eat() {
      log.info("{}在吃东西...", "caramel");
    }

    @Function
    @Override
    public void play() {
      log.info("{}在玩...", "caramel");
    }
  }

  @Component
  @Animal("cat")
  @Name("mocha")
  @Tag("small")
  @Target(Toy.class)
  public class Mocha implements Cat {

    @Function
    @Override
    public void eat() {
      log.info("{}在吃东西...", "mocha");
    }

    @Function
    @Override
    public void play() {
      log.info("{}在玩...", "mocha");
    }
  }

  @Component
  @Animal
  @Tag("small")
  @Target(Food.class)
  public class Rabbit {

  }
}
