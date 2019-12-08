package com.xiesx.gotv.support.sgin;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.common.collect.Maps;

/**
 * 参数验签
 * 
 * @author Sixian.Xie
 * @date 2018-02-02
 */
@Component
public class SignInterceptor extends HandlerInterceptorAdapter {

	public static final String SIGN_KEY = "sign";

	public static final String SIGN_VAL = "136305973";

	@Value("${spring.profiles.active}")
	String active;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//
		Map<String, String> parms = Maps.newConcurrentMap();
		//
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = request.getParameter(name);
			parms.put(name, value);
		}
		//
		if (active.equals("release")) {
			//
			if (!parms.isEmpty()) {
				// 从header中获取sign
				String sign = request.getHeader(SIGN_KEY);
				// sign为空
				if (StringUtils.isBlank(sign)) {
					throw new RuntimeException("验签错误，非法请求");
				}
				// sign验签
				if (!SignUtils.getSignature(parms, SIGN_VAL).equals(sign)) {
					throw new RuntimeException("验签失败，非法请求");
				}
			}
		}
		return true;
	}
}