package site.morn.boot.netty;

import static org.awaitility.Awaitility.await;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import java.util.Objects;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Netty客户端单元测试
 *
 * @author timely-rain
 * @since 1.2.0, 2019/6/5
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class NettyClientTest {

  @Resource
  private NettyClient client;

  @Before
  public void setUp() throws Exception {
    client.connect().await();
  }

  @Test
  public void write() {
    String msg = "123456";
    Channel channel = client.getChannel();
    Assert.assertNotNull(channel);
    ChannelFuture channelFuture = channel.writeAndFlush(msg)
        .addListener(f -> {
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
}