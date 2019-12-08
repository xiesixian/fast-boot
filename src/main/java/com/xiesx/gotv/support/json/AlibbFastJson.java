package com.xiesx.gotv.support.json;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * @title AlibbFastJson.java
 * @description 全局配置JSON返回信息
 * @author Sixian.xie
 * @date 2018年12月24日 上午11:48:12
 */
public class AlibbFastJson {

	// 调试配置
	@SuppressWarnings("unused")
	private static final SerializerFeature[] debug_features = {//
	//// 输出空置字段
	//SerializerFeature.WriteMapNullValue,
	//// list字段如果为null，输出为[]，而不是null
	// SerializerFeature.WriteNullListAsEmpty,
	//// 数值字段如果为null，输出为0，而不是null
	// SerializerFeature.WriteNullNumberAsZero,
	//// Boolean字段如果为null，输出为false，而不是null
	// SerializerFeature.WriteNullBooleanAsFalse,
	//// 字符类型字段如果为null，输出为""，而不是null
	// SerializerFeature.WriteNullStringAsEmpty,
	// Enum输出为枚举值
			SerializerFeature.WriteEnumUsingToString,
			// 输入格式后的日期
			SerializerFeature.WriteDateUseDateFormat,
			// 输出格式化
			SerializerFeature.PrettyFormat };

	// 默认配置
	private static final SerializerFeature[] default_features = {//
	//// 输出空置字段
	//SerializerFeature.WriteMapNullValue,
	//// list字段如果为null，输出为[]，而不是null
	// SerializerFeature.WriteNullListAsEmpty,
	//// 数值字段如果为null，输出为0，而不是null
	// SerializerFeature.WriteNullNumberAsZero,
	//// Boolean字段如果为null，输出为false，而不是null
	// SerializerFeature.WriteNullBooleanAsFalse,
	//// 字符类型字段如果为null，输出为""，而不是null
	// SerializerFeature.WriteNullStringAsEmpty,
	// Enum输出为枚举值
			SerializerFeature.WriteEnumUsingToString,
			// 输入格式后的日期
			SerializerFeature.WriteDateUseDateFormat,
	// 输出格式化
	//SerializerFeature.PrettyFormat
	};

	/**
	 * json配置
	 * 
	 * @return
	 */
	public static FastJsonConfig fastJsonConfig() {
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(default_features);// 输出配置
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");// 日期配置
		return fastJsonConfig;
	}

	/**
	 * json编码
	 * 
	 * @return
	 */
	public static List<MediaType> fastMediaTypes() {
		// 
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		return fastMediaTypes;
	}

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
}