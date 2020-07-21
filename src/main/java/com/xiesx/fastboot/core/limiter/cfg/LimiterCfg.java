package com.xiesx.fastboot.core.limiter.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.fastboot.core.limiter.LimiterAspect;

@Configuration
@Import({LimiterAspect.class})
public class LimiterCfg {

}
