package com.xiesx.gotv.core.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.xiesx.gotv.GotvStartup;

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
			GotvStartup.startup();
		}
	}
}