package site.morn.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Optional;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import site.morn.bean.annotation.Function;
import site.morn.bean.annotation.Name;
import site.morn.bean.annotation.Source;
import site.morn.bean.annotation.Tag;
import site.morn.bean.annotation.Target;

/**
 * 实例注解测试类
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/18
 */
@Slf4j
@Component
public class TestAnnotationBeans {

  @Component
  @Animal("dog")
  @Color("yellow")
  @Tag("big")
  @Target(Toy.class)
  public static class Dog implements Mammal {

    @Function
    public String eat(String food) {
      log.info("狗在吃{}...", food);
      return "bone";
    }

    @Function
    public void play() {
      log.info("{}在玩...", "狗");
    }
  }

  @Component
  @Animal("cat")
  @Name("caramel")
  @Tag("small")
  @Target(Food.class)
  public static class RagDoll implements Cat {

    @Function
    @Tag("meat")
    public String eat(String meat, Food food) {
      log.info("{}在吃{}和{}...", "caramel", meat,
          Optional.ofNullable(food).map(Food::getName).orElse("未知实物"));
      return "fishBone";
    }

    @Function
    @Source(Cat.class)
    public void play() {
      log.info("{}在玩...", "caramel");
    }
  }

  @Component
  @Animal("cat")
  @Name("mocha")
  @Tag("small")
  @Target(Toy.class)
  public static class BritishShortHair implements Cat {

    @Function
    public void eat(String food) {
      log.info("{}在吃{}...", "mocha", food);
    }

    @Function
    @Source(Cat.class)
    public void play() {
      log.info("{}在玩...", "mocha");
    }
  }

  @Component
  @Animal
  @Tag("small")
  @Target(Food.class)
  public static class Rabbit implements Mammal {

    @Function
    public void eat(String food) {
      log.info("{}在吃{}...", "蜗牛", food);
    }
  }

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

  public interface Mammal {

  }

  public interface Cat extends Mammal {

  }

  @Data
  public static class Food {

    private final String name;
  }

  public static class Toy {

  }
}
