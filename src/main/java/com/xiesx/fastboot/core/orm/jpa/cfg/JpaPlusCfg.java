package com.xiesx.fastboot.core.orm.jpa.cfg;

import javax.persistence.EntityManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.querydsl.jpa.impl.JPAQueryFactory;

/**
 * @title TokenCfg.java
 * @description 令牌认证
 * @author Sixian.xie
 * @date 2020-7-21 22:36:02
 */
@Configuration
public class JpaPlusCfg implements WebMvcConfigurer {

    @Bean
    public JPAQueryFactory jpaQuery(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
