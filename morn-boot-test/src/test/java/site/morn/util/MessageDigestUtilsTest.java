package site.morn.util;

import static site.morn.constant.DigestConstant.Algorithms.MD5;
import static site.morn.constant.DigestConstant.Algorithms.SPRING_B_CRYPT;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 数据加密单元测试
 *
 * @author timely-rain
 * @since 1.2.1, 2020/4/24
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageDigestUtilsTest {

  @Test
  public void encryptBCrypt() {
    String password = MessageDigestUtils
        .encrypt(SPRING_B_CRYPT, "password");
    Assert.assertNotEquals("password", password);
    Assert.assertTrue(MessageDigestUtils.matches(SPRING_B_CRYPT, "password",
        "{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG"));
  }

  @Test
  public void encryptMD5() {
    String password = MessageDigestUtils.encrypt(MD5, "password");
    Assert.assertNotEquals("password", password);
    Assert.assertTrue(
        MessageDigestUtils.matches(MD5, "password", "5f4dcc3b5aa765d61d8327deb882cf99"));
  }
}