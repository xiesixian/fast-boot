package com.xiesx.gotv.support.http;

import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.github.rholder.retry.RetryException;

/**
 * @title 自定义Requests网络请求类实现类（暂未实现）
 * @description 基于Apache公司HttpClient来实现（网络请求 + 动态代理 + 失败重试）
 * @author Sixian.xie
 * @date 2018年7月17日 下午2:36:42
 */
@Service("httpRetryCilentServiceImplHc")
public class HttpRetryCilentServiceImplHc<T> implements HttpRetryCilentInterface<T>, HttpRetryInterface<T> {

	@Override
	public Callable<T> call(String method, String url, Map<String, Object> params, Proxy proxy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(String url, Proxy proxy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(String url, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public T get(String url, Map<String, Object> params, Proxy proxy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get_try(String url) throws ExecutionException, RetryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get_try(String url, Proxy proxy) throws ExecutionException, RetryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get_try(String url, Map<String, Object> params) throws ExecutionException, RetryException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public T get_try(String url, Map<String, Object> params, Proxy proxy) throws ExecutionException, RetryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T post(String url, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T post(String url, Map<String, Object> params, Proxy proxy) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T post_try(String url, Map<String, Object> params) throws ExecutionException, RetryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T post_try(String url, Map<String, Object> params, Proxy proxy) throws ExecutionException, RetryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T request(String method, String url, Map<String, Object> params, Proxy proxy) {
		// TODO Auto-generated method stub
		return null;
	}
}
