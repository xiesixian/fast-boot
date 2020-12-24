package com.xiesx.fastboot.core.body.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * @title GoHeader.java
 * @description 请求头信息
 * @author Sixian.xie
 * @date 2020-7-21 22:35:48
 */
@Target({PARAMETER})
@Retention(RUNTIME)
public @interface GoHeader {

}
