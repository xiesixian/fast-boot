package com.xiesx.fastboot.support.request;

import java.util.concurrent.TimeUnit;

import com.xiesx.fastboot.support.retry.*;

import net.dongliu.requests.RawResponse;
import net.dongliu.requests.RequestBuilder;
import net.dongliu.requests.Requests;

/**
 * @title HttpHelper.java
 * @description
 * @author Sixian.xie
 * @date 2020-8-11 8:50:53
 */
public class RequestsHelper extends Requests {

    static Retryer<RawResponse> retry;

    static {

        retry = RetryerBuilder.<RawResponse>newBuilder()
                // 重试条件
                .retryIfException()
                // 返回指定结果时重试
                .retryIfResult(HttpRetryer.reRetryPredicate)
                // 等待策略：每次请求间隔1s
                .withWaitStrategy(WaitStrategies.fixedWait(HttpRetryer.RETRY_HTTP_WAIT, TimeUnit.SECONDS))
                // 停止策略 : 尝试请求3次
                .withStopStrategy(StopStrategies.stopAfterAttempt(HttpRetryer.RETRY_HTTP_NUM))
                // 时间限制 : 请求限制2s
                .withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(HttpRetryer.RETRY_HTTP_LIMIT, TimeUnit.SECONDS))
                // 重试监听
                .withRetryListener(HttpRetryer.reRetryListener)
                //
                .build();
    }

    public static RawResponse retry(RequestBuilder request) {
        return HttpRetryer.retry(request, retry);
    }

    public static RawResponse retry(RequestBuilder request, Retryer<RawResponse> retry) {
        return HttpRetryer.retry(request, retry);
    }
}
