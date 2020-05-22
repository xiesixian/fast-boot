package com.xiesx.springboot.base.result;

import java.util.concurrent.ExecutionException;

import com.github.rholder.retry.Attempt;
import com.github.rholder.retry.RetryListener;

import lombok.extern.slf4j.Slf4j;

/**
 * @title BaseResultRetryListener.java
 * @description 重试监听基类（业务重试）默认监听
 * @author Sixian.xie
 * @date 2018年12月24日 下午1:16:06
 */
@Slf4j
public class BaseResultRetryListener<T> implements RetryListener {

    @Override
    public <V> void onRetry(Attempt<V> attempt) {
        if (attempt.hasException()) {
            log.warn("onException causeBy:{}", attempt.getExceptionCause().toString());
        }
        if (attempt.hasResult()) {
            try {
                V result = attempt.get();
                if (result instanceof BaseResult) {
                    log.warn("onRetry time:{} delay:{} isError:{} result:{} - {}", attempt.getAttemptNumber(),
                            attempt.getDelaySinceFirstAttempt(), attempt.hasException(), attempt.hasResult(),
                            ((BaseResult) result).getCode());
                }
            } catch (ExecutionException e) {
                log.error("onResult attempt produce exception:{}", e.getCause().toString());
            }
        }
    }
}
