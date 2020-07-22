package site.morn.boot.netty.cache;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.local.LocalChannel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Netty缓存单元测试
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/6
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NettyCachesTest {

  private static final Channel localChannel = new LocalChannel();
  private static final String VIRUS_A = "SARS";
  private static final String VIRUS_B = "MERS";
  private String groupVirus;
  private ChannelIdentify idA;
  private ChannelIdentify idB;

  @Before
  public void setUp() throws Exception {
    groupVirus = "virus";
    idA = new ChannelIdentify(groupVirus, VIRUS_A);
    idB = new ChannelIdentify(groupVirus, VIRUS_B);
  }

  @Test
  public void test1PutChannelByString() {
    try {
      NettyCaches.putChannel(groupVirus, VIRUS_A, localChannel);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      Assert.fail();
    }
  }

  @Test
  public void test2GetChannel() {
    Channel channel = NettyCaches.getChannel(idA);
    Assert.assertNotNull(channel);
    Assert.assertEquals(localChannel, channel);
  }

  @Test
  public void test3RemoveById() {
    NettyCaches.removeAll(idA);
    Channel channel = NettyCaches.getChannel(idA);
    Assert.assertNull(channel);
  }

  @Test
  public void test4PutChannelById() {
    try {
      NettyCaches.putChannel(idB, localChannel);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      Assert.fail();
    }
    Assert.assertNotNull(localChannel);
  }

  @Test
  public void test5RemoveById() {
    Channel channel = NettyCaches.getChannel(idB);
    ChannelId id = channel.id();
    NettyCaches.removeAll(id);
    channel = NettyCaches.getChannel(idB);
    Assert.assertNull(channel);
  }
}