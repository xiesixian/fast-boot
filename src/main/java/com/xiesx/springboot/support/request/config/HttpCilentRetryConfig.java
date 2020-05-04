package com.xiesx.springboot.support.request.config;

/**
 * @title HttpConfig.java
 * @description Http相关配置
 * @author Sixian.xie
 * @date 2018年12月26日 下午5:57:55
 */
public class HttpCilentRetryConfig {

	/**
	 * 是否使用代理
	 */
	public static final Boolean ISPROXY = true;

	/**
	 * 链接超时（毫秒）
	 */
	public static final int HTTP_CONNECT_TIMEOUT = 6 * 1000;

	/**
	 * 会话超时（毫秒）
	 */
	public static final int HTTP_SOCKS_TIMEOUT = 10 * 1000;

	/**
	 * 重试等待（秒）
	 */
	public static final Integer RETRY_HTTP_WAIT = 3;

	/**
	 * 重试次数
	 */
	public static final Integer RETRY_HTTP_NUM = 3;

	/**
	 * 证书
	 */
	public static final boolean SSL_VERIFY = false;
}
