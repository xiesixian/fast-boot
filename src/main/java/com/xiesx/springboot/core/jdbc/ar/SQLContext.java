package com.xiesx.springboot.core.jdbc.ar;

import java.util.Map;

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
	private Map<String, Object> params;

	// ================构造方法

	public SQLContext(StringBuilder sql, Map<String, Object> params) {
		this.sql = sql;
		this.params = params;
	}

	public SQLContext(StringBuilder sql, String primaryKey, Map<String, Object> params) {
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

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
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
	public Map<String, Object> getSqlParams() {
		return params;
	}

	// ================私有

	private String getSqlToStringFormat(Boolean format) {
		if (format) {
			// return SQLUtils.formatMySql(this.sql.toString(), new
			// SQLUtils.FormatOption(false));
			return this.sql.toString();
		} else {
			return this.sql.toString();
		}
	}
}