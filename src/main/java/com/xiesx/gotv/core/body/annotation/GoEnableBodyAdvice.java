package com.xiesx.gotv.core.body.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.xiesx.gotv.core.body.BaseResultBodyAdvice;

@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({ BaseResultBodyAdvice.class })
@Documented
public @interface GoEnableBodyAdvice {
}
