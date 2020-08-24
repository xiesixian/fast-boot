package com.xiesx.fastboot.core.sign.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @title GoSign.java
 * @description 数据签名
 * @author Sixian.xie
 * @date 2020-7-21 22:35:16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GoSign {

    boolean ignore() default false;
}
