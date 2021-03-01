package com.xiesx.fastboot;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class SpringContextAware implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ObjectUtils.isEmpty(SpringContextAware.applicationContext)) {
            SpringContextAware.applicationContext = applicationContext;
            SpringStartup.init();
            SpringStartup.license();
            SpringStartup.scheduler();
            log.info("Startup ApplicationContext completed.");
        }
    }
}
