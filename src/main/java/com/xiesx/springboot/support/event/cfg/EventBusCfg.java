package com.xiesx.springboot.support.event.cfg;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Configuration;

import com.xiesx.springboot.support.context.SpringStartup;
import com.xiesx.springboot.support.event.EventBusHelper;
import com.xiesx.springboot.support.event.base.AbstractEventBus;

/**
 * @title EventBusCfg.java
 * @description 事件总线配置
 * @author Sixian.xie
 * @date 2019年3月14日 上午10:48:52
 */
@Slf4j
@Configuration
public class EventBusCfg implements DisposableBean {

	/**
	 * 销毁时
	 */
	@Override
	public void destroy() throws Exception {
		if (SpringStartup.beans != null) {
			for (AbstractEventBus<?> eventAbstract : SpringStartup.beans.values()) {
				EventBusHelper.unregister(eventAbstract);
			}
		}
		log.info("Startup EventBus {} destroy", SpringStartup.beans.size());
	}
}