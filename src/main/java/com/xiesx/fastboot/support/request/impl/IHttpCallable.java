package com.xiesx.fastboot.support.request.impl;

import java.net.Proxy;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @title IHttpCallable.java
 * @description 网络重试类接口
 * @author Sixian.xie
 * @date 2020-7-21 22:41:45
 */
public interface IHttpCallable<T> {

    Callable<T> call(String method, String url, Map<String, Object> params, Proxy proxy);
}
