package com.xiesx.fastboot.core.sign.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.xiesx.fastboot.core.sign.cfg.SignalCfg;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({SignalCfg.class})
public @interface GoEnableSignal {
}
