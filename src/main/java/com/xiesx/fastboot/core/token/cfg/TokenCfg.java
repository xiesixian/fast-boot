package com.xiesx.fastboot.core.token.cfg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xiesx.fastboot.core.token.handle.TokenArgumentResolver;
import com.xiesx.fastboot.core.token.handle.TokenInterceptorHandler;

import cn.hutool.core.collection.ListUtil;

/**
 * @title TokenCfg.java
 * @description 令牌认证
 * @author Sixian.xie
 * @date 2020-7-21 22:36:02
 */
@Configuration
@EnableConfigurationProperties(TokenProperties.class)
public class TokenCfg implements WebMvcConfigurer {

    public static final String USERID = "userid";

    public static final String USERNAME = "username";

    public static final String NICKNAME = "nickname";

    @Autowired
    private TokenProperties mTokenProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加处理器
        InterceptorRegistration in = registry.addInterceptor(new TokenInterceptorHandler());
        // 处理url
        List<String> paths = ListUtil.toList(mTokenProperties.getIncludePaths());
        if (!paths.isEmpty()) {
            in.addPathPatterns(paths);
        }
        // 排除处理url
        List<String> excludePaths = ListUtil.toList(mTokenProperties.getExcludePaths());
        if (!excludePaths.isEmpty()) {
            in.excludePathPatterns(excludePaths);
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new TokenArgumentResolver());
    }
}
