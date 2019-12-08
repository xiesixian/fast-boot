package com.xiesx.gotv.core.validate.rule;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.xiesx.gotv.core.validate.annotation.VNumber;

/**
 * @title VNumberRule.java
 * @description
 * @author Sixian.xie
 * @date 2019年3月7日 上午10:56:06
 */
public class VNumberRule implements ConstraintValidator<VNumber, String> {

	private final Pattern pattern = Pattern.compile("^[0-9]*$");

	public void initialize(VNumber number) {
	}

	public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
		if (s == null || s.equals("")) {
			return false;
		}
		return pattern.matcher(s).matches();
	}
}