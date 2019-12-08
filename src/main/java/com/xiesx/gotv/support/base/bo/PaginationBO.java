package com.xiesx.gotv.support.base.bo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @title PaginationBO.java
 * @description 分页返回对象
 * @author Sixian.xie
 * @date 2018年12月24日 下午1:15:29
 */
@Data
@AllArgsConstructor
public class PaginationBO<T> {

	@JSONField(ordinal = 1)
	public Integer total;

	@JSONField(ordinal = 2)
	public List<T> data;
}
