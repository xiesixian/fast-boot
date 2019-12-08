package com.xiesx.gotv.base.pagination;

import java.util.List;

import lombok.NonNull;

import com.google.common.collect.Lists;
import com.xiesx.gotv.base.pagination.bean.PaginationResult;

/**
 * @title PaginationVO.java
 * @description 分页帮助类
 * @author Sixian.xie
 * @date 2018年12月24日 下午1:16:06
 */
public class PaginationHelper {

	/**
	 * 返回
	 * 
	 * @param data
	 * @param total
	 * @return
	 */
	public static PaginationResult create(@NonNull List<?> data) {
		return create(data, null);
	}

	/**
	 * 返回
	 * 
	 * @param data
	 * @param total
	 * @return
	 */
	public static PaginationResult create(@NonNull List<?> data, Integer total) {
		return PaginationResult.builder().data(Lists.newArrayList(data)).total(total).build();
	}
}
