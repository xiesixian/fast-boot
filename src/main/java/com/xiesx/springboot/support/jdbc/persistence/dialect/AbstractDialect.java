/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xiesx.springboot.support.jdbc.persistence.dialect;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.xiesx.springboot.support.jdbc.persistence.Fields;
import com.xiesx.springboot.support.jdbc.persistence.IShardingRule;
import com.xiesx.springboot.support.jdbc.persistence.IShardingable;
import com.xiesx.springboot.support.jdbc.persistence.base.EntityMeta;
import com.xiesx.springboot.support.jdbc.persistence.base.IEntity;
import com.xiesx.springboot.support.jdbc.utils.ClassUtils;
import com.xiesx.springboot.support.jdbc.utils.ExpressionUtils;

/**
 * 数据库方言接口抽象实现
 *
 * @author 刘镇 (suninformation@163.com) on 2011-8-30 下午01:55:13
 * @version 1.0
 */
public abstract class AbstractDialect implements IDialect {

	protected static final String __LINE_END_FLAG = ",\n";

	/**
	 * 引用标识符-开始
	 */
	private String identifierQuoteBegin = "";

	/**
	 * 引用标识符-结束
	 */
	private String identifierQuoteEnd = "";

	public AbstractDialect() {
	}

	public AbstractDialect(String identifierQuoteBegin, String identifierQuoteEnd) {
		this.setIdentifierQuote(identifierQuoteBegin, identifierQuoteEnd);
	}

	@Override
	public String wrapIdentifierQuote(String origin) {
		return identifierQuoteBegin.concat(origin).concat(identifierQuoteEnd);
	}

	@Override
	public void setIdentifierQuote(String identifierQuoteBegin, String identifierQuoteEnd) {
		this.identifierQuoteBegin = StringUtils.trimToEmpty(identifierQuoteBegin);
		this.identifierQuoteEnd = StringUtils.trimToEmpty(identifierQuoteEnd);
	}

	@Override
	public String getIdentifierQuoteBegin() {
		return identifierQuoteBegin;
	}

	@Override
	public String getIdentifierQuoteEnd() {
		return identifierQuoteEnd;
	}

	@Override
	public Map<String, Object> getGeneratedKey(Statement statement, List<String> autoincrementKeys)
			throws SQLException {
		// 检索由于执行此 Statement 对象而创建的所有自动生成的键
		Map<String, Object> _ids = new HashMap<String, Object>();
		ResultSet _keyRSet = statement.getGeneratedKeys();
		try {
			for (String _autoKey : autoincrementKeys) {
				while (_keyRSet.next()) {
					Object _keyValue;
					try {
						_keyValue = _keyRSet.getObject(_autoKey);
					} catch (SQLException e) {
						_keyValue = _keyRSet.getObject(1);
					}
					_ids.put(_autoKey, _keyValue);
				}
			}
		} finally {
			_keyRSet.close();
		}
		return _ids;
	}

	@Override
	public String getSequenceNextValSql(String sequenceName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String buildPagedQuerySQL(String originSql, int page, int pageSize) {
		int _limit = ((page - 1) * pageSize);
		if (pageSize == 0) {
			return originSql.concat(" limit ").concat(Integer.toString(_limit));
		} else {
			return originSql.concat(" limit ")
					.concat(Integer.toString(_limit))
					.concat(", ")
					.concat(Integer.toString(pageSize));
		}
	}

	@Override
	public String buildCreateSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String buildDropSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable) {
		throw new UnsupportedOperationException();
	}

	protected String __doGetColumnType(Class<?> clazz) {
		String _columnType = "VARCHAR";
		if (BigDecimal.class.equals(clazz)) {
			_columnType = "NUMERIC";
		} else if (Boolean.class.equals(clazz) || boolean.class.equals(clazz)) {
			_columnType = "BIT";
		} else if (Byte.class.equals(clazz) || byte.class.equals(clazz)) {
			_columnType = "TINYINT";
		} else if (Short.class.equals(clazz) || short.class.equals(clazz)) {
			_columnType = "SMALLINT";
		} else if (Integer.class.equals(clazz) || int.class.equals(clazz)) {
			_columnType = "INTEGER";
		} else if (Long.class.equals(clazz) || long.class.equals(clazz)) {
			_columnType = "BIGINT";
		} else if (Float.class.equals(clazz) || float.class.equals(clazz)) {
			_columnType = "FLOAT";
		} else if (Double.class.equals(clazz) || double.class.equals(clazz)) {
			_columnType = "DOUBLE";
		} else if (byte[].class.equals(clazz) || Byte[].class.equals(clazz)) {
			_columnType = "BINARY";
		} else if (java.sql.Date.class.equals(clazz) || java.util.Date.class.equals(clazz)) {
			_columnType = "DATE";
		} else if (java.sql.Time.class.equals(clazz)) {
			_columnType = "TIME";
		} else if (java.sql.Timestamp.class.equals(clazz)) {
			_columnType = "TIMESTAMP";
		} else if (java.sql.Blob.class.equals(clazz)) {
			_columnType = "BLOB";
		} else if (java.sql.Clob.class.equals(clazz)) {
			_columnType = "CLOB";
		}
		return _columnType;
	}

	/**
	 * @param fields    字段名称集合
	 * @param suffix    字段名称后缀，可选
	 * @param separator 分隔符，可选，默认“, ”
	 * @return 将字段名称集合转换成为采用separator分隔的字符串
	 */
	protected String __doGenerateFieldsFormatStr(Fields fields, String suffix, String separator) {
		StringBuilder _fieldsSB = new StringBuilder();
		Iterator<String> _fieldsIt = fields.fields().iterator();
		suffix = StringUtils.defaultIfBlank(suffix, "");
		separator = StringUtils.defaultIfBlank(separator, ", ");
		while (_fieldsIt.hasNext()) {
			_fieldsSB.append(this.wrapIdentifierQuote(_fieldsIt.next())).append(suffix);
			if (_fieldsIt.hasNext()) {
				_fieldsSB.append(separator);
			}
		}
		return _fieldsSB.toString();
	}

	@Override
	public String buildTableName(String prefix, EntityMeta entityMeta, IShardingable shardingable) {
		String _entityName = entityMeta.getEntityName();
		if (shardingable != null && entityMeta.getShardingRule() != null) {
			IShardingRule _rule = ClassUtils.impl(entityMeta.getShardingRule().value(), IShardingRule.class);
			if (_rule != null) {
				_entityName = _rule.getShardName(entityMeta.getEntityName(), shardingable.getShardingParam());
			}
		}
		return this.wrapIdentifierQuote(StringUtils.defaultIfBlank(prefix, "").concat(_entityName));
	}

	/**
	 * 验证字段是否合法有效
	 *
	 * @param entityMeta    数据实体属性描述对象
	 * @param fields        字段名称集合
	 * @param isPrimaryKeys fields中存放的是否为主键
	 */
	protected void __doValidProperty(EntityMeta entityMeta, Fields fields, boolean isPrimaryKeys) {
		if (isPrimaryKeys) {
			for (String _pkField : fields.fields()) {
				if (!entityMeta.isPrimaryKey(_pkField)) {
					throw new IllegalArgumentException("'".concat(_pkField).concat("' isn't primary key field"));
				}
			}
		} else {
			for (String _field : fields.fields()) {
				if (!entityMeta.containsProperty(_field)) {
					throw new IllegalArgumentException("'".concat(_field).concat("' isn't table field"));
				}
			}
		}
	}

	@Override
	public String buildInsertSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable,
			Fields fields) {
		EntityMeta _meta = EntityMeta.createAndGet(entityClass);
		ExpressionUtils _exp = ExpressionUtils.bind("INSERT INTO ${table_name} (${fields}) VALUES (${values})")
				.set("table_name", buildTableName(prefix, _meta, shardingable));
		//
		Fields _fields = Fields.create();
		if (fields == null || fields.fields().isEmpty()) {
			_fields.add(_meta.getPropertyNames());
		} else {
			_fields.add(fields);
			__doValidProperty(_meta, _fields, false);
		}
		return _exp.set("fields", __doGenerateFieldsFormatStr(_fields, null, null))
				.set("values", StringUtils.repeat("?", ", ", _fields.fields().size()))
				.getResult();
	}

	@Override
	public String buildDeleteByPkSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable,
			Fields pkFields) {
		EntityMeta _meta = EntityMeta.createAndGet(entityClass);
		ExpressionUtils _exp = ExpressionUtils.bind("DELETE FROM ${table_name} WHERE ${pk}")
				.set("table_name", buildTableName(prefix, _meta, shardingable));
		//
		Fields _fields = Fields.create();
		if (pkFields == null || pkFields.fields().isEmpty()) {
			_fields.add(_meta.getPrimaryKeys());
		} else {
			_fields.add(pkFields);
			__doValidProperty(_meta, _fields, true);
		}
		return _exp.set("pk", __doGenerateFieldsFormatStr(_fields, " = ?", " and ")).getResult();
	}

	@Override
	public String buildUpdateByPkSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable,
			Fields pkFields, Fields fields) {
		EntityMeta _meta = EntityMeta.createAndGet(entityClass);
		ExpressionUtils _exp = ExpressionUtils.bind("UPDATE ${table_name} SET ${fields} WHERE ${pk}")
				.set("table_name", buildTableName(prefix, _meta, shardingable));
		//
		Fields _fields = Fields.create();
		for (String _field : (fields == null || fields.fields().isEmpty()) ? _meta.getPropertyNames()
				: fields.fields()) {
			if (_meta.containsProperty(_field)) {
				if (_meta.isPrimaryKey(_field)) {
					// 排除主键
					continue;
				}
				_fields.add(_field);
			} else {
				throw new IllegalArgumentException("'".concat(_field).concat("' isn't table field"));
			}
		}
		_exp.set("fields", __doGenerateFieldsFormatStr(_fields, " = ?", null));
		//
		if (pkFields != null && !pkFields.fields().isEmpty()) {
			_fields = pkFields;
			__doValidProperty(_meta, _fields, true);
		} else {
			_fields = Fields.create().add(_meta.getPrimaryKeys());
		}
		return _exp.set("pk", __doGenerateFieldsFormatStr(_fields, " = ?", " and ")).getResult();
	}

	@Override
	public String buildSelectByPkSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable,
			Fields pkFields, Fields fields) {
		EntityMeta _meta = EntityMeta.createAndGet(entityClass);
		ExpressionUtils _exp = ExpressionUtils.bind("SELECT ${fields} FROM ${table_name} WHERE ${pk}")
				.set("table_name", buildTableName(prefix, _meta, shardingable));
		//
		if (fields == null || fields.fields().isEmpty()) {
			fields = Fields.create().add(_meta.getPropertyNames());
		} else {
			__doValidProperty(_meta, fields, false);
		}
		_exp.set("fields", __doGenerateFieldsFormatStr(fields, null, null));
		//
		if (pkFields != null && !pkFields.fields().isEmpty()) {
			__doValidProperty(_meta, pkFields, true);
		} else {
			pkFields = Fields.create().add(_meta.getPrimaryKeys());
		}
		return _exp.set("pk", __doGenerateFieldsFormatStr(pkFields, " = ?", " and ")).getResult();
	}

	@Override
	public String buildSelectSQL(Class<? extends IEntity> entityClass, String prefix, IShardingable shardingable,
			Fields fields) {
		EntityMeta _meta = EntityMeta.createAndGet(entityClass);
		ExpressionUtils _exp = ExpressionUtils.bind("SELECT ${fields} FROM ${table_name}")
				.set("table_name", buildTableName(prefix, _meta, shardingable));
		//
		if (fields == null || fields.fields().isEmpty()) {
			fields = Fields.create().add(_meta.getPropertyNames());
		} else {
			__doValidProperty(_meta, fields, false);
		}
		return _exp.set("fields", __doGenerateFieldsFormatStr(fields, null, null)).getResult();
	}
}
