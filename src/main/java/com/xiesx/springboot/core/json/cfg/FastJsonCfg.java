package com.xiesx.springboot.core.json.cfg;

import java.math.BigInteger;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
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
     * https://www.cnblogs.com/showme1942/p/7596713.html
     *
     * @return
     */
    public static FastJsonHttpMessageConverter fastConverters() {
        // 转换
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        // 序列化配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                // // 输出空置字段
                // SerializerFeature.WriteMapNullValue,
                // 字符类型字段如果为null，输出为""，而不是null
                SerializerFeature.WriteNullStringAsEmpty,
                // 数值字段如果为null，输出为0，而不是null
                SerializerFeature.WriteNullNumberAsZero,
                // Boolean字段如果为null，输出为false，而不是null
                SerializerFeature.WriteNullBooleanAsFalse,
                // list字段如果为null，输出为[]，而不是null
                SerializerFeature.WriteNullListAsEmpty,
                // Enum输出为枚举值
                SerializerFeature.WriteEnumUsingToString,
                // 输入格式后的日期
                SerializerFeature.WriteDateUseDateFormat,
                // 禁用循环引用
                SerializerFeature.DisableCircularReferenceDetect,
                // 忽略错误的get
                SerializerFeature.IgnoreErrorGetter,
                // 大数字写成文本
                SerializerFeature.WriteBigDecimalAsPlain
        // 输出格式化
        // SerializerFeature.PrettyFormat
        );
        // 日期配置
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        // 解决Long转json精度丢失的问题
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);
        // 自定义json配置
        fastConverter.setFastJsonConfig(fastJsonConfig);
        // 自定义编码配置
        List<MediaType> fastMediaTypes = Lists.newArrayList();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        return fastConverter;
    }
}
