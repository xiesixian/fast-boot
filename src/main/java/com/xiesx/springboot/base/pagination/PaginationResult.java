package com.xiesx.springboot.base.pagination;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Builder;
import lombok.Data;

/**
 * @title PaginationBO.java
 * @description 分页返回对象
 * @author Sixian.xie
 * @date 2018年12月24日 下午1:15:29
 */
@Data
@Builder
public class PaginationResult {

	// 状态
	@JSONField(ordinal = 1)
	public Integer code;

	@JSONField(ordinal = 2)
	public String msg;

	@JSONField(ordinal = 3)
	public List<?> data;

	@JSONField(ordinal = 4)
	public Integer count;
}
