package com.xiesx.gotv.support.datebase.jdbc.sql.query;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * @title SQLFields.java
 * @description 字段过滤对象
 * @author Sixian.xie
 * @date 2019年1月22日 下午6:47:52
 */
public final class Fields {

	/**
	 * 字段名称集合
	 */
	private final List<String> fields;

	/**
	 * 是否为排除字段集合
	 */
	private boolean excluded;

	/**
	 * 静态构造
	 * 
	 * @param fields
	 * @return
	 */
	public static Fields create(String... fields) {
		return new Fields(fields);
	}

	/**
	 * 构造方法
	 * 
	 * @param fields
	 */
	private Fields(String... fields){
		this.fields = Lists.newArrayList();
		if (fields != null && fields.length > 0) {
			this.fields.addAll(Arrays.asList(fields));
		}
	}

	public Fields add(String field) {
		this.fields.add(field);
		return this;
	}

	public Fields add(String field, String alias) {
		this.fields.add(field.concat(" ").concat(alias));
		return this;
	}

	public Fields add(Fields fields) {
		this.fields.addAll(fields.fields());
		this.excluded = fields.isExcluded();
		return this;
	}

	public Fields add(Collection<String> fields) {
		this.fields.addAll(fields);
		return this;
	}

	public Fields excluded(boolean excluded) {
		this.excluded = excluded;
		return this;
	}

	public boolean isExcluded() {
		return excluded;
	}

	/**
	 * 获取所有字段
	 * 
	 * @return
	 */
	public List<String> fields() {
		return this.fields;
	}
	
	public String[] toArray() {
		return fields.toArray(new String[0]);
	}
}
