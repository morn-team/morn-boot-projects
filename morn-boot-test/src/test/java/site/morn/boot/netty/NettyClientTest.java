package site.morn.boot.netty;

import io.netty.channel.ChannelFuture;
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
 * @since 2.1.0, 2019/6/5
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class NettyClientTest {

  @Resource
  private NettyClient client;

  @Before
  public void setUp() throws Exception {
    client.connect().await();
  }

  @Test
  public void write() throws InterruptedException {
    String msg = "123456";
    ChannelFuture future = client.getChannel().writeAndFlush(msg).await();
    Assert.assertTrue(future.isDone());
    Assert.assertTrue(future.isSuccess());
  }
}