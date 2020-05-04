package com.xiesx.springboot.support.context;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringApplicationContextAware implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringApplicationContextAware.applicationContext == null) {
			SpringApplicationContextAware.applicationContext = applicationContext;
			SpringStartup.init();
			SpringStartup.logger();
			SpringStartup.event();
			SpringStartup.schedule();
			log.info("Startup ApplicationContext completed.");
		}
	}
}