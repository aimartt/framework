package com.aimartt.framework.jpa.repository.support;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.aimartt.framework.jpa.repository.EntityJpaRepository;

public class EntityRepositoryFactoryBean<T extends JpaRepository<M, ID>, M, ID extends Serializable> extends
		JpaRepositoryFactoryBean<T, M, ID> {

	private static final Logger log = Logger.getLogger(EntityRepositoryFactoryBean.class);

	@Override
	protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
		return new EntityRepositoryFactory<M, ID>(entityManager);
	}

	private static class EntityRepositoryFactory<M, ID extends Serializable> extends JpaRepositoryFactory {
		private final EntityManager entityManager;
		public EntityRepositoryFactory(EntityManager entityManager) {
			super(entityManager);
			this.entityManager = entityManager;
		}

		@Override
		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			Class<?> repositoryInterface = metadata.getRepositoryInterface();
			if (isEntityJpaRepository(repositoryInterface)) {
				return SimpleEntityJpaRepository.class;
			}
			return super.getRepositoryBaseClass(metadata);
		}

		private boolean isEntityJpaRepository(Class<?> repositoryInterface) {
			return EntityJpaRepository.class.isAssignableFrom(repositoryInterface);
		}

		@Override
		@SuppressWarnings("unchecked")
		protected Object getTargetRepository(RepositoryMetadata metadata) {
			Class<?> repositoryInterface = metadata.getRepositoryInterface();
			if (isEntityJpaRepository(repositoryInterface)) {
				if (log.isDebugEnabled()) {
					log.debug("创建" + repositoryInterface.getSimpleName() + "JPA实现类");
				}
				Class<M> domainClass = (Class<M>) metadata.getDomainType();
				return new SimpleEntityJpaRepository<M, ID>(domainClass, entityManager);
			}
			return super.getTargetRepository(metadata);
		}
	}
	
}