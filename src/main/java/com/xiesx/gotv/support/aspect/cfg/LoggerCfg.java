package com.xiesx.gotv.support.aspect.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.gotv.support.aspect.LoggerAspect;

@Configuration
@Import({ LoggerAspect.class })
public class LoggerCfg {}
