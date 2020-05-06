package com.xiesx.springboot.support.token.handle;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.xiesx.springboot.support.token.annotation.Token;
import com.xiesx.springboot.support.token.cfg.TokenCfg;

/**
 * @title TokenMethodArgumentResolverHandler
 * @description
 * @author XIE
 * @date 2020年4月25日下午6:15:53
 */
public class TokenMethodArgumentResolverHandler implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(CurrentToken.class)
				&& parameter.hasParameterAnnotation(Token.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request,
			WebDataBinderFactory factory) throws Exception {
		// 获取用户id
		Object user_id = request.getAttribute(TokenCfg.USER_KEY, RequestAttributes.SCOPE_REQUEST);
		if (ObjectUtils.isEmpty(user_id)) {
			return null;
		}
		// 设置用户id
		return CurrentToken.builder().id(String.valueOf(user_id)).build();
	}
}
