package com.xiesx.springboot.support.sgin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * api接口，忽略Token验证
 * 
 * @author Sixian.Xie
 * @date 2018-03-19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GoSign {

	boolean ignore() default false;
}
