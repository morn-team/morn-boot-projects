package site.morn.bean;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.bean.support.AnnotationFeatureBuilder;
import site.morn.bean.support.BeanCaches;
import site.morn.bean.support.FunctionHolderUtils;
import site.morn.bean.support.Tags;
import site.morn.test.TestAnnotationBeans.Animal;
import site.morn.test.TestAnnotationBeans.Food;

/**
 * 实例函数单元测试
 *
 * @author timely-rain
 * @since 1.0.0, 2019/5/19
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class FunctionHolderUtilsTest {

  @Test
  public void catPlay() {
    String[] cats = Tags.from(Animal.class, "cat").toArray();
    AnnotationFeature beanId = AnnotationFeatureBuilder.withTags(cats).build();
    AnnotationFeature functionId = AnnotationFeatureBuilder.withName("play").build();
    List<FunctionHolder> functions = BeanCaches.functions(beanId, functionId);
    try {
      List<String> call = FunctionHolderUtils.call(functions);
      log.info(call.toString());
      Assert.assertEquals("catPlay", 2, call.size());
    } catch (Exception e) {
      Assert.fail("catPlay");
    }
  }

  @Test
  public void eat() {
    String meat = "meat";
    Food food = new Food("fish");

    AnnotationFeature functionId = AnnotationFeatureBuilder.withName("eat").build();
    List<FunctionHolder> functions = BeanCaches.functions(functionId);
    try {
      List<String> call = FunctionHolderUtils.call(functions, food, meat);
      log.info(call.toString());
      Assert.assertEquals("call", 4, call.size());
    } catch (Exception e) {
      Assert.fail("call");
    }
  }
}