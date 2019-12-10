package com.xiesx.gotv.core.body.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.xiesx.gotv.core.body.BaseResultBodyAdvice;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({ BaseResultBodyAdvice.class })
public @interface GoEnableBodyAdvice {
}
