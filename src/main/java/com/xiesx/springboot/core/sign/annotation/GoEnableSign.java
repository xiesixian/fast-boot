package com.xiesx.springboot.core.sign.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.xiesx.springboot.core.sign.cfg.SignCfg;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({SignCfg.class})
public @interface GoEnableSign {
}
