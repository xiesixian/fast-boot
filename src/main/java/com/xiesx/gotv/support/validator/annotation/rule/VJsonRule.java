package com.xiesx.gotv.support.validator.annotation.rule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.alibaba.fastjson.JSONObject;
import com.xiesx.gotv.support.validator.annotation.VJson;

/**
 * @title VJsonRule.java
 * @description
 * @author Sixian.xie
 * @date 2019年3月6日 下午6:25:39
 */
public class VJsonRule implements ConstraintValidator<VJson, String> {

	public void initialize(VJson number) {
	}

	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		try {
			if (s == null || s.equals("")) {
				return false;
			}
			//判断是否是json（原理：利用fastjson中str-->jsonOject 如果报错则非不是，反正则是）
			JSONObject.parseObject(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}