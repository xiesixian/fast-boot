package com.xiesx.springboot.core.jpa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.xiesx.springboot.core.jpa.factory.JpaPlusRepositoryFactoryBean;

/**
 * Annotation to enable {@link ExtendedQuerydslJpaRepository} support.
 *
 * @see EnableJpaRepositories
 */
@EnableJpaRepositories(repositoryFactoryBeanClass = JpaPlusRepositoryFactoryBean.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableJpaPlusRepositories {
}
