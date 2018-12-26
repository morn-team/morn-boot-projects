package site.morn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableCaching
@EnableTransactionManagement
@EnableWebSocket
public class TestApplicationLauncher {

  public static void main(String[] args) {
    SpringApplication.run(TestApplicationLauncher.class, args);
  }
}
