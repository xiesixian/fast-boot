package com.xiesx.fastboot.support.validate.rule;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.xiesx.fastboot.support.validate.annotation.VNumber;

/**
 * @title VNumberRule.java
 * @description
 * @author Sixian.xie
 * @date 2019年3月7日 上午10:56:06
 */
public class VNumberRule implements ConstraintValidator<VNumber, String> {

    private final Pattern pattern = Pattern.compile("^[0-9]*$");

    @Override
    public void initialize(VNumber number) {}

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        return pattern.matcher(s).matches();
    }
}
