package com.xiesx.gotv.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * @title ArrayUtils.java
 * @description 字符串转数组转换
 * @author Sixian.xie
 * @date 2019年5月14日 下午1:49:15
 */
public class ArrayUtils {

	/**
	 * str转list
	 * 
	 * @param str
	 * @return
	 */
	public static List<String> splitToList(String str) {
		return splitToList(str, ",");
	}

	/**
	 * str转list
	 * 
	 * @param str
	 * @param separator
	 * @return
	 */
	public static List<String> splitToList(String str, String separator) {
		return Splitter.on(separator).omitEmptyStrings().trimResults().splitToList(str);
	}

	/**
	 * list转str
	 * 
	 * @param str
	 * @return
	 */
	public static String joinTolist(List<String> list) {
		return joinTolist(list, ",");
	}

	/**
	 * list转str
	 * 
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String joinTolist(List<String> list, String separator) {
		return Joiner.on(separator).join(list);
	}

	public static void main(String[] args) throws UnsupportedEncodingException, IllegalAccessException {
		// 按照,号分割
		List<String> list1 = splitToList(",1,2,3, ,4,");
		System.out.println(list1);
		System.out.println(joinTolist(list1));
		// 按照|号分割
		List<String> list2 = splitToList("|4|5|6| |7", "|");
		System.out.println(list2);
		// 按照,号拼接
		System.out.println(joinTolist(list2, ","));
	}
}
