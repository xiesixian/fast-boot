package com.xiesx.springboot.support.sgin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.xiesx.springboot.support.sgin.cfg.SignCfg;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({SignCfg.class})
public @interface GoEnableSign {
}
