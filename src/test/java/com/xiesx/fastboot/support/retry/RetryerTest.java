package com.xiesx.fastboot.support.retry;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ObjectUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSON;
import com.xiesx.fastboot.base.TestRetryResponse;
import com.xiesx.fastboot.base.result.BaseResult;
import com.xiesx.fastboot.base.result.R;
import com.xiesx.fastboot.core.exception.RunExc;
import com.xiesx.fastboot.core.exception.RunException;
import com.xiesx.fastboot.support.request.RequestsHelper;

import lombok.extern.slf4j.Slf4j;
import net.dongliu.requests.Parameter;
import net.dongliu.requests.RawResponse;
import net.dongliu.requests.RequestBuilder;
import net.dongliu.requests.Requests;

/**
 * @title HttpTest.java
 * @description
 * @author Sixian.xie
 * @date 2020-8-15 14:09:29
 */
@Slf4j
public class RetryerTest {

    public final static String url = "https://api.go168.xyz/api/appConfig";

    @Test
    public void retry() {
        // 构造重试
        Retryer<BaseResult> retry = RetryerBuilder.<BaseResult>newBuilder()
                // 重试条件
                .retryIfException()
                // 返回指定结果时重试
                .retryIfResult((@Nullable BaseResult result) -> {
                    if (ObjectUtils.isEmpty(result)) {
                        return true;
                    } else if (result.getCode() == -3) {
                        return true;
                    }
                    return false;
                })
                // 等待策略：每次请求间隔1s
                .withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
                // 停止策略 : 尝试请求2次
                .withStopStrategy(StopStrategies.stopAfterAttempt(2))
                // 时间限制 : 请求限制2s
                .withAttemptTimeLimiter(AttemptTimeLimiters.fixedTimeLimit(5, TimeUnit.SECONDS))
                .withRetryListener(new RetryListener() {

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
                                    if (result instanceof BaseResult) {
                                        log.warn("onRetry number:{} error:{} result:{} statusCode:{} delay:{}", number, isError, isResult,
                                                ((BaseResult) result).getCode(), delay);
                                    }
                                } catch (ExecutionException e) {
                                    log.error("onResult exception:{}", e.getCause().toString());
                                    throw new RunException(RunExc.RETRY, "test retry");
                                }
                            }
                        }
                    }
                })
                .build();

        try {
            BaseResult result = retry.call(() -> {
                // 构造请求
                RequestBuilder req = Requests.post(url).params(Parameter.of("configKey", "appLaunch"));
                // 请求重试
                RawResponse response = RequestsHelper.retry(req);
                // 获取结果
                TestRetryResponse result1 = response.readToJson(TestRetryResponse.class);
                // 验证结果，如果结果正确则返回，错误则重试
                if (result1.getCode() == 0) {
                    return R.succ(result1.getData());
                } else {
                    return R.fail(BaseResult.RETRY, result1.getMsg());
                }
            });
            // 验证结果，如果结果正确则返回，错误则重试
            log.info(JSON.toJSONString(R.succ(result.getData())));
        } catch (ExecutionException | RetryException e) {
            throw new RunException(RunExc.RETRY, "test retry");
        }
    }
}
