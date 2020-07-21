package com.xiesx.fastboot.core.jpa.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.xiesx.fastboot.core.jpa.factory.JpaPlusRepositoryFactoryBean;

/**
 * @title EnableJpaPlusRepositories.java
 * @description
 * @author Sixian.xie
 * @date 2020-7-21 22:32:26
 */
@EnableJpaRepositories(repositoryFactoryBeanClass = JpaPlusRepositoryFactoryBean.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableJpaPlusRepositories {
}
