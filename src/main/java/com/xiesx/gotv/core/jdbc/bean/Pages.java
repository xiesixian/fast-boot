package com.xiesx.gotv.core.jdbc.bean;

/**
 * @title Page.java
 * @description 分页参数对象
 * @author Sixian.xie
 * @date 2019年5月20日 下午2:00:27
 */
public final class Pages {

	/**
	 * 默认分页大小
	 */
	public static int DEFAULT_PAGE_SIZE = 2;

	/**
	 * 分页大小
	 */
	private int pageSize;

	/**
	 * 当前页号
	 */
	private final int page;

	/**
	 * 是否执行总记录数统计
	 */
	private boolean isCount;

	/**
	 * 静态构造
	 * 
	 * @param page
	 * @return
	 */
	public static Pages create(int page) {
		return new Pages(page);
	}

	/**
	 * 静态构造
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public static Pages create(int page, int pageSize) {
		if (page > 0 && pageSize > 0) {
			return new Pages(page).pageSize(pageSize);
		}
		return null;
	}

	/**
	 * 构造方法
	 * 
	 * @param page
	 */
	private Pages(int page){
		this.page = page > 0 ? page : 1;
		this.isCount = true;
	}

	/**
	 * 设置count
	 * 
	 * @param count
	 * @return
	 */
	public Pages count(boolean count) {
		this.isCount = count;
		return this;
	}

	/**
	 * 设置大小
	 * 
	 * @param pageSize
	 * @return
	 */
	public Pages pageSize(int pageSize) {
		this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
		return this;
	}

	public int page() {
		return page;
	}

	public int pageSize() {
		return pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
	}

	public boolean isCount() {
		return isCount;
	}

	public String sql() {
		return String.format(" limit %d,%d", page() * pageSize() - pageSize(), pageSize());
	}
}
