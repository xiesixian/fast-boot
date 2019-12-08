package com.xiesx.gotv.core.exception;

import lombok.AllArgsConstructor;

/**
 * @title RunExc.java
 * @description 异常枚举处理类
 * @author Sixian.xie
 * @date 2018年12月24日 下午1:16:40
 */
@AllArgsConstructor
public enum RunExc {

	RUN(9000, "运行异常"),

	PARA(9010, "参数错误"),
	
	SIGN(9020, "参数错误"),

	HTTP(9030, "请求异常"),

	RETR(9050, "重试异常"),

	CALL(9050, "回调异常"),

	API(9060, "接口异常");

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
