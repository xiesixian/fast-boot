package com.xiesx.gotv.support.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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
			SpringStartup.schedule();
		}
	}
}