package com.xiesx.gotv.core.token.handle;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.xiesx.gotv.core.token.annotation.Token;
import com.xiesx.gotv.core.token.cfg.TokenCfg;

/**
 * 有@AuthUser注解的方法参数，注入当前登录用户
 * 
 * @author Sixian.Xie
 * @date 2018-03-19
 */
public class TokenMethodArgumentResolverHandler implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(CurrentToken.class) && parameter.hasParameterAnnotation(Token.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
		// 获取用户id
		Object user_id = request.getAttribute(TokenCfg.USER_KEY, RequestAttributes.SCOPE_REQUEST);
		if (user_id == null) {
			return null;
		}
		// 设置用户id
		return CurrentToken.builder().id(String.valueOf(user_id)).build();
	}
}