package com.xiesx.fastboot.core.sign.cfg;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.fastboot.core.sign.SignalAspect;

/**
 * @title EventBusCfg.java
 * @description 事件总线配置
 * @author Sixian.xie
 * @date 2019年3月14日 上午10:48:52
 */
@Configuration
@EnableConfigurationProperties(SignalProperties.class)
@Import({SignalAspect.class})
public class SignalCfg {

}
