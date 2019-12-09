package com.xiesx.gotv.core.json;

import java.util.List;

import org.springframework.http.MediaType;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;

/**
 * @title FastJsonHelper.java
 * @description 全局配置JSON返回信息
 * @author Sixian.xie
 * @date 2018年12月24日 上午11:48:12
 */
public class FastJsonHelper {

	/**
	 * json转化
	 * 
	 * @return
	 */
	public static FastJsonHttpMessageConverter fastConverters() {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		fastConverter.setFastJsonConfig(fastJsonConfig());
		fastConverter.setSupportedMediaTypes(fastMediaTypes());
		return fastConverter;
	}

	/**
	 * json配置
	 * 
	 * @return
	 */
	public static FastJsonConfig fastJsonConfig() {
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		// 自定义输出配置
		fastJsonConfig.setSerializerFeatures(
		// // 输出空置字段
		// SerializerFeature.WriteMapNullValue,
		// // list字段如果为null，输出为[]，而不是null
		// SerializerFeature.WriteNullListAsEmpty,
		// 输出格式化
		// SerializerFeature.PrettyFormat
		// 数值字段如果为null，输出为0，而不是null
				SerializerFeature.WriteNullNumberAsZero,
				// Boolean字段如果为null，输出为false，而不是null
				SerializerFeature.WriteNullBooleanAsFalse,
				// 字符类型字段如果为null，输出为""，而不是null
				SerializerFeature.WriteNullStringAsEmpty,
				// Enum输出为枚举值
				SerializerFeature.WriteEnumUsingToString,
				// 输入格式后的日期
				SerializerFeature.WriteDateUseDateFormat,
				// 禁用循环引用
				SerializerFeature.DisableCircularReferenceDetect);
		// 日期配置
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");// 日期配置
		return fastJsonConfig;
	}

	/**
	 * json编码
	 * 
	 * @return
	 */
	public static List<MediaType> fastMediaTypes() {
		List<MediaType> fastMediaTypes = Lists.newArrayList();
		fastMediaTypes.add(MediaType.APPLICATION_JSON);
		return fastMediaTypes;
	}
}