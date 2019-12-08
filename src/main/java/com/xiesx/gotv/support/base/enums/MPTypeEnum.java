package com.xiesx.gotv.support.base.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 用户类型枚举
 * 
 * @author Sixian.Xie
 * @date 2018-01-29
 */
public enum MPTypeEnum implements IEnum<Integer> {

	business(1, "商家"),
	user(2, "用户");

	private int value;

	private String desc;

	MPTypeEnum(final int value, final String desc){
		this.value = value;
		this.desc = desc;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}

	@JsonValue
	public String getDesc() {
		return this.desc;
	}
}
