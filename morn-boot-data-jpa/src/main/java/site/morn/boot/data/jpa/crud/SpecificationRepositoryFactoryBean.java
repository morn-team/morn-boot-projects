package site.morn.boot.data.jpa.crud;

import java.io.Serializable;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * 基础数据访问工厂类
 *
 * @author timely-rain
 * @since 0.0.1, 2019/4/13 0013
 */
public class SpecificationRepositoryFactoryBean<R extends SpecificationRepository<T, I>, T, I extends Serializable> extends
    JpaRepositoryFactoryBean<R, T, I> {

  public SpecificationRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
    super(repositoryInterface);
  }

  @Override
  protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
    return new BaseRepositoryFactory(em);
  }

  private static class BaseRepositoryFactory extends JpaRepositoryFactory {

    private final EntityManager em;

    public BaseRepositoryFactory(EntityManager em) {
      super(em);
      this.em = em;
    }

    @Override
    protected JpaRepositoryImplementation<?, ?> getTargetRepository(
        RepositoryInformation information, EntityManager entityManager) {
      return new SpecificationRepositoryImpl(information.getDomainType(), em);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
      return SpecificationRepositoryImpl.class;
    }
  }
}