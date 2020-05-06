package com.xiesx.springboot.core.logger.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.springboot.core.logger.LoggerAspect;

@Configuration
@Import({ LoggerAspect.class })
public class LoggerCfg {

	public static final String NICKNAME = "nickname";

}
