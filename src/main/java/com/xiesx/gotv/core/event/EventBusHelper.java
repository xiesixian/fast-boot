package com.xiesx.gotv.core.event;

import lombok.extern.slf4j.Slf4j;

import com.google.common.eventbus.EventBus;
import com.xiesx.gotv.core.event.base.AbstractEventBus;
import com.xiesx.gotv.core.event.base.EventBusInterface;

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
		if (event == null) {
			return;
		}
		log.info("post : {}", event.getClass());
		eventBus.post(event);
	}

	/**
	 * 注册
	 * 
	 * @param handler
	 */
	public static void register(AbstractEventBus<? extends EventBusInterface> handler) {
		if (handler == null) {
			return;
		}
		log.info("register : {}", handler.getClass());
		eventBus.register(handler);
	}

	/**
	 * 销毁
	 * 
	 * @param handler
	 */
	public static void unregister(AbstractEventBus<? extends EventBusInterface> handler) {
		if (handler == null) {
			return;
		}
		log.info("unregister : {}", handler.getClass());
		eventBus.unregister(handler);
	}
}