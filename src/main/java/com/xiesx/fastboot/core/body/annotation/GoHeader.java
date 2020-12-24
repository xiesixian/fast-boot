package com.xiesx.fastboot.core.body.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

// 申明注解的作用位置
@Target({PARAMETER})
// 运行时机
@Retention(RUNTIME)
public @interface GoHeader {

    boolean required() default true;
}
