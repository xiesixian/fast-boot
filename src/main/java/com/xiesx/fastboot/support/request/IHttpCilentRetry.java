package com.xiesx.fastboot.support.request;

import com.xiesx.fastboot.support.request.impl.IHttpCilent;
import com.xiesx.fastboot.support.request.impl.IHttpRetry;

/**
 * @title IHttpCilentRetry.java
 * @description 网络重试类接口
 * @author Sixian.xie
 * @date 2020-7-21 22:42:40
 */
public interface IHttpCilentRetry<T> extends IHttpCilent<T>, IHttpRetry<T> {

}
