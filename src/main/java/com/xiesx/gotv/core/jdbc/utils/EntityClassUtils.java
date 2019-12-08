package com.xiesx.gotv.core.jdbc.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @title EntityClassUtils.java
 * @description 获取Entity对象类辅助
 * @author Sixian.xie
 * @date 2019年1月22日 下午5:22:28
 */
public class EntityClassUtils {

	/**
	 * 弱引用，BeanInfo信息需要注意垃圾回收
	 */
	private static final Map<Class<?>, BeanInfo> classCache = Collections.synchronizedMap(new WeakHashMap<Class<?>, BeanInfo>());

	/**
	 * 初始化实例
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static Object newInstance(Class<?> clazz) throws Exception {
		return clazz.newInstance();
	}

	/**
	 * 获取类本身的BeanInfo
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static BeanInfo getSelfBeanInfo(Class<?> clazz) throws Exception {
		BeanInfo beanInfo;
		if (classCache.get(clazz) == null) {
			beanInfo = Introspector.getBeanInfo(clazz, clazz.getSuperclass());
			classCache.put(clazz, beanInfo);
			Class<?> classToFlush = clazz;
			do {
				Introspector.flushFromCaches(classToFlush);
				classToFlush = classToFlush.getSuperclass();
			}
			while (classToFlush != null);
		} else {
			beanInfo = classCache.get(clazz);
		}
		return beanInfo;
	}
}