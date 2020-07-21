package com.xiesx.fastboot.support.validate.rule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
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
        try {
            if (StringUtils.isEmpty(s)) {
                return false;
            }
            // 判断是否是json（原理：利用fastjson中str-->jsonOject 如果报错则非不是，反正则是）
            JSON.parseObject(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
