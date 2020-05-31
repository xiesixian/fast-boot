package com.xiesx.fastboot.support.request;

import com.xiesx.fastboot.support.request.impl.IHttpCilent;
import com.xiesx.fastboot.support.request.impl.IHttpRetry;

/**
 * @title 网络重试类接口
 * @description
 * @author Sixian.xie
 * @date 2018年7月17日 下午2:36:42
 */
public interface IHttpCilentRetry<T> extends IHttpCilent<T>, IHttpRetry<T> {

}
