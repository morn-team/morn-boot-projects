package site.morn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import site.morn.boot.netty.annotation.EnableNettyClient;
import site.morn.boot.netty.annotation.EnableNettyServer;
import site.morn.boot.support.JpaRepositoryFactoryBean;


@EnableAsync
@EnableCaching
@EnableJpaRepositories(repositoryFactoryBeanClass = JpaRepositoryFactoryBean.class)
@EnableNettyClient
@EnableNettyServer
@EnableTransactionManagement
@SpringBootApplication
public class TestApplicationLauncher {

  public static void main(String[] args) {
    SpringApplication.run(TestApplicationLauncher.class, args);
  }
}
