package site.morn.bean;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.bean.support.AnnotationFeatureBuilder;
import site.morn.bean.support.BeanCaches;
import site.morn.bean.support.Tags;
import site.morn.test.TestAnnotationBeans.Animal;
import site.morn.test.TestAnnotationBeans.Cat;
import site.morn.test.TestAnnotationBeans.Color;
import site.morn.test.TestAnnotationBeans.Dog;
import site.morn.test.TestAnnotationBeans.Food;
import site.morn.test.TestAnnotationBeans.Mammal;
import site.morn.test.TestAnnotationBeans.Toy;

/**
 * 实例缓存单元测试
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BeanCachesTest {

  @Test
  public void bean() { // bean test
    String[] tags = Tags.from(Color.class, "yellow").add("big").toArray();
    AnnotationFeature annotationFeature = AnnotationFeatureBuilder.withTags(tags).target(Toy.class)
        .build();
    Dog dog = BeanCaches.bean(Dog.class, annotationFeature);
    Assert.assertNotNull("bean", dog);
  }

  @Test
  public void nameBean() { // bean test
    Cat caramel = BeanCaches.nameBean(Cat.class, "caramel");
    Assert.assertNotNull("nameBean", caramel);
  }

  @Test
  public void tagBean() { // bean test
    Object big = BeanCaches.tagBean(null, "big");
    Assert.assertNotNull("tagBean", big);
  }

  @Test
  public void targetBean() { // bean test
    Object o = BeanCaches.targetBean(null, Food.class);
    Assert.assertNotNull("targetBean", o);
  }

  @Test
  public void beans() { // bean test
    String[] tags = Tags.from(Animal.class).toArray();
    AnnotationFeature annotationFeature = AnnotationFeatureBuilder.withTags(tags).target(Food.class)
        .build();
    List<Cat> beans = BeanCaches.beans(null, annotationFeature);
    Assert.assertEquals("beans", 2, beans.size());
  }

  @Test
  public void tagBeans() { // bean test
    List<Mammal> small = BeanCaches.tagBeans(Mammal.class, "small");
    Assert.assertEquals("tagBeans", 3, small.size());
  }

  @Test
  public void targetBeans() { // bean test
    List<Object> objects = BeanCaches.targetBeans(null, Toy.class);
    Assert.assertEquals("targetBeans", 2, objects.size());
  }

  @Test
  public void targetCats() { // bean test
    List<Cat> cats = BeanCaches.targetBeans(Cat.class, Toy.class);
    Assert.assertEquals("targetCats", 1, cats.size());
  }

  @Test
  public void eat() { // function test
    AnnotationFeature annotationFeature = AnnotationFeatureBuilder.withName("eat").build();
    List<FunctionHolder> functions = BeanCaches.functions(annotationFeature);
    Assert.assertEquals("函数：" + functions.size(), 4, functions.size());
  }

  @Test
  public void eatMeat() { // function test
    String[] meats = Tags.from("meat").toArray();
    AnnotationFeature annotationFeature = AnnotationFeatureBuilder.withName("eat").tags(meats)
        .build();
    List<FunctionHolder> functions = BeanCaches.functions(annotationFeature);
    Assert.assertEquals("函数：" + functions.size(), 1, functions.size());
  }

  @Test
  public void play() { // function test
    AnnotationFeature annotationFeature = AnnotationFeatureBuilder.withName("play")
        .source(Cat.class).build();
    List<FunctionHolder> functions = BeanCaches.functions(annotationFeature);
    Assert.assertEquals("函数：" + functions.size(), 2, functions.size());
  }

  @Test
  public void dogEat() { // function test
    String[] beanTags = {"animal:dog"};
    AnnotationFeature beanFeature = AnnotationFeatureBuilder.withTags(beanTags).build();
    AnnotationFeature functionFeature = AnnotationFeatureBuilder.withName("eat").build();
    List<FunctionHolder> functions = BeanCaches.functions(beanFeature, functionFeature);
    Assert.assertEquals("函数：" + functions.size(), 1, functions.size());
  }

  @Test
  public void beanCache() {
    BeanCache beanCache = BeanCaches.beanCache();
    Assert.assertNotNull(beanCache);
  }

  @Test
  public void beanFactory() {
    BeanFactory beanFactory = BeanCaches.beanFactory();
    Assert.assertNotNull(beanFactory);
  }

  @Test
  public void featureString() {
    AnnotationFeature feature = AnnotationFeatureBuilder.withName("ThisName").source(Object.class)
        .target(Object.class).tags("ThisTag").build();
    Assert.assertEquals(
        "SimpleAnnotationFeature(name=ThisName, tags=[ThisTag], source=class java.lang.Object, target=class java.lang.Object)",
        feature.toString());
  }
}
