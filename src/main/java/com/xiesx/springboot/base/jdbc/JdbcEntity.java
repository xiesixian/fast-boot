package com.xiesx.springboot.base.jdbc;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xiesx.springboot.core.context.SpringHelper;
import com.xiesx.springboot.support.jdbc.builder.SQLBuilder;
import com.xiesx.springboot.support.jdbc.builder.SQLContext;

public abstract class JdbcEntity<T> extends JdbcDaoSupport implements Serializable {

	/** 序列化 */
	private static final long serialVersionUID = 1L;

	/** 实体对象 */
	private Class<T> entityClass;

	/** 持久对象 */
	private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

	/**
	 * 获取运行时的具体实体对象
	 */
	@SuppressWarnings("unchecked")
	public JdbcEntity() {
		Type superclass = getClass().getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superclass;
		entityClass = (Class<T>) type.getActualTypeArguments()[0];
		mNamedParameterJdbcTemplate = SpringHelper.getBean(NamedParameterJdbcTemplate.class);
	}

	/**
	 * 单条
	 * 
	 * @param entity
	 */
	public T find() {
		return find(null);
	}

	public T find(List<String> fields) {
		try {
			SQLContext sqlContext = SQLBuilder.select(this, fields);
			return result(mNamedParameterJdbcTemplate.queryForMap(sqlContext.getSqlString(), sqlContext.getSqlParams()),
					entityClass);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 多条
	 * 
	 * @param entity
	 */
	public List<T> list() {
		return list(null);
	}

	public List<T> list(List<String> fields) {
		SQLContext sqlContext = SQLBuilder.select(this);
		return result(mNamedParameterJdbcTemplate.queryForList(sqlContext.getSqlString(), sqlContext.getSqlParams()),
				entityClass);
	}

	/**
	 * 添加
	 * 
	 * @param entity
	 */
	public Integer insert() {
		SQLContext sqlContext = SQLBuilder.insert(this);
		return mNamedParameterJdbcTemplate.update(sqlContext.getSqlString(), sqlContext.getSqlParams());
	}

	/**
	 * 更新
	 * 
	 * @param entity
	 * @return
	 */
	public Integer update() {
		SQLContext sqlContext = SQLBuilder.update(this);
		return mNamedParameterJdbcTemplate.update(sqlContext.getSqlString(), sqlContext.getSqlParams());
	}

	/**
	 * 数据填充
	 * 
	 * @param map
	 * @return
	 */
	public T result(Map<String, Object> map, Class<T> clazz) {
		if (ObjectUtils.isEmpty(map)) {
			return null;
		}
		return JSON.toJavaObject(new JSONObject(map), clazz);
	}

	/**
	 * 数据填充
	 * 
	 * @param list
	 * @return
	 */
	public List<T> result(List<Map<String, Object>> list, Class<T> clazz) {
		List<T> data = Lists.newArrayList();
		for (Map<String, Object> map : list) {
			data.add((T) result(map, clazz));
		}
		return data;
	}
}
