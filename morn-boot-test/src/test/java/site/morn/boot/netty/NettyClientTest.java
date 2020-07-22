package site.morn.boot.netty;

import static org.awaitility.Awaitility.await;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.boot.netty.config.NettyClientProperties;


/**
 * Netty客户端单元测试
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/5
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NettyClientTest {

  @Resource
  private NettyClient client;

  @Resource
  private NettyServer server;

  @Before
  public void setUp() throws Exception {
    client.connect().get();
  }

  @Test
  public void test1Write() {
    String msg = "123456";
    Channel channel = client.getChannel();
    Assert.assertNotNull(channel);
    ChannelFuture channelFuture = channel.writeAndFlush(msg).addListener(f -> {
      Throwable cause = f.cause();
      if (Objects.nonNull(cause)) {
        log.warn(cause.getMessage(), cause);
      }
    });
    await().until(() -> {
      ChannelFuture future = channelFuture.await();
      return future.isDone() && future.isSuccess();
    });
  }

  @Test
  public void test2Close() {
    Bootstrap bootstrap = client.getBootstrap();
    Assert.assertNotNull(bootstrap);
    EventLoopGroup group = client.getGroup();
    Assert.assertNotNull(group);
    NettyClientProperties properties = client.getProperties();
    Assert.assertNotNull(properties);
    try {
      client.close();
      server.close();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      Assert.fail();
    }
  }
}