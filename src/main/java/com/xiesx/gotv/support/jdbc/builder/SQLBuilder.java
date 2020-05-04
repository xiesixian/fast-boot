package com.xiesx.gotv.support.jdbc.builder;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.google.common.base.Joiner;
import com.xiesx.gotv.support.jdbc.utils.EntityClassUtils;
import com.xiesx.gotv.support.jdbc.utils.EntityNameHandler;

/**
 * @title SQLBuilder.java
 * @description SQL构造
 * @author Sixian.xie
 * @date 2019年1月22日 下午5:33:43
 */
@Slf4j
public class SQLBuilder {

	/**
	 * 构建 select sql
	 * 
	 * @param entity
	 * @return
	 *         @
	 */
	public static SQLContext select(Object entity) {
		return select(entity, null, new EntityNameHandler());
	}

	/**
	 * 构建 select sql
	 * 
	 * @param entity
	 * @param fields
	 * @return
	 *         @
	 */
	public static SQLContext select(Object entity, List<String> fields) {
		return select(entity, fields, new EntityNameHandler());
	}

	/**
	 * 构建 select sql
	 * 
	 * @param entity
	 * @param fields
	 * @param nameHandler
	 * @return
	 *         @
	 */
	public static SQLContext select(Object entity, List<String> fields, EntityNameHandler nameHandler) {
		try {
			//
			Class<?> clazz = entity.getClass();
			String tableName = nameHandler.getTableName(clazz);
			// 获取属性信息
			BeanInfo beanInfo = EntityClassUtils.getSelfBeanInfo(entity.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			// 默认查所有
			String field = "*";
			// 拼接查询字段
			StringBuilder condition = new StringBuilder();
			if (fields != null) {
				field = Joiner.on(", ").join(fields);
			}
			condition.append("select " + field + " from " + tableName + " where 1=1");
			List<Object> params = new ArrayList<Object>();
			int count = 0;
			for (PropertyDescriptor pd : pds) {
				String key = nameHandler.getColumnName(clazz, pd.getName());
				if (key == null) {
					continue;
				}
				Object value = readMethodValue(pd.getReadMethod(), entity);
				if (value == null) {
					continue;
				}
				if (count >= 0) {
					condition.append(" and ");
				}
				condition.append(key);
				condition.append(" = ?");
				params.add(value);
				count++;
			}
			SQLContext sqlContext = new SQLContext(condition, params);
			log.info(String.format("query sql------>： %s par------>：%s", sqlContext.getSqlString(), sqlContext.getParams()));
			return sqlContext;
		} catch (Exception e) {
			log.error("select error:{}", e);
		}
		return null;
	}

	/**
	 * 构建 insert sql
	 * 
	 * @param entity
	 * @return
	 *         @
	 */
	public static SQLContext insert(Object entity) {
		return insert(entity, new EntityNameHandler());
	}

	/**
	 * 构建 insert sql
	 * 
	 * @param entity
	 * @param nameHandler
	 * @return
	 *         @
	 */
	public static SQLContext insert(Object entity, EntityNameHandler nameHandler) {
		try {
			Class<?> clazz = entity.getClass();
			String tableName = nameHandler.getTableName(clazz);
			// String primaryName = nameHandler.getPrimaryName(clazz);
			// 添加参数
			List<Object> params = new ArrayList<Object>();
			// 拼接sql
			StringBuilder sql = new StringBuilder("insert into ");
			sql.append(tableName);
			sql.append("(");
			StringBuilder args = new StringBuilder();
			args.append("(");
			// 获取属性信息
			BeanInfo beanInfo = EntityClassUtils.getSelfBeanInfo(clazz);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				String key = nameHandler.getColumnName(clazz, pd.getName());
				if (key == null) {
					continue;
				}
				Object value = readMethodValue(pd.getReadMethod(), entity);
				if (value == null) {
					continue;
				}
				sql.append(key);
				args.append("?");
				params.add(value);
				sql.append(",");
				args.append(",");
			}
			sql.deleteCharAt(sql.length() - 1);
			args.deleteCharAt(args.length() - 1);
			args.append(")");
			sql.append(")");
			sql.append(" values ");
			sql.append(args);
			SQLContext sqlContext = new SQLContext(sql, params);
			log.info(String.format("insert sql------>： %s par------>：%s", sqlContext.getSqlString(), sqlContext.getParams()));
			return sqlContext;
		} catch (Exception e) {
			log.error("insert error:{}", e);
		}
		return null;
	}

	/**
	 * 构建 update sql
	 * 
	 * @param entity
	 * @return
	 *         @
	 */
	public static SQLContext update(Object entity) {
		return update(entity, new EntityNameHandler());
	}

	/**
	 * 构建 update sql
	 * 
	 * @param entity
	 * @param nameHandler
	 * @return
	 *         @
	 */
	public static SQLContext update(Object entity, EntityNameHandler nameHandler) {
		try {
			Class<?> clazz = entity.getClass();
			String tableName = nameHandler.getTableName(clazz);
			String primaryName = nameHandler.getPrimaryName(clazz);
			// 拼接sql
			StringBuilder sql = new StringBuilder();
			// 添加参数
			List<Object> params = new ArrayList<Object>();
			// 获取属性信息
			BeanInfo beanInfo = EntityClassUtils.getSelfBeanInfo(clazz);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			sql.append("update ");
			sql.append(tableName);
			sql.append(" set ");
			Object primaryValue = null;
			for (PropertyDescriptor pd : pds) {
				String key = nameHandler.getColumnName(clazz, pd.getName());
				if (key == null) {
					continue;
				}
				Object value = readMethodValue(pd.getReadMethod(), entity);
				if (value == null) {
					continue;
				}
				if (primaryName.equalsIgnoreCase(key)) {
					primaryValue = value;
				}
				sql.append(key);
				sql.append(" = ");
				sql.append("?");
				params.add(value);
				sql.append(",");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(" where ");
			sql.append(primaryName);
			sql.append(" = ?");
			params.add(primaryValue);
			SQLContext sqlContext = new SQLContext(sql, primaryName, params);
			log.info(String.format("update sql------>： %s par------>：%s", sqlContext.getSqlString(), sqlContext.getParams()));
			return sqlContext;
		} catch (Exception e) {
			log.error("update error:{}", e);
		}
		return null;
	}

	/**
	 * 构建 del sql（省略）
	 * 
	 * @param entity
	 * @param nameHandler
	 * @return
	 *         @
	 */
	public static SQLContext del(Object entity) {
		return null;
	}

	// =======================================
	/**
	 * 获取属性值
	 * 
	 * @param readMethod
	 * @param entity
	 * @return
	 *         @
	 */
	private static Object readMethodValue(Method readMethod, Object entity) {
		try {
			if (readMethod == null) {
				return null;
			}
			if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
				readMethod.setAccessible(true);
			}
			return readMethod.invoke(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// public static void main(String[] args) {
	// // 链式构造
	// LogStorage cipOrderChain = new LogStorage().setId(1L).setIp("0.0.0.0").setCreateDate(new Date());
	// // 普通构造
	// LogStorage cipOrder = new LogStorage();
	// cipOrder.setId(1L);
	// cipOrder.setIp("0.0.0.0");
	// cipOrder.setCreateDate(new Date());
	// // 查询参数构造
	// Fields common = Fields.create(LogStorage.FIELDS.ID, LogStorage.FIELDS.CREATE_DATE);
	// Fields field = Fields.create();
	// field.add(common);
	// field.add(LogStorage.FIELDS.METHOD, "q");// 别名
	// field.add(LogStorage.FIELDS.TYPE);
	// field.add(LogStorage.FIELDS.URL);
	// // 查询所有
	// SQLContext queryAllSql = SQLBuilder.select(cipOrderChain);
	// System.out.println(String.format("查询所有: SQL %s", queryAllSql.getSqlFormat()));
	// System.out.println(String.format("查询所有: 参数 %s \n\n", queryAllSql.getParams()));
	// // 条件查询
	// SQLContext queryFieldsSql = SQLBuilder.select(cipOrder, field.fields());
	// System.out.println(String.format("条件查询: SQL %s", queryFieldsSql.getSqlFormat()));
	// System.out.println(String.format("条件查询: 参数 %s \n\n", queryFieldsSql.getParams()));
	// // 添加
	// SQLContext insertSql = SQLBuilder.insert(cipOrder);
	// System.out.println(String.format("添加: SQL %s \n\n 参数:%s \n\n", insertSql.getSqlFormat(), insertSql.getParams()));
	// // 修改
	// SQLContext updateSql = SQLBuilder.update(cipOrder);
	// System.out.println(String.format("修改: SQL %s \n\n 参数:%s 主键:%s", updateSql.getSqlFormat(), updateSql.getParams(), updateSql.getPrimaryKey()));
	// }
}