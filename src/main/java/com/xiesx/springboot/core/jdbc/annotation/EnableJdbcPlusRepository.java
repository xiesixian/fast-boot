package com.xiesx.springboot.core.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

import com.xiesx.springboot.core.jdbc.JdbcPlusRepository;
import com.xiesx.springboot.core.jdbc.factory.JdbcPlusRepositoryFactoryBean;

/**
 * Annotation to enable {@link JdbcPlusRepository} support.
 *
 * @see EnableJdbcRepositories
 */
@EnableJdbcRepositories(repositoryFactoryBeanClass = JdbcPlusRepositoryFactoryBean.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableJdbcPlusRepository {

}
