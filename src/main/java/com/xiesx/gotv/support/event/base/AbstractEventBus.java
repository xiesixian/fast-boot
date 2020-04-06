package com.xiesx.gotv.support.event.base;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.ReflectionUtils;

import com.google.common.eventbus.Subscribe;

/***
 * @title SimpleEventAdapter.java
 * @description 事件总线接口 (抽象父类)
 * @author Sixian.xie
 * @date 2019年3月12日 下午5:47:10
 */
@Slf4j
public abstract class AbstractEventBus<T extends EventBusInterface> {

	private static final String METHOD_NAME = "process";

	@Subscribe
	@SuppressWarnings("all")
	public void onEvent(EventBusInterface event) {
		if (ReflectionUtils.findMethod(this.getClass(), METHOD_NAME, event.getClass()) != null) {
			try {
				if (!process((T) event)) {
					log.info("handle event {} fail", event.getClass());
				}
			} catch (Exception e) {
				log.error(String.format("handle event %s exception", event.getClass()), e);
			}
		}
	}

	public abstract boolean process(T t);
}