package com.xiesx.gotv.base.jdbc;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.xiesx.gotv.core.context.SpringHelper;
import com.xiesx.gotv.support.jdbc.DefaultRowMappler;
import com.xiesx.gotv.support.jdbc.builder.SQLBuilder;
import com.xiesx.gotv.support.jdbc.builder.SQLContext;

public abstract class JdbcEntity<T> extends JdbcDaoSupport implements Serializable {

	/** 序列化 */
	private static final long serialVersionUID = 1L;

	/** 实体对象 */
	private Class<T> entityClass;

	/** 填充对象 */
	private DefaultRowMappler<T> rowMapper;

	/** 持久对象 */
	private JdbcTemplate jdbcTemplate;

	/**
	 * 获取运行时的具体实体对象
	 */
	@SuppressWarnings("unchecked")
	public JdbcEntity(){
		Type superclass = getClass().getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superclass;
		entityClass = (Class<T>) type.getActualTypeArguments()[0];
		rowMapper = new DefaultRowMappler<T>(entityClass);
		jdbcTemplate = SpringHelper.getBean(JdbcTemplate.class);
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
			return rowMapper.fillToMap(jdbcTemplate.queryForMap(sqlContext.getSqlString(), sqlContext.getSqlParams()));
		} catch (Exception e) {}
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
		return rowMapper.fillToList(jdbcTemplate.queryForList(sqlContext.getSqlString(), sqlContext.getSqlParams()));
	}

	/**
	 * 添加
	 * 
	 * @param entity
	 */
	public Integer insert() {
		SQLContext sqlContext = SQLBuilder.insert(this);
		return jdbcTemplate.update(sqlContext.getSqlString(), sqlContext.getSqlParams());
	}

	/**
	 * 更新
	 * 
	 * @param entity
	 * @return
	 */
	public Integer update() {
		SQLContext sqlContext = SQLBuilder.update(this);
		return jdbcTemplate.update(sqlContext.getSqlString(), sqlContext.getSqlParams());
	}
}
