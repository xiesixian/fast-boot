package com.xiesx.gotv.support.aspect.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.xiesx.gotv.support.aspect.cfg.LoggerCfg;

@Target({ java.lang.annotation.ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Import({ LoggerCfg.class })
public @interface GoEnableLoggerStorage {

	boolean print() default false;

	boolean storage() default false;

	boolean prettyFormat() default false;
}
