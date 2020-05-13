package com.xiesx.springboot.core.jdbc;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @title JdbcPlusTemplate
 * @description https://blog.csdn.net/hbtj_1216/article/details/100532763?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-1.3f686c96b88b49ab&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-1.3f686c96b88b49ab
 * @author XIE
 * @date 2020年4月24日下午10:01:05
 */
@Slf4j
@Component
public class JdbcPlusTemplate {

	/** 持久对象 */
	@Autowired
	private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

	public NamedParameterJdbcTemplate getJdbcPlusTemplate() {
		return mNamedParameterJdbcTemplate;
	}

	/**
	 * 查询Map
	 *
	 * @param sql
	 * @param args
	 * @return
	 */
	public Map<String, Object> queryForMap(String sql, Object... args) {
		try {
			return mNamedParameterJdbcTemplate.getJdbcTemplate().queryForMap(sql, args);
		} catch (Exception e) {
			log.error("queryForMap error error", e);
			return null;
		}
	}

	/**
	 * 查询List<Map>
	 *
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<Map<String, Object>> queryForList(String sql, Object... args) {
		try {
			return mNamedParameterJdbcTemplate.getJdbcTemplate().queryForList(sql, args);
		} catch (Exception e) {
			log.error("queryForList error", e);
			return null;
		}
	}

	/**
	 * 查询Obj
	 *
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param clazz
	 * @return
	 */
	public <T> T queryForMap(String sql, Class<T> clazz) {
		try {
			return result(mNamedParameterJdbcTemplate.getJdbcTemplate().queryForMap(sql), clazz);
		} catch (Exception e) {
			log.error("queryForMap error", e);
			return null;
		}
	}

	public <T> T queryForMap(String sql, Object[] args, Class<T> clazz) {
		try {
			return result(mNamedParameterJdbcTemplate.getJdbcTemplate().queryForMap(sql, args), clazz);
		} catch (Exception e) {
			log.error("queryForMap error", e);
			return null;
		}
	}

	/**
	 * 查询List<Obj>
	 *
	 * @param <T>
	 * @param sql
	 * @param args
	 * @param clazz
	 * @return
	 */
	public <T> List<T> queryForList(String sql, Class<T> clazz) {
		try {
			return result(mNamedParameterJdbcTemplate.getJdbcTemplate().queryForList(sql), clazz);
		} catch (Exception e) {
			log.error("queryForList error", e);
			return null;
		}
	}

	public <T> List<T> queryForList(String sql, Object[] args, Class<T> clazz) {
		try {
			return result(mNamedParameterJdbcTemplate.getJdbcTemplate().queryForList(sql, args), clazz);
		} catch (Exception e) {
			log.error("queryForList error", e);
			return null;
		}
	}

	/**
	 * 执行update
	 *
	 * @param sql
	 * @param args
	 * @return
	 */
	public int update(String sql, @Nullable Object... args) {
		try {
			return mNamedParameterJdbcTemplate.getJdbcTemplate().update(sql, args);
		} catch (Exception e) {
			log.error("update error", e.getMessage());
			return 0;
		}
	}

	/**
	 * 执行update
	 *
	 * @param sql
	 * @param args
	 * @return
	 */
	public int update(String sql, @Nullable Map<String, ?> paramMap) {
		try {
			return mNamedParameterJdbcTemplate.update(sql, paramMap);
		} catch (Exception e) {
			log.error("update error", e);
			return 0;
		}
	}

	/**
	 * 数据填充
	 *
	 * @param map
	 * @return
	 */
	protected <T> T result(Map<String, Object> map, Class<T> clazz) {
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
	protected <T> List<T> result(List<Map<String, Object>> list, Class<T> clazz) {
		List<T> data = Lists.newArrayList();
		for (Map<String, Object> map : list) {
			data.add(result(map, clazz));
		}
		return data;
	}
}
