package com.xiesx.springboot.core.logger.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import com.xiesx.springboot.core.logger.cfg.LoggerCfg;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import({LoggerCfg.class})
public @interface LoggerStorage {

    boolean print() default true;

    boolean storage() default false;

    boolean prettyFormat() default false;
}
