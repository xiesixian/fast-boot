package com.xiesx.fastboot.core.body.cfg;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xiesx.fastboot.core.body.handle.HeaderArgumentResolver;

/**
 * @title BodyCfg.java
 * @description 请求参数配置
 * @author Sixian.xie
 * @date 2020-7-21 22:44:43
 */
@Configuration
public class HeaderCfg implements WebMvcConfigurer {

    public static final String DEVICE = "device";

    public static final String OSVERSION = "osVersion";

    public static final String APVERSION = "apVersion";

    public static final String APVERSIONCODE = "apVersionCode";

    public static final String ANDROIDID = "androidId";

    public static final String PSUEDOUNIQUEID = "psuedoUniqueId";

    public static final String NETWORKTYPE = "networkType";
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new HeaderArgumentResolver());
    }
}
