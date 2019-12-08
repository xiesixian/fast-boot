package com.xiesx.gotv.core.token.handle;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.xiesx.gotv.core.token.AuthorizationInterceptor;
import com.xiesx.gotv.core.token.annotation.AuthUser;

/**
 * 有@AuthUser注解的方法参数，注入当前登录用户
 * 
 * @author Sixian.Xie
 * @date 2018-03-19
 */
@Component
public class AuthUserHandler implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(Member.class) && parameter.hasParameterAnnotation(AuthUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest request, WebDataBinderFactory factory) throws Exception {
		// 获取用户id
		Object user_id = request.getAttribute(AuthorizationInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
		if (user_id == null) {
			return null;
		}
		// 获取用户信息
		Member member = new Member();
		member.setId(String.valueOf(user_id));
		return member.selectById();
	}
}
