package com.xiesx.fastboot.core.orm.jdbc2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

import com.xiesx.fastboot.core.orm.jdbc2.factory.JdbcPlusRepositoryFactoryBean;

/**
 * @title EnableJdbcPlusRepository.java
 * @description
 * @author Sixian.xie
 * @date 2020-7-21 22:31:37
 */
@EnableJdbcRepositories(repositoryFactoryBeanClass = JdbcPlusRepositoryFactoryBean.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableJdbcPlusRepository {

}
