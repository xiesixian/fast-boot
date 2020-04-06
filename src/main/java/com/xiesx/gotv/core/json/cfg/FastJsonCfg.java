package com.xiesx.gotv.core.json.cfg;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;

@Configuration
public class FastJsonCfg implements WebMvcConfigurer {

	/**
	 * 消息转换
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(0, fastConverters());
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
