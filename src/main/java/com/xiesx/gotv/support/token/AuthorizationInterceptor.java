package com.xiesx.gotv.support.token;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiesx.gotv.support.token.annotation.AuthIgnore;

/**
 * 权限(Token)验证
 * 
 * @author theodo
 * @email 36780272@qq.com
 * @date 2017-03-23 15:38
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private AuthTokenService tokenValidateService;

	public static final String USER_KEY = "user_id";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		AuthIgnore annotation;
		if (handler instanceof HandlerMethod) {
			annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
		} else {
			return true;
		}
		// 如果有@AuthIgnore注解，则不验证token
		if (annotation != null) {
			return true;
		}
		// 从header中获取token
		String token = request.getHeader("token");
		// 如果header中不存在token，则从参数中获取token
		if (StringUtils.isBlank(token)) {
			token = request.getParameter("token");
		}
		// token为空
		if (StringUtils.isBlank(token)) {
			throw new RuntimeException("登录信息错误，请重新登录");
		}
		// token过期
		AuthToken tokenEntity = tokenValidateService.queryByToken(token);
		if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
			throw new RuntimeException("登录信息失效，请重新登录");
		}
		// 设置user_id到request里，后续根据user_id，获取用户信息
		request.setAttribute(USER_KEY, tokenEntity.getUserId());
		return true;
	}
}
