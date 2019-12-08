package com.xiesx.gotv.utils;

import java.util.Random;

/**
 * 转换
 * 
 * @author Sixian.Xie
 * @date 2018-01-29
 */
public class RadomUtils {

	/**
	 * 获取随机数字或字母
	 * 
	 * @param len
	 *            - 随机数长度
	 * @param type
	 *            - 随机数类型: n-数字 w-字母 nw-字母加数字
	 * @return
	 */
	public static String radomCode(int len, String type) {
		String result = "0000";
		String wSource = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String nwSource = "13456789ABCDEFGHIJKLMNPQRSTUVWXY";
		Random random = new Random();

		if ("n".equals(type)) {// 随机数字
			StringBuffer sb = new StringBuffer(len);
			for (int i = 0; i < len; i++) {
				String number = random.nextInt(len) + "";
				sb.append(number);
			}
			result = sb.toString();
		} else if ("w".equals(type)) {// 随机字母
			StringBuffer sb = new StringBuffer(len);
			for (int i = 0; i < len; i++) {
				final int number = random.nextInt(wSource.length());
				sb.append(wSource.charAt(number));
			}
			result = sb.toString();
		} else if ("nw".equals(type)) {// 随机数字+字母
			StringBuffer sb = new StringBuffer(len);
			for (int i = 0; i < len; i++) {
				final int number = random.nextInt(nwSource.length());
				sb.append(nwSource.charAt(number));
			}
			result = sb.toString();
		}
		return result;
	}
}
