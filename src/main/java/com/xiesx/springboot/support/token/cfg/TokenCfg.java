package com.xiesx.springboot.support.token.cfg;

import java.util.List;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xiesx.springboot.support.token.handle.TokenInterceptorHandler;
import com.xiesx.springboot.support.token.handle.TokenMethodArgumentResolverHandler;

/**
 * @title EventBusCfg.java
 * @description 事件总线配置
 * @author Sixian.xie
 * @date 2019年3月14日 上午10:48:52
 */
@Configuration
@EnableConfigurationProperties(TokenProperties.class)
public class TokenCfg implements WebMvcConfigurer {

    public static final String USERID = "userid";

    public static final String USERNAME = "username";

    public static final String NICKNAME = "nickname";

    public TokenProperties mTokenProperties;

    public TokenCfg(TokenProperties mTokenProperties) {
        this.mTokenProperties = mTokenProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptorHandler(mTokenProperties)).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new TokenMethodArgumentResolverHandler());
    }
}
