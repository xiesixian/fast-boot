package com.xiesx.gotv.core.exception.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.xiesx.gotv.core.exception.BaseRestExceptionAdvice;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Import({ BaseRestExceptionAdvice.class })
@Documented
public @interface GoEnableRestExcAdvice {
}
