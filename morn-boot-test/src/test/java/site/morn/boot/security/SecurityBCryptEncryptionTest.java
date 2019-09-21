package site.morn.boot.security;

import static site.morn.constant.DigestConstant.Algorithms.SPRING_B_CRYPT;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.morn.util.MessageDigestUtils;

/**
 * SecurityBCrypt加密单元测试
 *
 * @author timely-rain
 * @since 1.2.0, 2019/8/30
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SecurityBCryptEncryptionTest {

  @Test
  public void encrypt() {
    String password = MessageDigestUtils
        .encrypt(SPRING_B_CRYPT, "password");
    Assert.assertNotEquals("password", password);
    Assert.assertTrue(MessageDigestUtils.matches(SPRING_B_CRYPT, "password",
        "{bcrypt}$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG"));
  }
}