package com.xiesx.fastboot.core.limiter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @title GoLimit.java
 * @description 限流器
 * @author Sixian.xie
 * @date 2020-7-21 22:34:43
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GoLimit {

    String message() default "";

    double limit() default Double.MAX_VALUE;
}
