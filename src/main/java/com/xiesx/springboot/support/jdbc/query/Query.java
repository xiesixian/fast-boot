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
package com.xiesx.springboot.support.jdbc.query;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xiesx.springboot.support.jdbc.persistence.Fields;
import com.xiesx.springboot.support.jdbc.persistence.IShardingable;
import com.xiesx.springboot.support.jdbc.persistence.base.EntityMeta;
import com.xiesx.springboot.support.jdbc.persistence.dialect.IDialect;
import com.xiesx.springboot.support.jdbc.persistence.dialect.impl.MySQLDialect;
import com.xiesx.springboot.support.jdbc.utils.ClassUtils;

/**
 * @param <T> 当前实现类类型
 * @author 刘镇 (suninformation@163.com) on 2017/12/14 下午11:43
 * @version 1.0
 */
public class Query<T> {

	private static final Log _LOG = LogFactory.getLog(Query.class);

	private String __tablePrefix;

	private IDialect __dialect;

	private IShardingable __shardingable;

	/**
	 * @return 返回表前缀，若未设置则返回默认数据源配置的表前缀
	 */
	public String tablePrefix() {
		return "";
	}

	@SuppressWarnings("unchecked")
	public T tablePrefix(String tablePrefix) {
		__tablePrefix = tablePrefix;
		//
		return (T) this;
	}

	/**
	 * @return 返回当前数据库方言，若未设置则返回默认数据源配置的方言
	 */
	public IDialect dialect() {
		return ClassUtils.impl(MySQLDialect.class, IDialect.class);
	}

	@SuppressWarnings("unchecked")
	public T dialect(IDialect dialect) {
		__dialect = dialect;
		//
		return (T) this;
	}

	public IShardingable shardingable() {
		return __shardingable;
	}

	@SuppressWarnings("unchecked")
	public T shardingable(IShardingable shardingable) {
		__shardingable = shardingable;
		//
		return (T) this;
	}

	// ----------

	protected Fields __checkFieldExcluded(Fields fields) {
		if (fields.isExcluded()) {
			_LOG.warn("Query fields do not support exclusion and have been cleaned up.");
			return Fields.create();
		}
		return fields;
	}

	protected String __buildSafeTableName(String prefix, String tableName, boolean safePrefix) {
		if (safePrefix) {
			prefix = StringUtils.defaultIfBlank(prefix, this.tablePrefix());
			return this.dialect().wrapIdentifierQuote(StringUtils.trimToEmpty(prefix).concat(tableName));
		}
		return StringUtils.trimToEmpty(prefix).concat(tableName);
	}

	protected String __buildSafeTableName(String prefix, EntityMeta entityMeta, boolean safePrefix) {
		if (safePrefix) {
			prefix = StringUtils.defaultIfBlank(prefix, this.tablePrefix());
			return this.dialect().buildTableName(prefix, entityMeta, this.shardingable());
		}
		return StringUtils.trimToEmpty(prefix).concat(entityMeta.getEntityName());
	}

	protected Fields __wrapIdentifierFields(String... fields) {
		Fields _returnValue = Fields.create();
		if (fields != null) {
			for (String _field : fields) {
				_returnValue.add(__wrapIdentifierField(_field));
			}
		}
		return _returnValue;
	}

	protected String __wrapIdentifierField(String field) {
		String[] _split = StringUtils.split(field, ".");
		if (_split != null && _split.length == 2) {
			String[] _alias = StringUtils.split(_split[1]);
			if (_alias != null && _alias.length == 2) {
				return _split[0] + "." + this.dialect().wrapIdentifierQuote(_alias[0]) + " " + _alias[1];
			}
			return _split[0] + "." + this.dialect().wrapIdentifierQuote(_split[1]);
		}
		return this.dialect().wrapIdentifierQuote(field);
	}
}
