package com.xiesx.fastboot.core.token.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @title GoToken.java
 * @description 登录用户信息
 * @author Sixian.xie
 * @date 2020-7-21 22:35:48
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface GoToken {

}
