package com.xiesx.fastboot.core.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.xiesx.fastboot.core.json.cfg.FastJsonCfg;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({FastJsonCfg.class})
public @interface GoEnableFastJson {
}
