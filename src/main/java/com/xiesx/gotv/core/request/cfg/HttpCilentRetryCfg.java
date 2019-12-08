package com.xiesx.gotv.core.request.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.gotv.core.request.HttpCilentRetryImplRe;

@Configuration
@Import({ HttpCilentRetryImplRe.class })
public class HttpCilentRetryCfg {}
