package com.xiesx.fastboot.support.executor;

import java.util.concurrent.Callable;

import com.google.common.util.concurrent.FutureCallback;

import lombok.extern.log4j.Log4j2;

/**
 * @title DefaultTask.java
 * @description 基类任务：线程池，基类任务，定义3种状态
 * @author Sixian.xie
 * @date 2020-7-21 22:39:54
 */
@Log4j2
public class DefaultTask<T> implements Callable<T>, FutureCallback<T> {

    /**
     * 执行
     */
    @Override
    public T call() throws Exception {
        log.debug("call.......");
        return null;
    }

    /**
     * 成功
     */
    @Override
    public void onSuccess(T t) {
        log.debug("call success........");
    }

    /**
     * 失败
     */
    @Override
    public void onFailure(Throwable e) {
        log.error("call fail........", e);
    }
}
