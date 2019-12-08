package com.xiesx.gotv.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class IDUtils {

	/**
	 * md5 类型ID
	 * 
	 * @param target
	 * @return
	 */
	public static String md5Id(String target) {
		return DigestUtils.md5Hex(target);
	}

	/**
	 * 时间戳
	 * 
	 * @return
	 */
	public static String timeId() {
		return String.valueOf(System.currentTimeMillis());
	}
}
