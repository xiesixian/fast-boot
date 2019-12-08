package com.xiesx.gotv.support.http;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.dongliu.requests.RawResponse;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @title HttpRetryCilentServiceImpl.java
 * @description 网络请求重试类，包含（网络请求 + 动态代理 + 失败重试）
 * @author Sixian.xie
 * @date 2018年12月26日 下午6:04:47
 */
public class HttpRetryCilentBase {

	private static Map<String, Object> headers;

	private static List<String> userAgents;

	/**
	 * ============这里灰常重要
	 * 判断重试的标准
	 * ============这里灰常重要
	 **/
	protected Predicate<RawResponse> reRetryPredicate = new Predicate<RawResponse>() {

		public boolean apply(RawResponse raw) {
			if (raw == null) {//当返回null
				return true;
			} else if (raw.statusCode() != 200) {//当返回不等于200时重试
				return true;
			}
			return false;
		}
	};

	static {
		//
		init_header();
		//
		init_user_agents();
	}

	/**
	 * 初始化 proxy
	 */
	private static void init_header() {
		//强制所有请求
		headers = Maps.newConcurrentMap();
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	}

	/**
	 * 初始化userAgent
	 */
	private static void init_user_agents() {
		//
		String[] uas = new String[] { "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; AcooBrowser; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)",
				"Mozilla/4.0 (compatible; MSIE 7.0; AOL 9.5; AOLBuild 4337.35; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)", "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)",
				"Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
				"Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)", "Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
				"Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5", "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.8) Gecko Fedora/1.9.0.8-1.fc10 Kazehakase/0.5.6",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.20 (KHTML, like Gecko) Chrome/19.0.1036.7 Safari/535.20",
				"Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52", };
		//
		userAgents = Lists.newArrayList(uas);
	}

	/**
	 * 获取所有header
	 * 
	 * @return
	 */
	protected static Map<String, Object> getHeaders() {
		return headers;
	}

	/**
	 * 获取所有机型
	 * 
	 * @return
	 */
	protected static List<String> getUserAgents() {
		return userAgents;
	}

	/**
	 * 获取随机单个机型
	 * 
	 * @return
	 */
	protected String getSingleRandomUserAgent() {
		return userAgents.get(new Random().nextInt(userAgents.size() - 1));
	}
}
