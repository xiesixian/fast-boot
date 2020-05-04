package com.xiesx.gotv.support.request;

import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import net.dongliu.requests.Methods;
import net.dongliu.requests.RawResponse;
import net.dongliu.requests.RequestBuilder;
import net.dongliu.requests.Requests;
import net.dongliu.requests.Session;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.xiesx.gotv.support.request.base.BaseHttpRetryCilent;
import com.xiesx.gotv.support.request.base.BaseHttpRetryListener;
import com.xiesx.gotv.support.request.config.HttpCilentRetryConfig;
import com.xiesx.gotv.support.request.impl.IHttpCallable;

/**
 * @title 自定义Requests网络请求类实现类
 * @description 基于Requests来实现（网络请求 + 动态代理 + 失败重试）
 * @author Sixian.xie
 * @date 2018年7月17日 下午2:36:42
 */
@Slf4j
@Scope("prototype")
@Component("httpCilentRetryImplRe")
public class HttpCilentRetryImplRe extends BaseHttpRetryCilent implements IHttpCilentRetry<RawResponse>, IHttpCallable<RawResponse> {

	/**
	 * ============这里灰常重要
	 * api请求是无状态的，该模块主要功能是提供可存储cookie的对象，并在后续连接请求时重新发送，避免手动去设置Cookie、Headers信息。(此公共在12306订餐用到)
	 * ============这里灰常重要
	 **/
	protected Session session = Requests.session();

	// ========get========

	@Override
	public RawResponse get(String url) {
		return request(Methods.GET, url, null, null);
	}

	@Override
	public RawResponse get(String url, Proxy proxy) {
		return request(Methods.GET, url, null, proxy);
	}

	@Override
	public RawResponse get(String url, Map<String, Object> params) {
		return request(Methods.GET, url, params, null);
	}

	@Override
	public RawResponse get(String url, Map<String, Object> params, Proxy proxy) {
		return request(Methods.GET, url, params, proxy);
	}

	@Override
	public RawResponse get_try(String url) throws ExecutionException, RetryException {
		return get_try(url, null, null);
	}

	@Override
	public RawResponse get_try(String url, Proxy proxy) throws ExecutionException, RetryException {
		return get_try(url, null, proxy);
	}

	@Override
	public RawResponse get_try(String url, Map<String, Object> params) throws ExecutionException, RetryException {
		return get_try(url, params, null);
	}

	@Override
	public RawResponse get_try(String url, Map<String, Object> params, Proxy proxy) throws ExecutionException, RetryException {
		// 构造
		Retryer<RawResponse> retry = RetryerBuilder.<RawResponse> newBuilder()
		// 抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
				.retryIfException()
				// 返回x需要重试
				.retryIfResult(reRetryPredicate)
				// 重试策略
				.withWaitStrategy(WaitStrategies.fixedWait(HttpCilentRetryConfig.RETRY_HTTP_WAIT, TimeUnit.SECONDS))
				// 尝试次数
				.withStopStrategy(StopStrategies.stopAfterAttempt(HttpCilentRetryConfig.RETRY_HTTP_NUM))
				// 重试监听
				.withRetryListener(new BaseHttpRetryListener<RawResponse>())
				.build();
		return retry.call(call(Methods.GET, url, params, proxy));
	}

	// ========post========

	@Override
	public RawResponse post(String url, Map<String, Object> params) {
		return request(Methods.POST, url, params, null);
	}

	@Override
	public RawResponse post(String url, Map<String, Object> params, Proxy proxy) {
		return request(Methods.POST, url, params, proxy);
	}

	@Override
	public RawResponse post_try(String url, Map<String, Object> params) throws ExecutionException, RetryException {
		return post_try(url, params, null);
	}

	@Override
	public RawResponse post_try(String url, Map<String, Object> params, Proxy proxy) throws ExecutionException, RetryException {
		// 构造
		Retryer<RawResponse> retry = RetryerBuilder.<RawResponse> newBuilder()
		// 抛出runtime异常、checked异常时都会重试，但是抛出error不会重试。
				.retryIfException()
				// 返回x需要重试
				.retryIfResult(reRetryPredicate)
				// 重试策略
				.withWaitStrategy(WaitStrategies.fixedWait(HttpCilentRetryConfig.RETRY_HTTP_WAIT, TimeUnit.SECONDS))
				// 尝试次数
				.withStopStrategy(StopStrategies.stopAfterAttempt(HttpCilentRetryConfig.RETRY_HTTP_NUM))
				// 重试监听
				.withRetryListener(new BaseHttpRetryListener<RawResponse>())
				.build();
		return retry.call(call(Methods.POST, url, params, proxy));
	}

	// ========request========

	@Override
	public RawResponse request(String method, String url, Map<String, Object> params, Proxy proxy) {
		log.info("{} cook: {}", method, session.currentCookies().size() + " length");
		// 请求方式
		RequestBuilder requestBuilder = session.newRequest(method, url);
		// 禁用SSL
		// requestBuilder.verify(HttpConfig.SSL_VERIFY);
		// 请求Header
		requestBuilder.headers(getHeaders());
		// 超时时间
		requestBuilder.timeout(HttpCilentRetryConfig.HTTP_CONNECT_TIMEOUT);
		requestBuilder.socksTimeout(HttpCilentRetryConfig.HTTP_SOCKS_TIMEOUT);
		// 请求UA
		requestBuilder.userAgent(getSingleRandomUserAgent());
		// 请求参数
		if (params != null) {
			if (method.equals(Methods.GET)) {
				requestBuilder.params(params);
				log.info("{} params: {}", method, JSON.toJSONString(params));
			} else if (method.equals(Methods.POST)) {
				requestBuilder.body(params);
				log.info("{} body: {}", method, JSON.toJSONString(params));
			}
		}
		// 动态代理
		if (HttpCilentRetryConfig.ISPROXY && proxy != null) {
			requestBuilder.proxy(proxy);
			log.info("{} proxy: {}", method, proxy);
		}
		// 请求获取内容
		return requestBuilder.send();
	}

	// ========call========

	@Override
	public Callable<RawResponse> call(final String method, final String url, final Map<String, Object> params, final Proxy proxy) {

		return new Callable<RawResponse>() {

			@Override
			public RawResponse call() {

				return request(method, url, params, proxy);
			}
		};
	}
}
