package com.xiesx.springboot.support.request.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.springboot.support.request.HttpCilentRetryImplRe;

@Configuration
@Import({ HttpCilentRetryImplRe.class })
public class HttpCilentRetryCfg {}
