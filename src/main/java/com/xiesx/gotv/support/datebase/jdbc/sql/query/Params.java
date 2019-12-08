package com.xiesx.gotv.support.datebase.jdbc.sql.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @title Params.java
 * @description 参数
 * @author Sixian.xie
 * @date 2019年5月20日 下午5:08:07
 */
public class Params {

	/**
	 * 参数集合
	 */
	private final List<Object> params;

	/**
	 * 静态构造
	 * 
	 * @param params
	 * @return
	 */
	public static Params create(Object... params) {
		return new Params(params);
	}

	/**
	 * 构造方法
	 * 
	 * @param params
	 */
	private Params(Object... params){
		this.params = new ArrayList<Object>();
		if (params != null && params.length > 0) {
			this.params.addAll(Arrays.asList(params));
		}
	}

	public Params add(Collection<Object> params) {
		this.params.addAll(params);
		return this;
	}

	public Params add(Params params) {
		this.params.addAll(params.params());
		return this;
	}

	public Params add(Object param) {
		if (param instanceof Collection) {
			this.params.addAll((Collection<?>) param);
		} else if (param != null && param.getClass().isArray()) {
			this.params.addAll(Arrays.asList((Object[]) param));
		} else {
			this.params.add(param);
		}
		return this;
	}

	/**
	 * 获取所有参数
	 * 
	 * @return
	 */
	public List<Object> params() {
		return this.params;
	}

	public Object[] toArray() {
		return params.toArray(new Object[0]);
	}

	/**
	 * 包裹参数值 set phone = '1234'
	 * 
	 * @param param
	 * @return
	 */
	public static String wrapQuote(String param) {
		return wrapQuote(param, "'");
	}

	public static String wrapQuote(String param, String quote) {
		quote = StringUtils.trimToEmpty(quote);
		return quote + StringUtils.trimToEmpty(param) + quote;
	}
}
