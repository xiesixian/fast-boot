/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xiesx.springboot.support.jdbc.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiesx.springboot.base.lang.BlurObject;
import com.xiesx.springboot.base.lang.PairObject;

/**
 * 类操作相关工具
 *
 * @author 刘镇 (suninformation@163.com) on 2012-12-5 下午6:41:23
 * @version 1.0
 */
public class ClassUtils {

	private static final Log _LOG = LogFactory.getLog(ClassUtils.class);

	private static final InnerClassLoader _INNER_CLASS_LOADER = new InnerClassLoader(new URL[] {},
			ClassUtils.class.getClassLoader());

	@SuppressWarnings("rawtypes")
	private static final ConcurrentHashMap<Class<?>, ExtensionLoader> EXTENSION_LOADERS = new ConcurrentHashMap<Class<?>, ExtensionLoader>();

	/**
	 * @return 返回默认类加载器对象
	 */
	public static ClassLoader getDefaultClassLoader() {
		return _INNER_CLASS_LOADER;
	}

	/**
	 * 获得指定名称、限定接口的实现类
	 *
	 * @param <T>            接口类型
	 * @param className      实现类名
	 * @param interfaceClass 限制接口名
	 * @param callingClass   调用者
	 * @return 如果可以得到并且限定于指定实现，那么返回实例，否则为空
	 */
	public static <T> T impl(String className, Class<T> interfaceClass, Class<?> callingClass) {
		if (StringUtils.isNotBlank(className)) {
			try {
				Class<?> implClass = loadClass(className, callingClass);
				return impl(implClass, interfaceClass);
			} catch (Exception e) {
				_LOG.warn("", RuntimeUtils.unwrapThrow(e));
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T impl(Class<?> implClass, Class<T> interfaceClass) {
		if (implClass != null) {
			if (interfaceClass == null || interfaceClass.isAssignableFrom(implClass)) {
				try {
					return (T) implClass.newInstance();
				} catch (Exception e) {
					_LOG.warn("", RuntimeUtils.unwrapThrow(e));
				}
			}
		}
		return null;
	}

	/**
	 * 获得指定名称、限定接口，通过特定参数类型构造的实现类
	 *
	 * @param <T>               接口类型
	 * @param className         实现类名
	 * @param interfaceClass    限制接口名
	 * @param callingClass      调用者
	 * @param parameterTypes    构造方法参数类型集合
	 * @param initArgs          构造方法参数值集合
	 * @param allowNoSuchMethod 当发生NoSuchMethodException异常时是否输出日志
	 * @return 如果可以得到并且限定于指定实现，那么返回实例，否则为空
	 */
	public static <T> T impl(String className, Class<T> interfaceClass, Class<?> callingClass,
			Class<?>[] parameterTypes, Object[] initArgs, boolean allowNoSuchMethod) {
		if (StringUtils.isNotBlank(className)) {
			try {
				Class<?> implClass = loadClass(className, callingClass);
				return impl(implClass, interfaceClass, parameterTypes, initArgs, allowNoSuchMethod);
			} catch (Exception e) {
				_LOG.warn("", RuntimeUtils.unwrapThrow(e));
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T impl(Class<?> implClass, Class<T> interfaceClass, Class<?>[] parameterTypes, Object[] initArgs,
			boolean allowNoSuchMethod) {
		if (implClass != null) {
			if (interfaceClass == null || interfaceClass.isAssignableFrom(implClass)) {
				try {
					if (parameterTypes != null && parameterTypes.length > 0) {
						return (T) implClass.getConstructor(parameterTypes).newInstance(initArgs);
					}
					return (T) implClass.newInstance();
				} catch (Exception e) {
					boolean _flag = true;
					if (e instanceof NoSuchMethodException) {
						_flag = allowNoSuchMethod;
					}
					if (_flag) {
						_LOG.warn("", RuntimeUtils.unwrapThrow(e));
					}
				}
			}
		}
		return null;
	}

	public static Class<?> loadClass(String className, Class<?> callingClass) throws ClassNotFoundException {
		Class<?> _targetClass = null;
		try {
			try {
				_targetClass = Thread.currentThread().getContextClassLoader().loadClass(className);
			} catch (ClassNotFoundException e) {
				try {
					_targetClass = Class.forName(className, false, ClassUtils.class.getClassLoader());
				} catch (ClassNotFoundException ex) {
					try {
						_targetClass = _INNER_CLASS_LOADER.loadClass(className);
					} catch (ClassNotFoundException exc) {
						_targetClass = callingClass.getClassLoader().loadClass(className);
					}
				}
			}
		} catch (NoClassDefFoundError e) {
			_LOG.warn(e.toString());
		}
		return _targetClass;
	}

	public static <T> T loadClass(Class<T> clazz) {
		try {
			return getExtensionLoader(clazz).getExtension();
		} catch (Exception e) {
			if (_LOG.isWarnEnabled()) {
				_LOG.warn(StringUtils.EMPTY, RuntimeUtils.unwrapThrow(e));
			}
		}
		return null;
	}

	/**
	 * @param clazz 目标接口类
	 * @param <T>   目标类型
	 * @return 获取服务提供者加载器，若不存在则创建
	 * @throws Exception 可能产生的任何异常
	 * @since 2.0.7
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> ExtensionLoader<T> getExtensionLoader(final Class<T> clazz) throws Exception {
		return ReentrantLockHelper.putIfAbsentAsync(EXTENSION_LOADERS, clazz,
				new ReentrantLockHelper.ValueGetter<ExtensionLoader>() {
					@Override
					public ExtensionLoader<T> getValue() {
						return new ExtensionLoader<T>(clazz);
					}
				});
	}

	/**
	 * @param clazz 目标类
	 * @return 验证clazz是否不为空且仅是接口或类
	 * @since 2.0.7
	 */
	public static boolean isNormalClass(Class<?> clazz) {
		return clazz != null && !clazz.isArray() && !clazz.isAnnotation() && !clazz.isEnum()
				&& !clazz.isAnonymousClass();
	}

	/**
	 * @param clazz      目标类
	 * @param superClass 父类
	 * @return 判断类clazz是否是superClass类的子类对象
	 */
	public static boolean isSubclassOf(Class<?> clazz, Class<?> superClass) {
		boolean _flag = false;
		do {
			Class<?> cc = clazz.getSuperclass();
			if (cc != null) {
				if (cc.equals(superClass)) {
					_flag = true;
					break;
				} else {
					clazz = clazz.getSuperclass();
				}
			} else {
				break;
			}
		} while ((clazz != null && clazz != Object.class));
		return _flag;
	}

	/**
	 * @param clazz          目标对象
	 * @param interfaceClass 接口类型
	 * @return 判断clazz类中是否实现了interfaceClass接口
	 */
	public static boolean isInterfaceOf(Class<?> clazz, Class<?> interfaceClass) {
		boolean _flag = false;
		do {
			for (Class<?> cc : clazz.getInterfaces()) {
				if (cc.equals(interfaceClass)) {
					_flag = true;
				}
			}
			clazz = clazz.getSuperclass();
		} while (!_flag && (clazz != null && clazz != Object.class));
		return _flag;
	}

	/**
	 * @param target          目标对象，即可以是Field对象、Method对象或是Class对象
	 * @param annotationClass 注解类对象
	 * @return 判断target对象是否存在annotationClass注解
	 */
	public static boolean isAnnotationOf(Object target, Class<? extends Annotation> annotationClass) {
		if (target instanceof Field) {
			return ((Field) target).isAnnotationPresent(annotationClass);
		} else if (target instanceof Method) {
			return ((Method) target).isAnnotationPresent(annotationClass);
		} else if (target instanceof Class) {
			return ((Class<?>) target).isAnnotationPresent(annotationClass);
		}
		return false;
	}

	/**
	 * @param target          目标类对象
	 * @param annotationClass 注解类对象
	 * @param <A>             注解类型
	 * @return 尝试获取目标类上声明的注解, 若目标类为代理类则尝试去除代理
	 */
	public static <A extends Annotation> A getAnnotation(Object target, Class<A> annotationClass) {
		A _annotation = target.getClass().getAnnotation(annotationClass);
		if (_annotation == null && StringUtils.contains(target.getClass().getName(), "$$")) {
			try {
				Class<?> _clazz = loadClass(StringUtils.substringBefore(target.getClass().getName(), "$$"),
						target.getClass());
				if (_clazz != null) {
					_annotation = _clazz.getAnnotation(annotationClass);
				}
			} catch (ClassNotFoundException ignored) {
			}
		}
		return _annotation;
	}

	/**
	 * @param clazz 类型
	 * @return 返回类中实现的接口名称集合
	 */
	public static String[] getInterfaceNames(Class<?> clazz) {
		Class<?>[] interfaces = clazz.getInterfaces();
		List<String> names = new ArrayList<String>();
		for (Class<?> i : interfaces) {
			names.add(i.getName());
		}
		return names.toArray(new String[0]);
	}

	/**
	 * @param clazz 类对象
	 * @return 获取泛型的数据类型集合，注：不适用于泛型嵌套, 即泛型里若包含泛型则返回此泛型的RawType类型
	 */
	public static List<Class<?>> getParameterizedTypes(Class<?> clazz) {
		List<Class<?>> _clazzs = new ArrayList<Class<?>>();
		Type _types = clazz.getGenericSuperclass();
		if (ParameterizedType.class.isAssignableFrom(_types.getClass())) {
			for (Type _type : ((ParameterizedType) _types).getActualTypeArguments()) {
				if (ParameterizedType.class.isAssignableFrom(_type.getClass())) {
					_clazzs.add((Class<?>) ((ParameterizedType) _type).getRawType());
				} else {
					_clazzs.add((Class<?>) _type);
				}
			}
		} else {
			_clazzs.add((Class<?>) _types);
		}
		return _clazzs;
	}

	/**
	 * 获取clazz指定的类对象所有的Field对象（若包含其父类对象，直至其父类为空）
	 *
	 * @param clazz  目标类
	 * @param parent 是否包含其父类对象
	 * @return Field对象集合
	 */
	public static List<Field> getFields(Class<?> clazz, boolean parent) {
		List<Field> fieldList = new ArrayList<Field>();
		Class<?> _clazz = clazz;
		while (_clazz != null) {
			fieldList.addAll(Arrays.asList(_clazz.getDeclaredFields()));
			if (parent) {
				_clazz = _clazz.getSuperclass();
			} else {
				_clazz = null;
			}
		}
		return fieldList;
	}

	/**
	 * @param <A>             注解类型
	 * @param clazz           目标类
	 * @param annotationClazz 目标注解类
	 * @return 获取clazz类中成员声明的所有annotationClazz注解
	 */
	public static <A extends Annotation> List<PairObject<Field, A>> getFieldAnnotations(Class<?> clazz,
			Class<A> annotationClazz) {
		List<PairObject<Field, A>> _annotations = new ArrayList<PairObject<Field, A>>();
		for (Field _field : ClassUtils.getFields(clazz, true)) {
			A _annotation = _field.getAnnotation(annotationClazz);
			if (_annotation != null) {
				_annotations.add(new PairObject<Field, A>(_field, _annotation));
			}
		}
		return _annotations;
	}

	/**
	 * @param <A>             注解类型
	 * @param clazz           目标类
	 * @param annotationClazz 目标注解类
	 * @return 获取clazz类中成员声明的第一个annotationClazz注解
	 */
	public static <A extends Annotation> PairObject<Field, A> getFieldAnnotationFirst(Class<?> clazz,
			Class<A> annotationClazz) {
		PairObject<Field, A> _returnAnno = null;
		for (Field _field : ClassUtils.getFields(clazz, true)) {
			A _annotation = _field.getAnnotation(annotationClazz);
			if (_annotation != null) {
				_returnAnno = new PairObject<Field, A>(_field, _annotation);
				break;
			}
		}
		return _returnAnno;
	}

	/**
	 * @param method 目标方法
	 * @return 获取方法的参数名集合，若找不到则返回元素数量为0的空数组
	 */
//    public static String[] getMethodParamNames(final Method method) {
//        return new AdaptiveParanamer().lookupParameterNames(method, false);
//    }

	/**
	 * @param clazz 数组类型
	 * @return 返回数组元素类型
	 */
	public static Class<?> getArrayClassType(Class<?> clazz) {
		try {
			return Class.forName(StringUtils.substringBetween(clazz.getName(), "[L", ";"));
		} catch (ClassNotFoundException e) {
			_LOG.warn("", RuntimeUtils.unwrapThrow(e));
		}
		return null;
	}

	/**
	 * @param <T>   目标类型
	 * @param clazz 目标类型
	 * @return 创建一个类对象实例，包裹它并赋予其简单对象属性操作能力，可能返回空
	 */
	public static <T> BeanWrapper<T> wrapper(Class<T> clazz) {
		try {
			return wrapper(clazz.newInstance());
		} catch (Exception e) {
			_LOG.warn("", RuntimeUtils.unwrapThrow(e));
		}
		return null;
	}

	public static class InnerClassLoader extends URLClassLoader {

		InnerClassLoader(URL[] urls, ClassLoader parent) {
			super(urls, parent);
		}

		@Override
		public void addURL(URL url) {
			super.addURL(url);
		}

	}

	/**
	 * 服务提供者加载器
	 *
	 * @since 2.0.7
	 */
	public static class ExtensionLoader<T> {

		private final List<Class<T>> classesCache = new ArrayList<Class<T>>();

		private final Map<String, T> instancesCache = new ConcurrentHashMap<String, T>();

		@SuppressWarnings("unchecked")
		ExtensionLoader(Class<T> clazz) {
			if (clazz == null) {
				throw new NullPointerException("clazz");
			}
			if (!clazz.isInterface()) {
				throw new IllegalArgumentException(
						String.format("Class type [%s] is not a interface.", clazz.getName()));
			}
			try {
				Iterator<URL> resources = ResourceUtils
						.getResources(String.format("META-INF/services/%s", clazz.getName()), clazz, true);
				while (resources.hasNext()) {
					InputStream inputStream = null;
					BufferedReader reader = null;
					try {
						inputStream = resources.next().openStream();
						if (inputStream != null) {
							reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
							String lineStr;
							do {
								lineStr = reader.readLine();
								if (StringUtils.isNotBlank(lineStr)) {
									lineStr = StringUtils.trim(lineStr);
									if (!StringUtils.startsWith(lineStr, "#")) {
										try {
											Class<T> loadedClass = (Class<T>) loadClass(lineStr, clazz);
											if (ClassUtils.isNormalClass(loadedClass)
													&& !classesCache.contains(loadedClass)) {
												classesCache.add(loadedClass);
											}
										} catch (ClassNotFoundException e) {
											if (_LOG.isWarnEnabled()) {
												_LOG.warn(StringUtils.EMPTY, RuntimeUtils.unwrapThrow(e));
											}
										}
									}
								}
							} while (lineStr != null);
						}
					} finally {
						IOUtils.closeQuietly(inputStream);
						IOUtils.closeQuietly(reader);
					}
				}
			} catch (IOException e) {
				if (_LOG.isWarnEnabled()) {
					_LOG.warn(StringUtils.EMPTY, RuntimeUtils.unwrapThrow(e));
				}
			}
		}

		public Class<T> getExtensionClass() {
			return !classesCache.isEmpty() ? classesCache.get(0) : null;
		}

		public List<Class<T>> getExtensionClasses() {
			return Collections.unmodifiableList(classesCache);
		}

		public T getExtension() {
			return getExtension(getExtensionClass());
		}

		private T getExtension(Class<T> clazz) {
			try {
				if (clazz != null) {
					synchronized (instancesCache) {
						return ReentrantLockHelper.putIfAbsent((ConcurrentMap<String, T>) instancesCache,
								clazz.getName(), clazz.newInstance());
					}
				}
			} catch (Exception e) {
				if (_LOG.isWarnEnabled()) {
					_LOG.warn(StringUtils.EMPTY, RuntimeUtils.unwrapThrow(e));
				}
			}
			return null;
		}

		public List<T> getExtensions() {
			List<Class<T>> extClasses = getExtensionClasses();
			if (!extClasses.isEmpty()) {
				List<T> list = new ArrayList<T>();
				for (Class<T> extClass : extClasses) {
					T extension = getExtension(extClass);
					list.add(extension);
				}
				return list;
			}
			return Collections.emptyList();
		}
	}

	/**
	 * @param <T>    目标类型
	 * @param target 目标类对象
	 * @return 包裹它并赋予其简单对象属性操作能力，可能返回空
	 */
	public static <T> BeanWrapper<T> wrapper(T target) {
		return new BeanWrapper<T>(target);
	}

	/**
	 * 类成员属性过滤器接口
	 */
	public interface IFieldValueFilter {

		/**
		 * @param fieldName  成员属性名称
		 * @param fieldValue 属性值对象
		 * @return 若返回true则该属性将被忽略
		 */
		boolean filter(String fieldName, Object fieldValue);
	}

	/**
	 * 类对象包裹器，赋予对象简单的属性操作能力
	 *
	 * @param <T> 对象类型
	 * @author 刘镇 (suninformation@163.com) on 2012-12-23 上午12:46:50
	 * @version 1.0
	 */
	public static class BeanWrapper<T> {

		private T target;

		private Map<String, Field> _fields;

		protected BeanWrapper(T target) {
			this.target = target;
			this._fields = new LinkedHashMap<String, Field>();
			//
			for (Field _field : ClassUtils.getFields(target.getClass(), true)) {
				if (Modifier.isStatic(_field.getModifiers())) {
					// 忽略静态成员
					continue;
				}
				_field.setAccessible(true);
				this._fields.put(_field.getName(), _field);
			}
		}

		public T getTargetObject() {
			return target;
		}

		public Set<String> getFieldNames() {
			return _fields.keySet();
		}

		public Annotation[] getFieldAnnotations(String fieldName) {
			return _fields.get(fieldName).getAnnotations();
		}

		public Collection<Field> getFields() {
			return _fields.values();
		}

		public Field getField(String fieldName) {
			return _fields.get(fieldName);
		}

		public Class<?> getFieldType(String fieldName) {
			return _fields.get(fieldName).getType();
		}

		public BeanWrapper<T> setValue(String fieldName, Object value) throws IllegalAccessException {
			_fields.get(fieldName).set(target, value);
			return this;
		}

		public BeanWrapper<T> setValue(Field field, Object value) throws IllegalAccessException {
			field.set(target, value);
			return this;
		}

		public Object getValue(String fieldName) throws IllegalAccessException {
			return _fields.get(fieldName).get(target);
		}

		public Object getValue(Field field) throws IllegalAccessException {
			return field.get(target);
		}

		public BeanWrapper<T> fromMap(Map<String, Object> map) {
			for (Map.Entry<String, Object> _entry : map.entrySet()) {
				try {
					setValue(_entry.getKey(), _entry.getValue());
				} catch (Exception ignored) {
					// 当赋值发生异常时，忽略当前值
				}
			}
			return this;
		}

		public BeanWrapper<T> fromMap(Map<String, Object> map, IFieldValueFilter filter) {
			for (Map.Entry<String, Object> _entry : map.entrySet()) {
				try {
					if (filter != null && filter.filter(_entry.getKey(), _entry.getValue())) {
						continue;
					}
					setValue(_entry.getKey(), _entry.getValue());
				} catch (Exception ignored) {
					// 当赋值发生异常时，忽略当前值
				}
			}
			return this;
		}

		public Map<String, Object> toMap() {
			Map<String, Object> _returnValues = new HashMap<String, Object>();
			for (Field _field : _fields.values()) {
				try {
					_returnValues.put(_field.getName(), getValue(_field.getName()));
				} catch (Exception ignored) {
					// 当赋值发生异常时，忽略当前值
				}
			}
			return _returnValues;
		}

		public Map<String, Object> toMap(IFieldValueFilter filter) {
			Map<String, Object> _returnValues = new HashMap<String, Object>();
			for (Field _field : _fields.values()) {
				try {
					Object _fValue = getValue(_field.getName());
					if (filter != null && filter.filter(_field.getName(), _fValue)) {
						continue;
					}
					_returnValues.put(_field.getName(), _fValue);
				} catch (Exception ignored) {
					// 当赋值发生异常时，忽略当前值
				}
			}
			return _returnValues;
		}

		/**
		 * @param dist 目标对象
		 * @param <D>  目标对象类型
		 * @return 拷贝当前对象的成员属性值到目标对象
		 */
		public <D> D duplicate(D dist) {
			return duplicate(dist, null);
		}

		/**
		 * @param dist   目标对象
		 * @param filter 类成员属性过滤器
		 * @param <D>    目标对象类型
		 * @return 拷贝当前对象的成员属性值到目标对象
		 */
		public <D> D duplicate(D dist, IFieldValueFilter filter) {
			BeanWrapper<D> _wrapDist = wrapper(dist);
			for (String _fieldName : getFieldNames()) {
				if (_wrapDist.getFieldNames().contains(_fieldName)) {
					Object _fValue = null;
					try {
						_fValue = getValue(_fieldName);
						if (filter != null && filter.filter(_fieldName, _fValue)) {
							continue;
						}
						_wrapDist.setValue(_fieldName, _fValue);
					} catch (Exception e) {
						// 当首次赋值发生异常时，若成员变量值不为NULL则尝试转换一下
						if (_fValue != null) {
							try {
								_wrapDist.setValue(_fieldName,
										BlurObject.bind(_fValue).toObjectValue(_wrapDist.getFieldType(_fieldName)));
							} catch (Exception ignored) {
								// 当再次赋值发生异常时，彻底忽略当前值，不中断整个拷贝过程
							}
						}
					}
				}
			}
			return _wrapDist.getTargetObject();
		}

	}

}