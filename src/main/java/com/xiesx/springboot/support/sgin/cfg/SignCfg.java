package com.xiesx.springboot.support.sgin.cfg;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.springboot.support.sgin.SignAspect;

/**
 * @title EventBusCfg.java
 * @description 事件总线配置
 * @author Sixian.xie
 * @date 2019年3月14日 上午10:48:52
 */
@Configuration
@EnableConfigurationProperties(SignProperties.class)
@ConditionalOnProperty(prefix = "fastboot.sign", name = {"key", "val", "open"})
@Import({SignAspect.class})
public class SignCfg {

    @SuppressWarnings("unused")
    private SignProperties mSignProperties;

    public SignCfg(SignProperties signProperties) {
        this.mSignProperties = signProperties;
    }
}
