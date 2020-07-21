package com.xiesx.fastboot.support.validate.cfg;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @title ValidatorCfg.java
 * @description 效验配置
 * @author Sixian.xie
 * @date 2020-7-21 22:44:43
 */
@Configuration
public class ValidatorCfg {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        /** 默认是普通模式，会返回所有的验证不通过信息集合 */
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        /** 设置validator模式为快速失败返回 */
        postProcessor.setValidator(validator());
        return postProcessor;
    }

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
