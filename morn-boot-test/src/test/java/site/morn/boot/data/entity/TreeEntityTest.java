package site.morn.boot.data.entity;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * 树形实体测试
 */
@RunWith(JUnit4.class)
public class TreeEntityTest {

  private Area province;

  private Area town;

  @Before
  public void setUp() throws Exception {
    province = new Area();
    province.setId(RandomUtils.nextInt());
    town = new Area();
    town.setId(RandomUtils.nextInt());
  }

  @Test
  public void parentIsNull() {
    town.beforeSave();
    Assert.assertNull(town.getParentId());
    Assert.assertEquals(String.format("|%d|", town.getId()), town.getLevelCode());
  }

  @Test
  public void parentIsNotNull() {
    province.beforeSave();
    town.setParent(province);
    town.beforeSave();
    Assert.assertEquals(province.getId(), town.getParentId());
    Assert.assertEquals(String.format("|%d|%d|", province.getId(), town.getId()),
        town.getLevelCode());
  }

  private class Area extends TreeEntity<Area, Integer> {

  }
}