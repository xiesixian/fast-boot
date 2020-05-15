package com.xiesx.springboot.support.request.impl;

import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @title 网络重试类接口
 * @description
 * @author Sixian.xie
 * @date 2018年7月17日 下午2:36:42
 */
public interface IHttpCallable<T> {

    Callable<T> call(String method, String url, Map<String, Object> params, Proxy proxy);
}
