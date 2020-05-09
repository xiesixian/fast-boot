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

import com.xiesx.springboot.support.jdbc.persistence.Fields;
import com.xiesx.springboot.support.jdbc.persistence.IFunction;
import com.xiesx.springboot.support.jdbc.persistence.Params;
import com.xiesx.springboot.support.jdbc.query.crud.Select;
import com.xiesx.springboot.support.jdbc.query.sql.SQL;

/**
 * 条件对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/9 下午8:12
 * @version 1.0
 */
public final class Cond extends Query<Cond> {

	public enum OPT {
		EQ("="), NOT_EQ("!="), LT("<"), GT(">"), LT_EQ("<="), GT_EQ(">="), LIKE("LIKE");

		private final String __opt;

		OPT(String opt) {
			this.__opt = opt;
		}

		@Override
		public String toString() {
			return __opt;
		}
	}

	private final StringBuilder __condSB;

	/**
	 * SQL参数集合
	 */
	private final Params __params;

	public static Cond create() {
		return new Cond();
	}

	private Cond() {
		__condSB = new StringBuilder();
		__params = Params.create();
	}

	public Params params() {
		return this.__params;
	}

	public Cond param(Object param) {
		this.__params.add(param);
		return this;
	}

	public Cond param(Params params) {
		this.__params.add(params);
		return this;
	}

	public Cond cond(String cond) {
		__condSB.append(" ").append(cond).append(" ");
		return this;
	}

	public Cond cond(IFunction func) {
		return cond(func.build());
	}

	public Cond cond(Cond cond) {
		__condSB.append(cond.toString());
		__params.add(cond.params());
		return this;
	}

	public Cond opt(String prefixA, String fieldA, OPT opt, String prefixB, String fieldB) {
		return opt(Fields.field(prefixA, __wrapIdentifierField(fieldA)), opt,
				Fields.field(prefixB, __wrapIdentifierField(fieldB)));
	}

	public Cond opt(String fieldA, OPT opt, String fieldB) {
		__condSB.append(fieldA).append(" ").append(opt).append(" ").append(fieldB);
		return this;
	}

	public Cond optWrap(String fieldA, OPT opt, String fieldB) {
		return opt(__wrapIdentifierField(fieldA), opt, __wrapIdentifierField(fieldB));
	}

	public Cond opt(String prefix, String field, OPT opt) {
		return opt(Fields.field(prefix, __wrapIdentifierField(field)), opt);
	}

	public Cond opt(String field, OPT opt) {
		__condSB.append(field).append(" ").append(opt).append(" ?");
		return this;
	}

	public Cond optWrap(String field, OPT opt) {
		return opt(__wrapIdentifierField(field), opt);
	}

	public Cond opt(IFunction func, OPT opt) {
		return opt(func.build(), opt);
	}

	/**
	 * @return 用于生成Where条件辅助表达式1=1
	 */
	public Cond eqOne() {
		__condSB.append("1 = 1");
		return this;
	}

	public Cond eq(String prefix, String field) {
		return eq(Fields.field(prefix, __wrapIdentifierField(field)));
	}

	public Cond eq(String field) {
		return opt(field, OPT.EQ);
	}

	public Cond eqWrap(String field) {
		return optWrap(field, OPT.EQ);
	}

	public Cond eq(IFunction func) {
		return opt(func, OPT.EQ);
	}

	// ------

	public Cond notEq(String prefix, String field) {
		return notEq(Fields.field(prefix, __wrapIdentifierField(field)));
	}

	public Cond notEq(String field) {
		return opt(field, OPT.NOT_EQ);
	}

	public Cond notEqWrap(String field) {
		return optWrap(field, OPT.NOT_EQ);
	}

	public Cond notEq(IFunction func) {
		return opt(func, OPT.NOT_EQ);
	}

	// ------

	public Cond gtEq(String prefix, String field) {
		return gtEq(Fields.field(prefix, __wrapIdentifierField(field)));
	}

	public Cond gtEq(String field) {
		return opt(field, OPT.GT_EQ);
	}

	public Cond gtEqWrap(String field) {
		return optWrap(field, OPT.GT_EQ);
	}

	public Cond gtEq(IFunction func) {
		return opt(func, OPT.GT_EQ);
	}

	// ------

	public Cond gt(String prefix, String field) {
		return gt(Fields.field(prefix, __wrapIdentifierField(field)));
	}

	public Cond gt(String field) {
		return opt(field, OPT.GT);
	}

	public Cond gtWrap(String field) {
		return optWrap(field, OPT.GT);
	}

	public Cond gt(IFunction func) {
		return opt(func, OPT.GT);
	}

	// ------

	public Cond ltEq(String prefix, String field) {
		return ltEq(Fields.field(prefix, __wrapIdentifierField(field)));
	}

	public Cond ltEq(String field) {
		return opt(field, OPT.LT_EQ);
	}

	public Cond ltEqWrap(String field) {
		return optWrap(field, OPT.LT_EQ);
	}

	public Cond ltEq(IFunction func) {
		return opt(func, OPT.LT_EQ);
	}

	// ------

	public Cond lt(String prefix, String field) {
		return lt(Fields.field(prefix, __wrapIdentifierField(field)));
	}

	public Cond lt(String field) {
		return opt(field, OPT.LT);
	}

	public Cond ltWrap(String field) {
		return optWrap(field, OPT.LT);
	}

	public Cond lt(IFunction func) {
		return opt(func, OPT.LT);
	}

	// ------

	public Cond like(String prefix, String field) {
		return like(Fields.field(prefix, __wrapIdentifierField(field)));
	}

	public Cond like(String field) {
		return opt(field, OPT.LIKE);
	}

	public Cond likeWrap(String field) {
		return optWrap(field, OPT.LIKE);
	}

	public Cond like(IFunction func) {
		return opt(func, OPT.LIKE);
	}

	// ------

	public Cond between(String prefix, String field, Object valueOne, Object valueTwo) {
		return between(Fields.field(prefix, __wrapIdentifierField(field)), valueOne, valueTwo);
	}

	public Cond between(String field, Object valueOne, Object valueTwo) {
		__condSB.append(field).append(" BETWEEN ? AND ?");
		__params.add(valueOne).add(valueTwo);
		return this;
	}

	public Cond betweenWrap(String field, Object valueOne, Object valueTwo) {
		return between(null, field, valueOne, valueTwo);
	}

	public Cond between(IFunction func, Object valueOne, Object valueTwo) {
		return between(func.build(), valueOne, valueTwo);
	}

	// ------

	public Cond isNull(String prefix, String field) {
		return isNull(Fields.field(prefix, __wrapIdentifierField(field)));
	}

	public Cond isNull(String field) {
		__condSB.append(field).append(" IS NULL");
		return this;
	}

	public Cond isNullWrap(String field) {
		return isNull(null, field);
	}

	public Cond isNull(IFunction func) {
		return isNull(func.build());
	}

	// ------

	public Cond isNotNull(String prefix, String field) {
		return isNotNull(Fields.field(prefix, __wrapIdentifierField(field)));
	}

	public Cond isNotNull(String field) {
		__condSB.append(field).append(" IS NOT NULL");
		return this;
	}

	public Cond isNotNullWrap(String field) {
		return isNotNull(null, field);
	}

	public Cond isNotNull(IFunction func) {
		return isNotNull(func.build());
	}

	// ------

	public Cond and() {
		return cond("AND");
	}

	public Cond or() {
		return cond("OR");
	}

	public Cond not() {
		return cond("NOT");
	}

	public Cond bracketBegin() {
		return cond("(");
	}

	public Cond bracketEnd() {
		return cond(")");
	}

	public Cond exists(SQL subSql) {
		__condSB.append(" EXISTS (").append(subSql.getSQL()).append(")");
		__params.add(subSql.params());
		return this;
	}

	public Cond exists(Select subSql) {
		__condSB.append(" EXISTS (").append(subSql.toString()).append(")");
		__params.add(subSql.getParams());
		return this;
	}

	// ------

	public Cond in(String prefix, String field, SQL subSql) {
		return in(Fields.field(prefix, __wrapIdentifierField(field)), subSql);
	}

	public Cond in(String field, SQL subSql) {
		__condSB.append(field).append(" IN (").append(subSql.getSQL()).append(")");
		__params.add(subSql.params());
		return this;
	}

	public Cond inWrap(String field, SQL subSql) {
		return in(null, field, subSql);
	}

	public Cond in(String prefix, String field, Select subSql) {
		return in(Fields.field(prefix, __wrapIdentifierField(field)), subSql);
	}

	public Cond in(String field, Select subSql) {
		__condSB.append(field).append(" IN (").append(subSql.toString()).append(")");
		__params.add(subSql.getParams());
		return this;
	}

	public Cond inWrap(String field, Select subSql) {
		return in(null, field, subSql);
	}

	public Cond in(String prefix, String field, Params params) {
		return in(Fields.field(prefix, __wrapIdentifierField(field)), params);
	}

	public Cond in(String field, Params params) {
		__condSB.append(field)
				.append(" IN (")
				.append(StringUtils.repeat("?", ", ", params.params().size()))
				.append(")");
		__params.add(params);
		return this;
	}

	public Cond inWrap(String field, Params params) {
		return in(null, field, params);
	}

	/**
	 * @param expression 逻辑表达式
	 * @param cond       条件对象
	 * @return 根据逻辑表达式运算结果决定是否采纳cond条件
	 */
	public Cond expr(boolean expression, Cond cond) {
		if (expression && cond != null) {
			this.cond(cond);
		}
		return this;
	}

	public Cond expr(boolean expression, String cond) {
		if (expression && cond != null) {
			this.cond(cond);
		}
		return this;
	}

	/**
	 * @param target 目标对象
	 * @param cond   条件对象
	 * @return 当目标对象非空则采纳cond条件
	 */
	public Cond exprNotEmpty(Object target, Cond cond) {
		if (target != null && cond != null) {
			boolean _flag = true;
			if (target.getClass().isArray()) {
				_flag = ((Object[]) target).length > 0;
			} else if (target instanceof String) {
				_flag = StringUtils.isNotBlank((String) target);
			}
			//
			if (_flag) {
				this.cond(cond);
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return __condSB.toString();
	}
}
