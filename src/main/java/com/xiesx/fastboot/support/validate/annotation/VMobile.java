package com.xiesx.fastboot.support.validate.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * @title VMobile.java
 * @description 手机验证
 * @author Sixian.xie
 * @date 2020-7-21 22:44:21
 */
// 申明注解的作用位置
@Target({ANNOTATION_TYPE, FIELD, METHOD, PARAMETER})
// 运行时机
@Retention(RUNTIME)
// 定义对应的校验器,自定义注解必须指定
@Constraint(validatedBy = {})
// 定义不能为空
@NotEmpty(message = "{fb.empty}")
// 定义长度验证
@Length(min = 11, max = 11, message = "{fb.mobile}")
// 验证手机号（第1为：1） + （第2位：可能是3/4/5/7/8等的任意一个） + （第3位：0-9） + d表示数字[0-9]的8位，总共加起来11位结束
@Pattern(regexp = "1[3|4|5|7|8][0-9]\\d{8}", message = "{fb.mobile}")
public @interface VMobile {

    String message() default "";// 错误提示信息默认值，可以使用el表达式。

    Class<?>[] groups() default {};// 约束注解在验证时所属的组别

    Class<? extends Payload>[] payload() default {};// 约束注解的有效负载
}
