package com.xiesx.gotv.support.http;

import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.github.rholder.retry.RetryException;

/**
 * @title HttpRetryCilentService.java
 * @description 自定义网络请求类接口
 * @author Sixian.xie
 * @date 2018年12月26日 下午6:02:53
 */
public interface HttpRetryCilentInterface<T> {

	//========get========

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

	/**
	 * get_try
	 * 
	 * @param url
	 * @return
	 * @throws ExecutionException
	 * @throws RetryException
	 */
	T get_try(String url) throws ExecutionException, RetryException;
	
	/**
	 * get_try
	 * 
	 * @param url
	 * @param proxy
	 * @return
	 * @throws ExecutionException
	 * @throws RetryException
	 */
	T get_try(String url, Proxy proxy) throws ExecutionException, RetryException;

	/**
	 * get_try
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ExecutionException
	 * @throws RetryException
	 */
	T get_try(String url, Map<String, Object> params) throws ExecutionException, RetryException;
	
	/**
	 * get_try
	 * 
	 * @param url
	 * @param params
	 * @param proxy
	 * @return
	 * @throws ExecutionException
	 * @throws RetryException
	 */
	T get_try(String url, Map<String, Object> params, Proxy proxy) throws ExecutionException, RetryException;

	//========post========
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

	/**
	 * post_try
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws ExecutionException
	 * @throws RetryException
	 */
	T post_try(String url, Map<String, Object> params) throws ExecutionException, RetryException;
	
	/**
	 * post_try
	 * 
	 * @param url
	 * @param params
	 * @param proxy
	 * @return
	 * @throws ExecutionException
	 * @throws RetryException
	 */
	T post_try(String url, Map<String, Object> params, Proxy proxy) throws ExecutionException, RetryException;

	//========request========
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
