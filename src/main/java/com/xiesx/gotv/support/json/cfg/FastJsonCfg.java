package com.xiesx.gotv.support.json.cfg;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xiesx.gotv.support.json.FastJsonHelper;

@Configuration
public class FastJsonCfg implements WebMvcConfigurer {

	/**
	 * 消息转换
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
		while (iterator.hasNext()) {
			HttpMessageConverter<?> converter = iterator.next();
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				iterator.remove();
			}
		}
		converters.add(FastJsonHelper.fastConverters());
	}
}
