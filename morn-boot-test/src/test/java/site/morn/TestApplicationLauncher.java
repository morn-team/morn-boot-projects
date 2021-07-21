package site.morn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import site.morn.boot.data.jpa.crud.SpecificationRepositoryFactoryBean;
import site.morn.boot.log.EnableOperateLog;
import site.morn.boot.netty.annotation.EnableNettyClient;
import site.morn.boot.netty.annotation.EnableNettyServer;
import site.morn.boot.notify.annotation.EnableNotify;
import site.morn.boot.template.annotation.EnableTemplate;

@SpringBootApplication
@EnableAsync
@EnableNotify
@EnableCaching
@EnableTemplate
@EnableOperateLog
@EnableJpaAuditing
@EnableNettyClient
@EnableNettyServer
@EnableTransactionManagement
@EnableJpaRepositories(repositoryFactoryBeanClass = SpecificationRepositoryFactoryBean.class)
public class TestApplicationLauncher {

  public static void main(String[] args) {
    SpringApplication.run(TestApplicationLauncher.class, args);
  }
}
