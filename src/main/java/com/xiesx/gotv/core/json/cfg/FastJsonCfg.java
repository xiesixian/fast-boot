package com.xiesx.gotv.core.json.cfg;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.xiesx.gotv.core.json.FastJsonHelper;

@Configuration
public class FastJsonCfg implements WebMvcConfigurer {

	/**
	 * 消息转换
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(0, FastJsonHelper.fastConverters());
	}
}
