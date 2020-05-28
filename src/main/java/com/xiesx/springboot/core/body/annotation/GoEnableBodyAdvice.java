package com.xiesx.springboot.core.body.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.xiesx.springboot.core.body.GlobalBodyAdvice;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({GlobalBodyAdvice.class})
public @interface GoEnableBodyAdvice {
}
