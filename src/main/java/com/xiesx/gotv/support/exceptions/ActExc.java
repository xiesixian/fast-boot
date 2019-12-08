package com.xiesx.gotv.support.exceptions;

import lombok.AllArgsConstructor;

/**
 * @title ActExc.java
 * @description 异常枚举处理类
 * @author Sixian.xie
 * @date 2018年12月24日 下午1:16:40
 */
@AllArgsConstructor
public enum ActExc {

	ACT(9000, "数据接口"),

	PARAM(9010, "参数错误"),

	HTTP(9020, "请求异常"),

	RETRY(9030, "重试异常"),

	CALL(9040, "回调状态"),

	API(9050, "公共接口"),

	CORE(9060, "业务模块");

	private Integer errorCode;

	private String massage;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}
}
