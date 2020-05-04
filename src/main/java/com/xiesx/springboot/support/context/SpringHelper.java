package com.xiesx.springboot.support.context;

import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;

/**
 * spring工具类，静态工具方法获取bean
 * 
 * @author zhanghui
 * @date 2019/5/29
 */
public class SpringHelper {

	/**
	 * 通过name获取 Bean
	 * 
	 * @param name
	 * @return
	 */
	public static Object getBean(String name) {
		ApplicationContext applicationContext = SpringApplicationContextAware.getApplicationContext();
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
		ApplicationContext applicationContext = SpringApplicationContextAware.getApplicationContext();
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
		ApplicationContext applicationContext = SpringApplicationContextAware.getApplicationContext();
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
		ApplicationContext applicationContext = SpringApplicationContextAware.getApplicationContext();
		if (applicationContext != null) {
			return BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getAutowireCapableBeanFactory(), clazz, qualifier);
		}
		return null;
	}
}