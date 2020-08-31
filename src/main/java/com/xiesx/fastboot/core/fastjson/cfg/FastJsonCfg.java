package com.xiesx.fastboot.core.fastjson.cfg;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import com.xiesx.fastboot.core.fastjson.filter.DesensitizeFilter;
import com.xiesx.fastboot.core.fastjson.serializer.CustomerBigDecimalCodec;

@Configuration
@EnableConfigurationProperties(FastJsonProperties.class)
public class FastJsonCfg implements WebMvcConfigurer {

    public static SerializerFeature[] serializerFeatures = new SerializerFeature[] {
            // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteNullStringAsEmpty,
            // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullNumberAsZero,
            // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,
            // 枚举类型用ToString输出为
            SerializerFeature.WriteEnumUsingToString,
            // 是否输出值为null的字段
            // SerializerFeature.WriteMapNullValue,
            // list字段如果为null，输出为[]，而不是null
            // SerializerFeature.WriteNullListAsEmpty,
            // 输出格式后的日期
            SerializerFeature.WriteDateUseDateFormat,
            // 禁用循环引用
            SerializerFeature.DisableCircularReferenceDetect,
            // 忽略错误的get
            SerializerFeature.IgnoreErrorGetter,
            // 输出格式化
            // SerializerFeature.PrettyFormat
    };

    @Autowired
    private FastJsonProperties fastJsonProperties;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(0, fastJsonHttpMessageConverters());
    }

    public FastJsonHttpMessageConverter fastJsonHttpMessageConverters() {
        // 转换器
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // json配置
        fastConverter.setFastJsonConfig(fastJsonConfig());
        // 支持类型
        fastConverter.setSupportedMediaTypes(MediaType.parseMediaTypes(MediaType.APPLICATION_JSON_VALUE));
        return fastConverter;
    }

    public FastJsonConfig fastJsonConfig() {
        // 自定义配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        // 序列化
        fastJsonConfig.setSerializerFeatures(serializerFeatures);
        // 日期
        fastJsonConfig.setDateFormat(fastJsonProperties.getDateFormat());
        // 精度
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(BigDecimal.class, CustomerBigDecimalCodec.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);
        // 自定义拦截件
        List<SerializeFilter> filters = Lists.newArrayList();
        if (fastJsonProperties.getDesensitize()) {
            filters.add(new DesensitizeFilter()); // 脱敏拦截器
        }
        fastJsonConfig.setSerializeFilters(filters.toArray(new SerializeFilter[filters.size()]));
        return fastJsonConfig;
    }
}
