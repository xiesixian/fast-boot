package com.xiesx.gotv.core.logger.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.gotv.core.logger.LoggerAspect;

@Configuration
@Import({ LoggerAspect.class })
public class LoggerCfg {}
