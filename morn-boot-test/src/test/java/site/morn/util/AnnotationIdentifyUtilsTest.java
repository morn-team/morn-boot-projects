package site.morn.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 注解标识工具类单元测试
 *
 * @author timely-rain
 * @since 2.1.0, 2019/5/18
 */
@RunWith(JUnit4.class)
public class AnnotationIdentifyUtilsTest {

  private String[] allSuits = {AnnotationIdentifyUtils.WILDCARD};
  private String[] whiteAnimalSuits = {"animal:*", "color:white"};
  private String[] dogSuits = {"animal:dog", "color:*"};
  private String[] blackCatSuits = {"animal:cat", "color:black"};

  private String[] whiteAnimalLimits = {"animal:", "color:white"};
  private String[] blackDogLimits = {"animal:dog", "color:black"};
  private String[] whiteDogLimits = {"animal:dog", "color:white"};
  private String[] catLimits = {"animal:cat", "color"};

  private String[] empty = {"", null, ":", "abc"};

  @Test
  public void all() {
    boolean suit1 = AnnotationIdentifyUtils.isSuitable(allSuits, whiteAnimalLimits);
    boolean suit2 = AnnotationIdentifyUtils.isSuitable(allSuits, blackDogLimits);
    boolean suit3 = AnnotationIdentifyUtils.isSuitable(allSuits, whiteDogLimits);
    boolean suit4 = AnnotationIdentifyUtils.isSuitable(whiteAnimalSuits, catLimits);
    Assert.assertTrue("suit1", suit1);
    Assert.assertTrue("suit2", suit2);
    Assert.assertTrue("suit3", suit3);
    Assert.assertTrue("suit4", suit4);
  }

  @Test
  public void whiteAnimal() {
    boolean suit1 = AnnotationIdentifyUtils.isSuitable(whiteAnimalSuits, whiteAnimalLimits);
    boolean suit2 = AnnotationIdentifyUtils.isSuitable(whiteAnimalSuits, blackDogLimits);
    boolean suit3 = AnnotationIdentifyUtils.isSuitable(whiteAnimalSuits, whiteDogLimits);
    boolean suit4 = AnnotationIdentifyUtils.isSuitable(whiteAnimalSuits, catLimits);
    Assert.assertTrue("suit1", suit1);
    Assert.assertFalse("suit2", suit2);
    Assert.assertTrue("suit3", suit3);
    Assert.assertTrue("suit4", suit4);
  }

  @Test
  public void dog() {
    boolean suit1 = AnnotationIdentifyUtils.isSuitable(dogSuits, whiteAnimalLimits);
    boolean suit2 = AnnotationIdentifyUtils.isSuitable(dogSuits, blackDogLimits);
    boolean suit3 = AnnotationIdentifyUtils.isSuitable(dogSuits, whiteDogLimits);
    boolean suit4 = AnnotationIdentifyUtils.isSuitable(dogSuits, catLimits);
    Assert.assertTrue("suit1", suit1);
    Assert.assertTrue("suit2", suit2);
    Assert.assertTrue("suit3", suit3);
    Assert.assertFalse("suit4", suit4);
  }

  @Test
  public void blackCat() {
    boolean suit1 = AnnotationIdentifyUtils.isSuitable(blackCatSuits, whiteAnimalLimits);
    boolean suit2 = AnnotationIdentifyUtils.isSuitable(blackCatSuits, blackDogLimits);
    boolean suit3 = AnnotationIdentifyUtils.isSuitable(blackCatSuits, whiteDogLimits);
    boolean suit4 = AnnotationIdentifyUtils.isSuitable(blackCatSuits, catLimits);
    Assert.assertFalse("suit1", suit1);
    Assert.assertFalse("suit2", suit2);
    Assert.assertFalse("suit3", suit3);
    Assert.assertTrue("suit4", suit4);
  }

  @Test
  public void other() {
    boolean suit1 = AnnotationIdentifyUtils.isSuitable(empty, whiteAnimalLimits);
    boolean suit2 = AnnotationIdentifyUtils.isSuitable(blackCatSuits, empty);
    Assert.assertFalse("suit1", suit1);
    Assert.assertFalse("suit2", suit2);
  }
}