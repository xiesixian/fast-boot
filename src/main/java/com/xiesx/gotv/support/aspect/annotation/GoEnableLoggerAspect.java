package com.xiesx.gotv.support.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.xiesx.gotv.support.aspect.cfg.LoggerCfg;

@Target({java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({LoggerCfg.class})
@Documented
public @interface GoEnableLoggerAspect {}
