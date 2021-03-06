package com.xiesx.fastboot;

import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;

/**
 * @title SpringHelper.java
 * @description Spring工具类，静态工具方法获取bean
 * @author Sixian.xie
 * @date 2020-7-21 22:46:54
 */
public class SpringHelper {

    /**
     * applicationContext
     *
     * @return
     */
    public static ApplicationContext getContext() {
        ApplicationContext applicationContext = SpringContextAware.getApplicationContext();
        if (applicationContext != null) {
            return applicationContext;
        }
        return null;
    }

    /**
     * 通过name获取 Bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        ApplicationContext applicationContext = SpringContextAware.getApplicationContext();
        if (applicationContext != null) {
            return applicationContext.getBean(name);
        }
        return null;
    }

    /**
     * 通过class获取Bean
     *
     * @param clazz
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        ApplicationContext applicationContext = SpringContextAware.getApplicationContext();
        if (applicationContext != null) {
            return applicationContext.getBean(clazz);
        }
        return null;
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        ApplicationContext applicationContext = SpringContextAware.getApplicationContext();
        if (applicationContext != null) {
            return applicationContext.getBean(name, clazz);
        }
        return null;
    }

    /**
     * 通过class，Qulifier值取同类型的某个bean
     *
     * @param clazz
     * @param qualifier
     * @return
     */
    public static <T> T getBean(Class<T> clazz, String qualifier) {
        ApplicationContext applicationContext = SpringContextAware.getApplicationContext();
        if (applicationContext != null) {
            return BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getAutowireCapableBeanFactory(), clazz, qualifier);
        }
        return null;
    }
}
