package com.xiesx.fastboot.support.validate.rule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONValidator;
import com.xiesx.fastboot.support.validate.annotation.VJson;

/**
 * @title VJsonRule.java
 * @description 验证JSON规则
 * @author Sixian.xie
 * @date 2020-7-21 22:44:51
 */
public class VJsonRule implements ConstraintValidator<VJson, String> {

    @Override
    public void initialize(VJson json) {}

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        return JSONValidator.from(s).validate();
    }
}
