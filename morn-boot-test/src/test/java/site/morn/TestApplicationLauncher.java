package site.morn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import site.morn.boot.support.JpaRepositoryFactoryBean;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(repositoryFactoryBeanClass = JpaRepositoryFactoryBean.class)
@EnableTransactionManagement
public class TestApplicationLauncher {

  public static void main(String[] args) {
    SpringApplication.run(TestApplicationLauncher.class, args);
  }
}
