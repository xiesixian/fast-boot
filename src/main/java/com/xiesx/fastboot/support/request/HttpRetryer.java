package com.xiesx.fastboot.support.request;

import java.util.concurrent.ExecutionException;

import com.google.common.base.Predicate;
import com.xiesx.fastboot.core.exception.RunExc;
import com.xiesx.fastboot.core.exception.RunException;
import com.xiesx.fastboot.support.retry.Attempt;
import com.xiesx.fastboot.support.retry.RetryException;
import com.xiesx.fastboot.support.retry.RetryListener;
import com.xiesx.fastboot.support.retry.Retryer;

import lombok.extern.slf4j.Slf4j;
import net.dongliu.requests.RawResponse;
import net.dongliu.requests.RequestBuilder;

/**
 * @title HttpCilent.java
 * @description 自定义Requests网络请求类实现类，基于Requests来实现（网络请求 + 动态代理 + 失败重试）
 * @author Sixian.xie
 * @date 2020-7-21 22:42:34
 */
@Slf4j
public class HttpRetryer {

    /**
     * 重试等待
     */
    public static Integer RETRY_HTTP_WAIT = 1;

    /**
     * 重试次数
     */
    public static Integer RETRY_HTTP_NUM = 3;

    /**
     * 重试限制
     */
    public static Integer RETRY_HTTP_LIMIT = 2;

    /**
     * 重试条件
     */
    public static Predicate<RawResponse> reRetryPredicate = raw -> {
        return raw.statusCode() != 200;
    };

    /**
     * 重试条件
     * 
     * @title HttpCilent.java
     * @description
     * @author Sixian.xie
     * @date 2020-8-10 18:32:46
     */
    public static RetryListener reRetryListener = new RetryListener() {

        @Override
        public <V> void onRetry(Attempt<V> attempt) {
            long number = attempt.getAttemptNumber();
            long delay = attempt.getDelaySinceFirstAttempt();
            boolean isError = attempt.hasException();
            boolean isResult = attempt.hasResult();
            if (attempt.hasException()) {
                if (attempt.getExceptionCause().getCause() instanceof RunException) {
                    RunException runException = (RunException) attempt.getExceptionCause().getCause();
                    log.warn("onException causeBy:{} {}", runException.getErrorCode(), runException.getMessage());
                } else {
                    log.warn("onException causeBy:{}", attempt.getExceptionCause().toString());
                }
            } else {
                if (attempt.hasResult()) {
                    try {
                        V result = attempt.get();
                        if (result instanceof RawResponse) {
                            log.warn("onRetry number:{} error:{} result:{} statusCode:{} delay:{}", number, isError, isResult,
                                    ((RawResponse) result).statusCode(), delay);
                        }
                    } catch (ExecutionException e) {
                        log.error("onResult exception:{}", e.getCause().toString());
                        throw new RunException(RunExc.REQUEST, "http retry error");
                    }
                }
            }
        }
    };

    public static RawResponse retry(RequestBuilder request, Retryer<RawResponse> retry) {
        try {
            return retry.call(() -> request.send());
        } catch (ExecutionException | RetryException e) {
            throw new RunException(RunExc.REQUEST, "http retry error");
        }
    }
}
