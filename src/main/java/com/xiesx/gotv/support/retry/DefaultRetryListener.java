package com.xiesx.gotv.support.retry;

import java.util.concurrent.ExecutionException;

import lombok.extern.slf4j.Slf4j;
import net.dongliu.requests.RawResponse;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;
import com.xiesx.gotv.support.base.BaseResult;

/**
 * @title BaseRetryListener.java
 * @description 重试监听基类（网络重试 + 业务重试）默认监听
 * @author Sixian.xie
 * @date 2018年12月26日 下午5:55:48
 */
@Slf4j
public class DefaultRetryListener<T> implements RetryListener {

	@Override
	public <V> void onRetry(Attempt<V> attempt) {
		if (attempt.hasException()) {
			log.warn("onException causeBy:{}", attempt.getExceptionCause().toString());
		}
		if (attempt.hasResult()) {
			try {
				V result = (V) attempt.get();
				if (result instanceof RawResponse) {
					log.warn("onRetry time:{} delay:{} isError:{} result:{} - {}", attempt.getAttemptNumber(), attempt.getDelaySinceFirstAttempt(), attempt.hasException(), attempt.hasResult(), ((RawResponse) result).statusCode());
				} else if (result instanceof BaseResult) {
					log.warn("onRetry time:{} delay:{} isError:{} result:{} - {}", attempt.getAttemptNumber(), attempt.getDelaySinceFirstAttempt(), attempt.hasException(), attempt.hasResult(), ((BaseResult) result).getCode());
				}
			} catch (ExecutionException e) {
				log.error("onResult attempt produce exception:{}", e.getCause().toString());
			}
		}
	}
}
