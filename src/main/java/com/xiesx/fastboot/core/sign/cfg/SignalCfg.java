package com.xiesx.fastboot.core.sign.cfg;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.fastboot.core.sign.SignalAspect;

@Configuration
@EnableConfigurationProperties(SignalProperties.class)
@Import({SignalAspect.class})
public class SignalCfg {

}
