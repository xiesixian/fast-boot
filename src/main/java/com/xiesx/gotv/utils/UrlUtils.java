package com.xiesx.gotv.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class UrlUtils {

	/**
	 * 将map型转为请求参数型
	 * 
	 * @param map
	 * @return 请求参数字符串 @throws
	 */
	@SuppressWarnings("all")
	public static Map<Object, Object> urlParam(HttpServletRequest req) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Enumeration em = req.getParameterNames();
		while (em.hasMoreElements()) {
			String key = (String) em.nextElement();
			String value = req.getParameter(key);
			map.put(key, value);
		}
		//
		return map;
	}

	/**
	 * 将map型转为请求参数型
	 * 
	 * @param map
	 * @return 请求参数字符串 @throws
	 */
	public static String urlEncode(Map<Object, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Object, Object> i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		//
		return sb.toString();
	}
}