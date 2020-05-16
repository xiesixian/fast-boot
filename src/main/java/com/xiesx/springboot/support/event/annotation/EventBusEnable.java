package com.xiesx.springboot.support.event.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;

/**
 * @title EnableEventBus.java
 * @description 启用事件总线
 * @author Sixian.xie
 * @date 2019年3月12日 下午6:06:05
 */
// 申明注解的作用位置
@Target(ElementType.TYPE)
// 运行时机
@Retention(RetentionPolicy.RUNTIME)
@Configuration
public @interface EventBusEnable {

}
