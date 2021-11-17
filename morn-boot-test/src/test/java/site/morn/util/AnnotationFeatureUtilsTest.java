package site.morn.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import site.morn.test.TestAnnotationBeans.Cat;
import site.morn.test.TestAnnotationBeans.Color;
import site.morn.test.TestAnnotationBeans.Food;
import site.morn.test.TestAnnotationBeans.Mammal;

/**
 * 注解标识工具类单元测试
 *
 * @author timely-rain
 * @since 1.2.0, 2019/5/18
 */
@RunWith(JUnit4.class)
public class AnnotationFeatureUtilsTest {

  private final String[] allSuits = {AnnotationFeatureUtils.WILDCARD};
  private final String[] whiteAnimalSuits = {"animal:*", "color:white"};
  private final String[] dogSuits = {"animal:dog", "color:*"};
  private final String[] blackCatSuits = {"animal:cat", "color:black"};

  private final String[] whiteAnimalLimits = {"animal:", "color:white"};
  private final String[] blackDogLimits = {"animal:dog", "color:black"};
  private final String[] whiteDogLimits = {"animal:dog", "color:white"};
  private final String[] catLimits = {"animal:cat", "color"};

  private final String[] empty = {"", null, ":", "abc"};

  @Test
  public void all() {
    boolean suit1 = AnnotationFeatureUtils.isSuitable(allSuits, whiteAnimalLimits);
    boolean suit2 = AnnotationFeatureUtils.isSuitable(allSuits, blackDogLimits);
    boolean suit3 = AnnotationFeatureUtils.isSuitable(allSuits, whiteDogLimits);
    boolean suit4 = AnnotationFeatureUtils.isSuitable(whiteAnimalSuits, catLimits);
    Assert.assertTrue("suit1", suit1);
    Assert.assertTrue("suit2", suit2);
    Assert.assertTrue("suit3", suit3);
    Assert.assertTrue("suit4", suit4);
  }

  @Test
  public void whiteAnimal() {
    boolean suit1 = AnnotationFeatureUtils.isSuitable(whiteAnimalSuits, whiteAnimalLimits);
    boolean suit2 = AnnotationFeatureUtils.isSuitable(whiteAnimalSuits, blackDogLimits);
    boolean suit3 = AnnotationFeatureUtils.isSuitable(whiteAnimalSuits, whiteDogLimits);
    boolean suit4 = AnnotationFeatureUtils.isSuitable(whiteAnimalSuits, catLimits);
    Assert.assertTrue("suit1", suit1);
    Assert.assertFalse("suit2", suit2);
    Assert.assertTrue("suit3", suit3);
    Assert.assertTrue("suit4", suit4);
  }

  @Test
  public void dog() {
    boolean suit1 = AnnotationFeatureUtils.isSuitable(dogSuits, whiteAnimalLimits);
    boolean suit2 = AnnotationFeatureUtils.isSuitable(dogSuits, blackDogLimits);
    boolean suit3 = AnnotationFeatureUtils.isSuitable(dogSuits, whiteDogLimits);
    boolean suit4 = AnnotationFeatureUtils.isSuitable(dogSuits, catLimits);
    Assert.assertTrue("suit1", suit1);
    Assert.assertTrue("suit2", suit2);
    Assert.assertTrue("suit3", suit3);
    Assert.assertFalse("suit4", suit4);
  }

  @Test
  public void blackCat() {
    boolean suit1 = AnnotationFeatureUtils.isSuitable(blackCatSuits, whiteAnimalLimits);
    boolean suit2 = AnnotationFeatureUtils.isSuitable(blackCatSuits, blackDogLimits);
    boolean suit3 = AnnotationFeatureUtils.isSuitable(blackCatSuits, whiteDogLimits);
    boolean suit4 = AnnotationFeatureUtils.isSuitable(blackCatSuits, catLimits);
    boolean suit5 = AnnotationFeatureUtils.isSuitable(null, catLimits);
    Assert.assertFalse("suit1", suit1);
    Assert.assertFalse("suit2", suit2);
    Assert.assertFalse("suit3", suit3);
    Assert.assertTrue("suit4", suit4);
    Assert.assertFalse("suit5", suit5);
  }

  @Test
  public void other() {
    boolean suit1 = AnnotationFeatureUtils.isSuitable(empty, whiteAnimalLimits);
    boolean suit2 = AnnotationFeatureUtils.isSuitable(blackCatSuits, empty);
    Assert.assertFalse("suit1", suit1);
    Assert.assertFalse("suit2", suit2);
  }

  @Test
  public void getTag() {
    String tag = AnnotationFeatureUtils.getTag(Color.class);
    Assert.assertEquals("color", tag);
  }

  @Test
  public void testGetTag() {
    String tag = AnnotationFeatureUtils.getTag("", "red");
    Assert.assertEquals("red", tag);
  }

  @Test
  public void testIsInstance() {
    boolean instance = AnnotationFeatureUtils.isInstance(Cat.class, Mammal.class);
    boolean notInstance = AnnotationFeatureUtils.isInstance(Food.class, Mammal.class);
    Assert.assertTrue(instance);
    Assert.assertFalse(notInstance);
  }
}
