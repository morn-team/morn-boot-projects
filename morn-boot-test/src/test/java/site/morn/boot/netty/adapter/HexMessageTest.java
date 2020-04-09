package site.morn.boot.netty.adapter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 十六进制消息单元测试
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/18
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class HexMessageTest {

  @Test
  public void addByte() {
    HexMessage hexMessage = new HexMessage().addByte(8);
    Assert.assertArrayEquals(hexMessage.getArray(), new byte[]{8});
    Assert.assertEquals("08", hexMessage.getMessage());
  }

  @Test
  public void addInt() {
    HexMessage hexMessage = new HexMessage().addInt(255);
    Assert.assertArrayEquals(hexMessage.getArray(), new byte[]{0, -1});
    Assert.assertEquals("00FF", hexMessage.getMessage());
  }

  @Test
  public void reverse() {
    HexMessage hexMessage = new HexMessage().addInt(255).reverse();
    Assert.assertArrayEquals(hexMessage.getArray(), new byte[]{-1, 0});
    Assert.assertEquals("FF00", hexMessage.getMessage());
  }
}