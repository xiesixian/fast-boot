package com.xiesx.fastboot.support.request.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.fastboot.support.request.HttpCilentRetryImplRe;

@Configuration
@Import({HttpCilentRetryImplRe.class})
public class HttpCilentRetryCfg {
}
