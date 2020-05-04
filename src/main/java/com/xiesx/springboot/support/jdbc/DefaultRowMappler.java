package com.xiesx.springboot.support.jdbc;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

/**
 * @title DefaultRowMappler.java
 * @description 基于现有的返回值的做的mapper处理，其实做的好一点直接用RowMappler
 * @author Sixian.xie
 * @date 2018年12月24日 上午11:36:38
 */
public class DefaultRowMappler<T> {

	/**
	 * 查询对象
	 */
	Class<T> clazz;

	/**
	 * 构造函数
	 * 
	 * @param clazz 实体类型
	 */
	public DefaultRowMappler(Class<T> clazz){
		this.clazz = clazz;
	}

	/**
	 * 数据填充
	 * 
	 * @param map
	 * @return
	 */
	public T fillToMap(Map<String, Object> map) {
		if (map == null) {
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
	public List<T> fillToList(List<Map<String, Object>> list) {
		List<T> data = Lists.newArrayList();
		for (Map<String, Object> map : list) {
			data.add((T) fillToMap(map));
		}
		return data;
	}
}
