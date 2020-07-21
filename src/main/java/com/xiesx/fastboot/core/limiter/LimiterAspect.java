package com.xiesx.fastboot.core.limiter;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.RateLimiter;
import com.xiesx.fastboot.core.exception.RunExc;
import com.xiesx.fastboot.core.exception.RunException;
import com.xiesx.fastboot.core.limiter.annotation.GoLimit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
@Order(-99)
public class LimiterAspect {

    private RateLimiter rateLimiter = RateLimiter.create(Double.MAX_VALUE);

    @Pointcut("@annotation(com.xiesx.fastboot.core.limiter.annotation.GoLimit)")
    public void limitPointcut() {
        log.debug("limitPointcut=====");
    }

    @Around("limitPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 获取方法信息
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // 获取注解信息
        GoLimit limit = method.getAnnotation(GoLimit.class);
        rateLimiter.setRate(limit.limit());
        // 获取令牌桶中的一个令牌，最多等待1秒
        if (rateLimiter.tryAcquire(1, 1, TimeUnit.SECONDS)) {
            return point.proceed();
        } else {
            if (StringUtils.isNotEmpty(limit.message())) {
                throw new RunException(RunExc.LIMITER, limit.message());
            }
            throw new RunException(RunExc.LIMITER);
        }
    }
}
