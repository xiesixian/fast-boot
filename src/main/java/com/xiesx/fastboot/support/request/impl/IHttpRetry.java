package com.xiesx.fastboot.support.request.impl;

import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.github.rholder.retry.RetryException;

/**
 * @title IHttpRetry.java
 * @description 自定义网络请求类接口
 * @author Sixian.xie
 * @date 2020-7-21 22:41:57
 */
public interface IHttpRetry<T> {

    // ========get========

    /**
     * get_try
     *
     * @param url
     * @return
     * @throws ExecutionException
     * @throws RetryException
     */
    T get_try(String url) throws ExecutionException, RetryException;

    /**
     * get_try
     *
     * @param url
     * @param proxy
     * @return
     * @throws ExecutionException
     * @throws RetryException
     */
    T get_try(String url, Proxy proxy) throws ExecutionException, RetryException;

    /**
     * get_try
     *
     * @param url
     * @param params
     * @return
     * @throws ExecutionException
     * @throws RetryException
     */
    T get_try(String url, Map<String, Object> params) throws ExecutionException, RetryException;

    /**
     * get_try
     *
     * @param url
     * @param params
     * @param proxy
     * @return
     * @throws ExecutionException
     * @throws RetryException
     */
    T get_try(String url, Map<String, Object> params, Proxy proxy) throws ExecutionException, RetryException;

    // ========post========

    /**
     * post_try
     *
     * @param url
     * @param params
     * @return
     * @throws ExecutionException
     * @throws RetryException
     */
    T post_try(String url, Map<String, Object> params) throws ExecutionException, RetryException;

    /**
     * post_try
     *
     * @param url
     * @param params
     * @param proxy
     * @return
     * @throws ExecutionException
     * @throws RetryException
     */
    T post_try(String url, Map<String, Object> params, Proxy proxy) throws ExecutionException, RetryException;

    // ========request========
    /**
     * request
     *
     * @param method
     * @param url
     * @param params
     * @param proxy
     * @return
     */
    T request(String method, String url, Map<String, Object> params, Proxy proxy);
}
