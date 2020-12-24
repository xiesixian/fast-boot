package com.xiesx.fastboot.core.body.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.xiesx.fastboot.core.body.GlobalBodyAdvice;
import com.xiesx.fastboot.core.body.cfg.HeaderCfg;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({HeaderCfg.class, GlobalBodyAdvice.class})
public @interface GoEnableBody {
}
