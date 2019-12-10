package com.xiesx.gotv.support.event.cfg;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;
import com.xiesx.gotv.core.context.SpringApplicationContextAware;
import com.xiesx.gotv.support.event.EventBusHelper;
import com.xiesx.gotv.support.event.base.AbstractEventBus;

/**
 * @title EventBusCfg.java
 * @description 事件总线配置
 * @author Sixian.xie
 * @date 2019年3月14日 上午10:48:52
 */
@Slf4j
@Configuration
public class EventBusCfg implements InitializingBean, DisposableBean {

	@SuppressWarnings("rawtypes")
	private Map<String, AbstractEventBus> beans = Maps.newConcurrentMap();

	/**
	 * 初始时
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		beans = SpringApplicationContextAware.getApplicationContext().getBeansOfType(AbstractEventBus.class);
		if (beans != null) {
			for (AbstractEventBus<?> eventAbstract : beans.values()) {
				EventBusHelper.register(eventAbstract);
			}
		}
		log.info("afterPropertiesSet:{}", beans);
	}

	/**
	 * 销毁时
	 */
	@Override
	public void destroy() throws Exception {
		if (beans != null) {
			for (AbstractEventBus<?> eventAbstract : beans.values()) {
				EventBusHelper.unregister(eventAbstract);
			}
		}
		log.info("destroy:{}", beans);
	}
}