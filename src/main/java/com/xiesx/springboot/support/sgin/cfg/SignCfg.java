package com.xiesx.springboot.support.sgin.cfg;

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
@Import({SignAspect.class})
public class SignCfg {

}
