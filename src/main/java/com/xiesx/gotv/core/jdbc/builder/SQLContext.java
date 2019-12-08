package com.xiesx.gotv.core.jdbc.builder;

import java.util.List;

import com.alibaba.druid.sql.SQLUtils;

/**
 * @title SQLContext.java
 * @description SQL信息
 * @author Sixian.xie
 * @date 2019年1月22日 下午5:33:58
 */
public class SQLContext {

	/** sql */
	private StringBuilder sql;

	/** 主键 */
	private String primaryKey;

	/** 参数，对应sql中的?号 */
	private List<Object> params;

	// ================构造方法

	public SQLContext(StringBuilder sql, List<Object> params){
		this.sql = sql;
		this.params = params;
	}

	public SQLContext(StringBuilder sql, String primaryKey, List<Object> params){
		this.sql = sql;
		this.primaryKey = primaryKey;
		this.params = params;
	}

	// ================get/set

	public StringBuilder getSql() {
		return sql;
	}

	public void setSql(StringBuilder sql) {
		this.sql = sql;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}

	// ================输出

	/**
	 * toString
	 * 
	 * @return
	 */
	public String getSqlString() {
		return getSqlToStringFormat(false);
	}

	/**
	 * ToFormat
	 * 
	 * @return
	 */
	public String getSqlFormat() {
		return getSqlToStringFormat(true);
	}

	/**
	 * toArray
	 * 
	 * @return
	 */
	public Object[] getSqlParams() {
		if (!params.isEmpty()) {
			return params.toArray();
		}
		return new Object[0];
	}

	// ================私有

	private String getSqlToStringFormat(Boolean format) {
		return SQLUtils.formatMySql(this.sql.toString(), new SQLUtils.FormatOption(false));
	}
}