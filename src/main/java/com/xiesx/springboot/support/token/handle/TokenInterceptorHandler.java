package com.xiesx.springboot.support.token.handle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiesx.springboot.support.token.TokenStorage;
import com.xiesx.springboot.support.token.annotation.Token;
import com.xiesx.springboot.support.token.cfg.TokenCfg;

/**
 * @title TokenInterceptorHandler
 * @description 
 * @author XIE
 * @date 2020年4月25日下午6:17:04
 */
public class TokenInterceptorHandler extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 获取方法信息
		Method method = ((HandlerMethod) handler).getMethod();
		// 获取参数注解信息
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		for (Annotation[] annotation1 : parameterAnnotations) {
			for (Annotation annotation2 : annotation1) {
				if (annotation2 instanceof Token) {
					// 从header中获取token
					String token = request.getHeader("token");
					// 如果header中不存在token，则从参数中获取token
					if (StringUtils.isEmpty(token)) {
						token = request.getParameter("token");
					}
					// token为空
					if (StringUtils.isEmpty(token)) {
						throw new RuntimeException("登录信息错误，请重新登录");
					}
					// token过期
					TokenStorage tokenEntity = queryByToken(token);
					if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
						throw new RuntimeException("登录信息失效，请重新登录");
					}
					// 设置user_id到request里，后续根据user_id，获取用户信息
					request.setAttribute(TokenCfg.USER_KEY, tokenEntity.getUserId());
				}
			}
		}
		return true;
	}

	/**
	 * 查询token信息
	 * 
	 * @param token
	 * @return
	 */
	public TokenStorage queryByToken(String token) {
		return new TokenStorage().setToken(token).find();
	}
}
