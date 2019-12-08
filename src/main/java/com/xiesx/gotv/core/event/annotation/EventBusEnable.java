package com.xiesx.gotv.core.event.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.xiesx.gotv.core.event.cfg.EventBusCfg;

/**
 * @title EnableEventBus.java
 * @description 启用事件总线
 * @author Sixian.xie
 * @date 2019年3月12日 下午6:06:05
 */
//申明注解的作用位置
@Target(ElementType.TYPE)
//运行时机
@Retention(RetentionPolicy.RUNTIME)
//初始扫描
@Configuration
//动态注入
@Import(EventBusCfg.class)
public @interface EventBusEnable {

}