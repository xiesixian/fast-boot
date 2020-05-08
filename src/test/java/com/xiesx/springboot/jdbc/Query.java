package com.xiesx.springboot.jdbc;

import com.xiesx.springboot.support.jdbc.persistence.Fields;
import com.xiesx.springboot.support.jdbc.persistence.Page;
import com.xiesx.springboot.support.jdbc.persistence.Params;
import com.xiesx.springboot.support.jdbc.query.Cond;
import com.xiesx.springboot.support.jdbc.query.Join;
import com.xiesx.springboot.support.jdbc.query.Union;
import com.xiesx.springboot.support.jdbc.query.Where;
import com.xiesx.springboot.support.jdbc.query.by.GroupBy;
import com.xiesx.springboot.support.jdbc.query.by.OrderBy;
import com.xiesx.springboot.support.jdbc.query.crud.Select;

public class Query {

	public static void main(String[] args) {
		// 创建Fields对象
		Fields _fields = Fields.create("username", "pwd", "age");
		// 带前缀和别名
		_fields.add("u", "sex", "s");
		// 带前缀
		_fields = Fields.create().add("u", "id").add(_fields);
		// 标记集合中的字段为排除的
		_fields.excluded(true);
		// 判断是否存在排除标记
		_fields.isExcluded();
		// 输出
		System.out.println(_fields.fields());

		// 创建Params对象，任何类型参数
		Params _params = Params.create("p1", 2, false, 0.1).add("param");
		_params = Params.create().add("paramN").add(_params);
		// 输出
		System.out.println(_params.params());

		// 查询每1页, 默认每页20条记录
		Page.create(1);
		// 查询第1页, 每页10条记录
		Page.create(1).pageSize(10);
		// 查询第1页, 每页10条记录, 不统计总记录数
		Page.create(1).pageSize(10).count(false);

		Cond _cond = Cond.create()//
				.bracketBegin().like("username").param("%ymp%").and().gtEq("age").param(20).bracketEnd()//
				.or()//
				.bracketBegin().eq("sex").param("F").and().lt("age").param(18).bracketEnd();//
		System.out.println("SQL: " + _cond.toString());
		System.out.println("参数: " + _cond.params().params());

		OrderBy _orderBy = OrderBy.create().asc("age").desc("u", "birthday");
		System.out.println(_orderBy.toSQL());

		GroupBy _groupBy = GroupBy.create(Fields.create().add("u", "sex").add("dept"))
				.having(Cond.create().lt("age").param(18));
		System.out.println("SQL: " + _groupBy.toString());
		System.out.println("参数: " + _groupBy.having().params().params());

		Where _where = Where.create(_cond).groupBy(_groupBy).orderDesc("username");
		_where.orderBy().orderBy(_orderBy);
		System.out.println("SQL: " + _where.toString());
		System.out.println("参数: " + _where.getParams().params());

		Join _join = Join.inner("user_ext").alias("ue").on(Cond.create().opt("ue", "uid", Cond.OPT.EQ, "u", "id"));
		System.out.println(_join);

		Select _select = Select.create("user").where(Where.create(Cond.create().eq("dept").param("IT")))
				.union(Union.create(Select.create("user").where(Where.create(Cond.create().lt("age").param(18)))));
		//
		System.out.println("SQL: " + _select.toString());
		System.out.println("参数: " + _select.getParams().params().toString());
	}
}
