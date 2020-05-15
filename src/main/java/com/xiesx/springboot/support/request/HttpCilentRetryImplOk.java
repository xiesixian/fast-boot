package com.xiesx.springboot.support.request;

import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import org.springframework.stereotype.Component;
import com.github.rholder.retry.RetryException;
import com.xiesx.springboot.support.request.impl.IHttpCallable;

/**
 * @title 自定义OkHttp网络请求类实现类（暂未实现）
 * @description 基于Square公司OkHttp来实现（网络请求 + 动态代理 + 失败重试）
 * @author Sixian.xie
 * @param <T>
 * @date 2018年7月17日 下午2:36:42
 */
@Component("httpCilentRetryImplOk")
public class HttpCilentRetryImplOk<T> implements IHttpCilentRetry<T>, IHttpCallable<T> {

    @Override
    public Callable<T> call(String method, String url, Map<String, Object> params, Proxy proxy) {
        return null;
    }

    @Override
    public T get(String url) {
        return null;
    }

    @Override
    public T get(String url, Proxy proxy) {
        return null;
    }

    @Override
    public T get(String url, Map<String, Object> params) {
        return null;
    }

    @Override
    public T get(String url, Map<String, Object> params, Proxy proxy) {
        return null;
    }

    @Override
    public T get_try(String url) throws ExecutionException, RetryException {
        return null;
    }

    @Override
    public T get_try(String url, Proxy proxy) throws ExecutionException, RetryException {
        return null;
    }

    @Override
    public T get_try(String url, Map<String, Object> params) throws ExecutionException, RetryException {
        return null;
    }

    @Override
    public T get_try(String url, Map<String, Object> params, Proxy proxy) throws ExecutionException, RetryException {
        return null;
    }

    @Override
    public T post(String url, Map<String, Object> params) {
        return null;
    }

    @Override
    public T post(String url, Map<String, Object> params, Proxy proxy) {
        return null;
    }

    @Override
    public T post_try(String url, Map<String, Object> params) throws ExecutionException, RetryException {
        return null;
    }

    @Override
    public T post_try(String url, Map<String, Object> params, Proxy proxy) throws ExecutionException, RetryException {
        return null;
    }

    @Override
    public T request(String method, String url, Map<String, Object> params, Proxy proxy) {
        return null;
    }
}
