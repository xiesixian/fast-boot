package com.xiesx.springboot.support.request.impl;

import java.net.Proxy;
import java.util.Map;

/**
 * @title HttpRetryCilentService.java
 * @description 自定义网络请求类接口
 * @author Sixian.xie
 * @date 2018年12月26日 下午6:02:53
 */
public interface IHttpCilent<T> {

	// ========get========

	/**
	 * get
	 *
	 * @param url
	 * @return
	 */
	T get(String url);

	/**
	 * get
	 *
	 * @param url
	 * @param proxy
	 * @return
	 */
	T get(String url, Proxy proxy);

	/**
	 * get
	 *
	 * @param url
	 * @param params
	 * @param proxy
	 * @return
	 */
	T get(String url, Map<String, Object> params);

	/**
	 * get
	 *
	 * @param url
	 * @param params
	 * @param proxy
	 * @return
	 */
	T get(String url, Map<String, Object> params, Proxy proxy);

	// ========post========
	/**
	 * post
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	T post(String url, Map<String, Object> params);

	/**
	 * post
	 *
	 * @param url
	 * @param params
	 * @param proxy
	 * @return
	 */
	T post(String url, Map<String, Object> params, Proxy proxy);

	// ========request========
	/**
	 * request
	 *
	 * @param method
	 * @param url
	 * @param params
	 * @param proxy
	 * @return
	 */
	T request(String method, String url, Map<String, Object> params, Proxy proxy);
}
