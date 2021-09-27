package site.morn.boot.netty;

import static org.awaitility.Awaitility.await;
import static site.morn.boot.netty.config.NettyConfigConstants.DEFAULT_HOST;
import static site.morn.boot.netty.config.NettyConfigConstants.DEFAULT_PORT;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
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
import site.morn.boot.netty.adapter.HexMessage;
import site.morn.boot.netty.config.NettyClientConfig;
import site.morn.boot.netty.support.FutureAccessor;
import site.morn.boot.netty.support.RemoteAddressChannelPoolIdentify;


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

  private NettyClient client;

  @Resource
  private NettyServer server;

  @Before
  public void setUp() {
    client = NettyClientPools
        .createClient(new RemoteAddressChannelPoolIdentify(DEFAULT_HOST, DEFAULT_PORT));
  }

  @Test
  public void test1Write() {
    HexMessage hexMessage = new HexMessage("f1f1f0f0");
    RemoteAddressChannelPoolIdentify identify = new RemoteAddressChannelPoolIdentify(DEFAULT_HOST,
        DEFAULT_PORT);
    FutureAccessor<Channel> accessor = NettyChannelOperations
        .writeAndFlush(identify, hexMessage)
        .failureThen(throwable -> log.warn(throwable.getMessage(), throwable));
    await().timeout(10, TimeUnit.MINUTES).until(() -> {
      Future<Channel> f = accessor.getFuture().await();
      return f.isDone() && f.isSuccess();
    });
  }

  @Test
  public void test2Close() {
    Bootstrap bootstrap = client.getBootstrap();
    Assert.assertNotNull(bootstrap);
    EventLoopGroup group = client.getGroup();
    Assert.assertNotNull(group);
    NettyClientConfig config = client.getConfig();
    Assert.assertNotNull(config);
    try {
      client.close();
      server.close();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      Assert.fail();
    }
  }
}