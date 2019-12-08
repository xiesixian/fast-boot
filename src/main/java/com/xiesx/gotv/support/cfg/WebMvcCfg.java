package com.xiesx.gotv.support.cfg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.xiesx.gotv.support.json.AlibbFastJson;
import com.xiesx.gotv.support.sgin.SignInterceptor;
import com.xiesx.gotv.support.token.AuthorizationInterceptor;
import com.xiesx.gotv.support.token.handle.AuthUserHandler;

@Configuration
public class WebMvcCfg extends WebMvcConfigurationSupport {

	@Autowired
	private SignInterceptor signInterceptor;

	@Autowired
	private AuthorizationInterceptor authorizationInterceptor;

	@Autowired
	private AuthUserHandler loginUserHandlerMethodArgumentResolver;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 签名拦截
		registry.addInterceptor(signInterceptor).addPathPatterns("/member/**");// 拦截会员
		registry.addInterceptor(signInterceptor).addPathPatterns("/vod/**");// 拦截影视
		registry.addInterceptor(signInterceptor).addPathPatterns("/dwz/**");// 拦截短网址
		registry.addInterceptor(signInterceptor).addPathPatterns("/crack/**");// 拦截破破解
		// 授权拦截
		registry.addInterceptor(authorizationInterceptor).addPathPatterns("/member/**");// 拦截会员
		registry.addInterceptor(authorizationInterceptor).addPathPatterns("/vod/**");// 拦截影视
		registry.addInterceptor(authorizationInterceptor).addPathPatterns("/dwz/**");// 拦截短网址
		registry.addInterceptor(authorizationInterceptor).addPathPatterns("/crack/**");// 拦截破破解
		super.addInterceptors(registry);
	}

	/**
	 * 异步支持
	 */
	@Override
	public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
		configurer.setDefaultTimeout(20000);
		configurer.registerCallableInterceptors(timeoutInterceptor());
	}

	/**
	 * 超时处理
	 * 
	 * @return
	 */
	@Bean
	public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
		return new TimeoutCallableProcessingInterceptor();
	}

	/**
	 * 消息转换
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(AlibbFastJson.fastConverters());
	}
}
