package com.xiesx.springboot.core.context;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SpringApplicationContextAware implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ObjectUtils.isEmpty(SpringApplicationContextAware.applicationContext)) {
            SpringApplicationContextAware.applicationContext = applicationContext;
            SpringStartup.license();
            SpringStartup.init();
            SpringStartup.event();
            SpringStartup.schedule();
            log.info("Startup ApplicationContext completed.");
        }
    }
}
