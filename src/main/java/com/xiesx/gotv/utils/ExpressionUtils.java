package com.xiesx.gotv.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ExpressionUtils {

	private final static Pattern __pattern = Pattern.compile("(?<=\\$\\{)(.+?)(?=})");

	private final static String __pre = "\\$\\{";

	private final static String __suf = "}";

	private String __result;

	/**
	 * @param expressionStr 目标字符串
	 * @return 创建表达式工具类实例对象
	 */
	public static ExpressionUtils bind(String expressionStr) {
		return new ExpressionUtils(expressionStr);
	}

	private ExpressionUtils(String expressionStr){
		this.__result = expressionStr;
	}

	/**
	 * @return 获取结果
	 */
	public String getResult() {
		return this.__result;
	}

	/**
	 * 设置变量值
	 * 
	 * @param key 变量名称
	 * @param value 变量值
	 * @return 当前表达式工具类实例
	 */
	public ExpressionUtils set(String key, String value) {
		String namePattern = __pre + key + __suf;
		this.__result = this.__result.replaceAll(namePattern, Matcher.quoteReplacement(value));
		return this;
	}

	/**
	 * @return 返回expressionStr中变量名称集合, 返回的数量将受set方法影响
	 */
	public List<String> getVariables() {
		List<String> _vars = new ArrayList<String>();
		Matcher _match = __pattern.matcher(this.__result);
		boolean _result = _match.find();
		if (_result) {
			do {
				_vars.add(_match.group());
				_result = _match.find();
			}
			while (_result);
		}
		return _vars;
	}

	/**
	 * @return 清理所有变量并返回当前表达式工具类实例
	 */
	public ExpressionUtils clean() {
		return set("(.+?)", "");
	}
}
