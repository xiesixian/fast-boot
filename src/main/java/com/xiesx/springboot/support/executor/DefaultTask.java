package com.xiesx.springboot.support.executor;

import java.util.concurrent.Callable;

import com.google.common.util.concurrent.FutureCallback;

import lombok.extern.slf4j.Slf4j;

/**
 * @title SCListenTask.java
 * @description 基类任务：线程池，基类任务，定义3种状态
 * @author Sixian.xie
 * @date 2019年1月3日 下午2:38:12
 */
@Slf4j
public class DefaultTask<T> implements Callable<T>, FutureCallback<T> {

    /**
     * 执行
     */
    @Override
    public T call() throws Exception {
        log.info("call.......");
        return null;
    }

    /**
     * 成功
     */
    @Override
    public void onSuccess(T t) {
        log.info("call success........");
    }

    /**
     * 失败
     */
    @Override
    public void onFailure(Throwable e) {
        log.error("call fail........", e);
    }
}
