package com.xiesx.gotv.support.jdbc.utils;

import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;

/**
 * @title SimpleNameHandler.java
 * @description 默认处理器
 * @author Sixian.xie
 * @date 2019年1月22日 下午5:23:23
 */
@Slf4j
public class EntityNameHandler {

	/**
	 * 获得表名
	 * 
	 * @param clazz
	 * @return
	 */
	public String getTableName(Class<?> clazz) {
		Table annotation = (Table) clazz.getAnnotation(Table.class);
		if (annotation != null) {
			log.debug("getTableName:{}", annotation.name());
			return annotation.name();
		}
		return null;
	}

	/**
	 * 获取主键名
	 * 
	 * @param clazz
	 * @return
	 */
	public String getPrimaryName(Class<?> clazz) {
		try {
			// 访问私有变量前：setAccessible(true)
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
			}
			// 循环@Id的字段
			for (int i = 0; i < fields.length; i++) {
				Field field = clazz.getDeclaredField(fields[i].getName());
				Id id = field.getAnnotation(Id.class);
				if (id != null) {
					return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
				}
			}
		} catch (Exception e) {
			log.error("getPrimaryName error:{}", e);
			return null;
		}
		return null;
	}

	/**
	 * 获取列名
	 * 
	 * @param clazz
	 * @param field
	 * @return
	 */
	public String getColumnName(Class<?> clazz, String field) {
		try {
			Field f = clazz.getDeclaredField(field);
			f.setAccessible(true);
			Column column = f.getAnnotation(Column.class);
			if (StringUtils.isEmpty(column.name())) {
				log.debug("getColumnName:{}", f.getName());
				return f.getName();
			} else {
				log.debug("getColumnName:{}", column.name());
				return column.name();
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取列名集合 
	 * 
	 * @param clazz
	 * @return
	 */
	public List<String> getColumnNames(Class<?> clazz) {
		try {
			// 零时集合
			List<String> list = Lists.newArrayList();
			// 访问私有变量前：setAccessible(true)
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
			}
			// 循环@Column的字段
			for (int i = 0; i < fields.length; i++) {
				Field field = clazz.getDeclaredField(fields[i].getName());
				Column column = field.getAnnotation(Column.class);
				if (column != null) {
					if (StringUtils.isEmpty(column.name())) {
						log.debug("getFieldName:{}", field.getName());
						list.add(field.getName());
					} else {
						log.debug("getColumnName:{}", column.name());
						list.add(column.name());
					}
				}
			}
			return list;
		} catch (Exception e) {
			return null;
		}
	}

	// public static void main(String[] args) {
	// EntityNameHandler handler = new EntityNameHandler();
	// System.out.println(handler.getTableName(LogStorage.class));
	// System.out.println(handler.getPrimaryName(LogStorage.class));
	// System.out.println(handler.getColumnName(LogStorage.class, "createTimeFormat"));
	// List<String> cols = handler.getColumnNames(LogStorage.class);
	// System.out.println(cols.size());
	// System.out.println(cols);
	// }
}