package com.xiesx.springboot.core.jpa.factory;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;

import com.xiesx.springboot.core.jpa.JpaPlusRepositoryExecutor;

public class JpaPlusRepositoryFactory extends JpaRepositoryFactory {

    public JpaPlusRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return JpaPlusRepositoryExecutor.class;
    }

    @Override
    protected JpaRepositoryImplementation<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
        return new JpaPlusRepositoryExecutor<>(information.getDomainType(), entityManager);
    }
}
