package com.xiesx.gotv.support.datebase.jdbc.base;

import java.io.Serializable;
import java.util.List;

/***
 * @title SimpleEventAdapter.java
 * @description 事件总线接口 (抽象父类)
 * @author Sixian.xie
 * @date 2019年3月12日 下午5:47:10
 */
public abstract class AbstractEntity<T> implements Serializable {

	/** 序列化 */
	private static final long serialVersionUID = 1L;

	public abstract T find();

	public abstract T find(List<String> fields);

	public abstract List<T> list();

	public abstract List<T> list(List<String> fields);

	public abstract Integer insert();

	public abstract Integer update();
}