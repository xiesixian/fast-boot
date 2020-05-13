package com.xiesx.springboot.core.jpa;

import com.alibaba.fastjson.JSON;
import com.xiesx.springboot.core.jdbc.JdbcPlusEntity;

public abstract class JpaPlusEntity<T> extends JdbcPlusEntity<T> {

	/** 序列化 */
	private static final long serialVersionUID = 1L;

	public String toJSONString() {
		return JSON.toJSONString(this);
	}
}
