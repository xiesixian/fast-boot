package com.xiesx.gotv.base.result;

import lombok.NonNull;

public class R {

	/**
	 * 成功
	 */
	public static BaseResult succ() {
		return BaseResult.builder().code(BaseResult.SUCCESS).msg(BaseResult.OP_MSG_SUCC).build();
	}

	public static BaseResult succ(@NonNull String msg) {
		return BaseResult.builder().code(BaseResult.SUCCESS).msg(msg).build();
	}

	public static BaseResult succ(@NonNull Object data) {
		return BaseResult.builder().code(BaseResult.SUCCESS).msg(BaseResult.OP_MSG_SUCC).data(data).build();
	}

	public static BaseResult succ(@NonNull String msg, @NonNull Object data) {
		return BaseResult.builder().code(BaseResult.SUCCESS).msg(msg).data(data).build();
	}

	public static BaseResult succ(@NonNull Integer code, @NonNull String msg) {
		return BaseResult.builder().code(code).msg(msg).build();
	}

	public static BaseResult succ(@NonNull Integer code, @NonNull String msg, @NonNull Object data) {
		return BaseResult.builder().code(code).msg(msg).data(data).build();
	}

	/**
	 * 失败
	 */
	public static BaseResult fail() {
		return BaseResult.builder().code(BaseResult.FAIL).msg(BaseResult.OP_MSG_FAIL).build();
	}

	public static BaseResult fail(@NonNull String msg) {
		return BaseResult.builder().code(BaseResult.FAIL).msg(msg).build();
	}

	public static BaseResult fail(@NonNull Object data) {
		return BaseResult.builder().code(BaseResult.FAIL).msg(BaseResult.OP_MSG_FAIL).data(data).build();
	}

	public static BaseResult fail(@NonNull String msg, Object data) {
		return BaseResult.builder().code(BaseResult.FAIL).msg(msg).data(data).build();
	}

	public static BaseResult fail(@NonNull Integer code, @NonNull String msg) {
		return BaseResult.builder().code(code).msg(msg).build();
	}

	public static BaseResult fail(@NonNull Integer code, @NonNull String msg, Object data) {
		return BaseResult.builder().code(code).msg(msg).data(data).build();
	}

	/**
	 * 异常
	 */
	public static BaseResult error() {
		return BaseResult.builder().code(BaseResult.ERROR).msg(BaseResult.OP_MSG_FAIL).build();
	}

	public static BaseResult error(@NonNull String msg) {
		return BaseResult.builder().code(BaseResult.ERROR).msg(msg).build();
	}

	public static BaseResult error(@NonNull Object data) {
		return BaseResult.builder().code(BaseResult.ERROR).msg(BaseResult.OP_MSG_FAIL).data(data).build();
	}

	public static BaseResult error(@NonNull String msg, Object data) {
		return BaseResult.builder().code(BaseResult.ERROR).msg(msg).data(data).build();
	}

	public static BaseResult error(@NonNull Integer code, @NonNull String msg) {
		return BaseResult.builder().code(code).msg(msg).build();
	}

	public static BaseResult error(@NonNull Integer code, @NonNull String msg, Object data) {
		return BaseResult.builder().code(code).msg(msg).data(data).build();
	}
}