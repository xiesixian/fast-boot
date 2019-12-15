package com.xiesx.gotv.base.pagination;

import lombok.Data;

/**
 * @title PaginationVO.java
 * @description 分页请求对象
 * @author Sixian.xie
 * @date 2018年12月24日 下午1:16:06
 */
@Data
public class PaginationVO {

	// 页码
	public Integer page = 1;

	// 大小
	public Integer limit = 25;

	// 大小
	public Integer size = 25;
}
