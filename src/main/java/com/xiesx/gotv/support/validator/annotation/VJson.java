package com.xiesx.gotv.support.validator.annotation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;

import com.xiesx.gotv.support.validator.annotation.rule.VJsonRule;

/**
 * @title VJson.java
 * @description
 * @author Sixian.xie
 * @date 2019年3月6日 下午6:24:22
 */
//申明注解的作用位置
@Target({ ANNOTATION_TYPE, FIELD, METHOD, PARAMETER })
//运行时机
@Retention(RUNTIME)
//定义对应的校验器,自定义注解必须指定
@Constraint(validatedBy = { VJsonRule.class })
//附带不能为空
@NotEmpty(message = "{act.empty}")
public @interface VJson {

	String message() default "{act.json}";// 错误提示信息默认值，可以使用el表达式。

	Class<?>[] groups() default {};// 约束注解在验证时所属的组别

	Class<? extends Payload>[] payload() default {};// 约束注解的有效负载

}