package com.xiesx.springboot.support.event;

import org.apache.commons.lang3.ObjectUtils;

import com.google.common.eventbus.EventBus;
import com.xiesx.springboot.support.event.base.AbstractEventBus;
import com.xiesx.springboot.support.event.base.EventBusInterface;

import lombok.extern.slf4j.Slf4j;

/**
 * @title SimpleEventBusUtils.java
 * @description 事件总线工具类
 * @author Sixian.xie
 * @date 2019年3月12日 下午5:51:58
 */
@Slf4j
public class EventBusHelper {

    private final static EventBus eventBus = new EventBus();

    /**
     * 发布
     *
     * @param event
     */
    public static void post(EventBusInterface event) {
        if (ObjectUtils.isEmpty(event)) {
            return;
        }
        log.info("post : {}", event.getClass().getName());
        eventBus.post(event);
    }

    /**
     * 注册
     *
     * @param handler
     */
    public static void register(AbstractEventBus<? extends EventBusInterface> handler) {
        if (ObjectUtils.isEmpty(handler)) {
            return;
        }
        log.info("register : {}", handler.getClass().getName());
        eventBus.register(handler);
    }

    /**
     * 销毁
     *
     * @param handler
     */
    public static void unregister(AbstractEventBus<? extends EventBusInterface> handler) {
        if (ObjectUtils.isEmpty(handler)) {
            return;
        }
        log.info("unregister : {}", handler.getClass().getName());
        eventBus.unregister(handler);
    }
}
